package net.untitledduckmod.goose;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.Tag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.IntRange;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.untitledduckmod.ModEntityTypes;
import net.untitledduckmod.ModItems;
import net.untitledduckmod.ModSoundEvents;
import net.untitledduckmod.duck.DuckSwimGoal;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.ParticleKeyFrameEvent;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.UUID;

public class GooseEntity extends TameableEntity implements IAnimatable, Angerable, AnimationController.IParticleListener {
    public static final String EGG_LAY_TIME_TAG = "gooseEggLayTime";
    public static final String VARIANT_TAG = "gooseVariant";
    public static final float SWIM_SPEED_MULTIPLIER = 3.0f;
    protected static final TrackedData<Byte> VARIANT = DataTracker.registerData(TameableEntity.class, TrackedDataHandlerRegistry.BYTE);
    protected static final TrackedData<Byte> ANIMATION = DataTracker.registerData(TameableEntity.class, TrackedDataHandlerRegistry.BYTE);
    private static final TrackedData<Integer> ANGER_TIME = DataTracker.registerData(TameableEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final IntRange ANGER_TIME_RANGE = new IntRange(20, 39);
    private UUID targetUuid;

    public static final byte ANIMATION_IDLE = 0;
    public static final byte ANIMATION_CLEAN = 1;
    public static final byte ANIMATION_BITE = 2;
    public static final int ANIMATION_BITE_LEN = 22;
    public static final byte ANIMATION_DANCE = 3;
    public static final byte ANIMATION_PANIC = 4;
    public static final byte ANIMATION_EAT = 5;
    public static final byte ANIMATION_INTIMIDATE = 6;

    private static final AnimationBuilder WALK_ANIM = new AnimationBuilder().addAnimation("walk", true);
    private static final AnimationBuilder IDLE_ANIM = new AnimationBuilder().addAnimation("idle", true);
    private static final AnimationBuilder SWIM_ANIM = new AnimationBuilder().addAnimation("swim", true);
    private static final AnimationBuilder SWIM_IDLE_ANIM = new AnimationBuilder().addAnimation("idle_swim", true);
    private static final AnimationBuilder PANIC_ANIM = new AnimationBuilder().addAnimation("panic", true);
    private static final AnimationBuilder FLY_ANIM = new AnimationBuilder().addAnimation("fly", true);

    private static final AnimationBuilder CLEAN_ANIM = new AnimationBuilder().addAnimation("clean");
    private static final AnimationBuilder EAT_ANIM = new AnimationBuilder().addAnimation("eat", true);
    private static final AnimationBuilder INTIMIDATE_ANIM = new AnimationBuilder().addAnimation("intimidate", true);

    private static final AnimationBuilder HONK_ANIM = new AnimationBuilder().addAnimation("honk", true);
    private static final AnimationBuilder BITE_ANIM = new AnimationBuilder().addAnimation("bite", true);
    private static final AnimationBuilder SIT_ANIM = new AnimationBuilder().addAnimation("sit", true);
    private static final AnimationBuilder CHARGE_ANIM = new AnimationBuilder().addAnimation("charge", true);

    public static final Ingredient FOOD = Ingredient.ofItems(Items.WHEAT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.BEETROOT_SEEDS,
            Items.KELP, Items.SEAGRASS);
    private static final Ingredient BREEDING_INGREDIENT = Ingredient.ofItems(Items.WHEAT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.BEETROOT_SEEDS);
    private static final Ingredient TAMING_INGREDIENT = Ingredient.ofItems(Items.KELP, Items.SEAGRASS);
    private static final int MIN_EGG_LAY_TIME = 6000;
    private static final int MAX_EGG_LAY_TIME = 12000;
    private final AnimationFactory factory = new AnimationFactory(this);
    private int eggLayTime;
    private boolean isFlapping;
    private boolean wasSongPlaying = false;
    private boolean panicked = false;
    private int animationTimer = 0;

    public GooseEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
        eggLayTime = random.nextInt(MIN_EGG_LAY_TIME) + (MAX_EGG_LAY_TIME - MIN_EGG_LAY_TIME);
        this.setPathfindingPenalty(PathNodeType.WATER, 0.0f);
        this.setCanPickUpLoot(true);
    }

    public static DefaultAttributeContainer.Builder getDefaultAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 7.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0D);
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
        return false;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        byte variant = (byte) random.nextInt(2); // Chooses between 0 and 1 randomly
        this.dataTracker.startTracking(VARIANT, variant);
        this.dataTracker.startTracking(ANIMATION, ANIMATION_IDLE);
        this.dataTracker.startTracking(ANGER_TIME, 0);
    }

    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.putByte(VARIANT_TAG, getVariant());
        tag.putInt(EGG_LAY_TIME_TAG, eggLayTime);
        this.angerToTag(tag);
    }

    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        setVariant(tag.getByte(VARIANT_TAG));
        if (tag.contains(EGG_LAY_TIME_TAG)) {
            this.eggLayTime = tag.getInt(EGG_LAY_TIME_TAG);
        }
        this.angerFromTag((ServerWorld)this.world, tag);
    }

    public byte getVariant() {
        return dataTracker.get(VARIANT);
    }

    public void setVariant(byte variant) {
        dataTracker.set(VARIANT, variant);
    }

    public byte getAnimation() {
        return dataTracker.get(ANIMATION);
    }

    public void setAnimation(byte animation) {
        dataTracker.set(ANIMATION, animation);
    }

    @Override
    public void setNearbySongPlaying(BlockPos songPosition, boolean playing) {
        if (playing && !wasSongPlaying) {
            setAnimation(ANIMATION_DANCE);
            wasSongPlaying = true;
        } else if (!playing && wasSongPlaying) {
            setAnimation(ANIMATION_IDLE);
            wasSongPlaying = false;
        }
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void handleStatus(byte status) {
        if (status == 100) {
            ParticleEffect particleEffect = ParticleTypes.HAPPY_VILLAGER;
            for(int i = 0; i < 7; ++i) {
                double d = this.random.nextGaussian() * 0.02D;
                double e = this.random.nextGaussian() * 0.02D;
                double f = this.random.nextGaussian() * 0.02D;
                this.world.addParticle(particleEffect, this.getParticleX(1.0D), this.getRandomBodyY() + 0.5D, this.getParticleZ(1.0D), d, e, f);
            }
        }
        super.handleStatus(status);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new DuckSwimGoal(this));
        this.goalSelector.add(1, new GooseEscapeDangerGoal(this, 1.7D));

        this.goalSelector.add(2, new GooseIntimidateMobsGoal(this));

        this.goalSelector.add(3, new AnimalMateGoal(this, 1.0D));
        this.goalSelector.add(4, new SitGoal(this));

        this.goalSelector.add(3, new GooseEatGoal(this));

        this.goalSelector.add(4, new GooseStealItemGoal(this));

        this.goalSelector.add(5, new GoosePickupFoodGoal(this));
        this.goalSelector.add(6, new GooseMeleeAttackGoal(this, 1.5D, true));

        this.goalSelector.add(7, new TemptGoal(this, 1.0D, false, BREEDING_INGREDIENT));
        this.goalSelector.add(8, new FollowParentGoal(this, 1.1D));

        this.goalSelector.add(9, new FollowOwnerGoal(this, 1.6D, 10.0F, 2.0F, false));

        // Idle behaviour when there is nothing too urgent
        this.goalSelector.add(9, new GooseCleanGoal(this));

        this.goalSelector.add(10, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(10, new WanderAroundGoal(this, 1.0D));
        this.goalSelector.add(10, new LookAroundGoal(this));

        this.targetSelector.add(1, new TrackOwnerAttackerGoal(this));
        this.targetSelector.add(2, new AttackWithOwnerGoal(this));
        this.targetSelector.add(3, new GooseRevengeGoal(this).setGroupRevenge());
    }

    public boolean isBreedingItem(ItemStack stack) {
        return !isAngry() && BREEDING_INGREDIENT.test(stack);
    }

    @Override
    protected void onEquipStack(ItemStack stack) {
        if (world.isClient) {
            return;
        }
        Entity holder = stack.getHolder();
        if (isAngry() && holder instanceof ItemEntity) {
            ItemEntity ie = (ItemEntity) holder;
            UUID thrower = ie.getThrower();
            if (thrower != null) {
               stopAnger();
            }
        }
        super.onEquipStack(stack);
    }

    @Override
    public void tickMovement() {
        super.tickMovement();

        if (!world.isClient) {
            // Lay egg
            if (isAlive() && !isBaby() && --eggLayTime <= 0) {
                this.playSound(ModSoundEvents.getGooseEggSound(), 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                this.dropItem(ModItems.getGooseEgg());
                this.eggLayTime = random.nextInt(MIN_EGG_LAY_TIME) + (MAX_EGG_LAY_TIME - MIN_EGG_LAY_TIME);
            }

            // Slow fall speed when flapping
            Vec3d velocity = this.getVelocity();
            if (!this.onGround && velocity.y < 0.0D) {
                this.setVelocity(velocity.multiply(1.0D, 0.6D, 1.0D));
            }

            // Trigger panic animation when being attacked or being on fire
            if (!panicked && (((getHealth() < getMaxHealth()/2) || isBaby()) && (getAttacker() != null || isOnFire()))) {
                setAnimation(ANIMATION_PANIC);
                panicked = true;
            } else if (panicked && getAttacker() == null && !isOnFire()) {
                setAnimation(ANIMATION_IDLE);
                panicked = false;
            }

            // Tick animation timer
            if (animationTimer > 0) {
                animationTimer--;
                if (animationTimer == 0) {
                    setAnimation(ANIMATION_IDLE);
                }
            }
        }

        // Play flapping/fly animation when falling
        isFlapping = world.isClient && !isTouchingWater() && !this.onGround;
    }

    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        // TODO: Cleanup
        ItemStack itemStack = player.getStackInHand(hand);
        if (this.world.isClient) {
            boolean tamable = this.isOwner(player) || this.isTamed() || TAMING_INGREDIENT.test(itemStack) && !this.isTamed() && !this.hasAngerTime();
            return tamable ? ActionResult.CONSUME : ActionResult.PASS;
        } else {
            if (isTamed()) {
                ActionResult actionResult = super.interactMob(player, hand);
                if ((!actionResult.isAccepted() || this.isBaby()) && this.isOwner(player)) {
                    this.setSitting(!this.isSitting());
                    this.jumping = false;
                    this.navigation.stop();
                    this.setTarget(null);
                    return ActionResult.SUCCESS;
                }
                return super.interactMob(player, hand);
            } else {
                if (isAngry()) {
                    // Peace goose when angry with food
                    if (FOOD.test(itemStack)) {
                        ItemStack newStack = itemStack.copy();
                        newStack.setCount(1);
                        if (!player.abilities.creativeMode) {
                            itemStack.decrement(1);
                        }
                        if (tryEquip(newStack)) {
                            stopAnger();
                        }
                    }
                    return ActionResult.CONSUME;
                } else {
                    if (TAMING_INGREDIENT.test(itemStack)) {
                        if (!player.abilities.creativeMode) {
                            itemStack.decrement(1);
                        }
                        if (this.random.nextInt(3) == 0) {
                            this.setOwner(player);
                            this.navigation.stop();
                            this.setTarget(null);
                            this.setSitting(true);
                            this.world.sendEntityStatus(this, (byte) 7);
                        } else {
                            this.world.sendEntityStatus(this, (byte) 6);
                        }
                        return ActionResult.SUCCESS;
                    }
                }
            }
        }
        return super.interactMob(player, hand);
    }

    private boolean isAngry() {
        return getTarget() != null;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        this.setSitting(false);
        return super.damage(source, amount);
    }

    @Override
    protected void loot(ItemEntity item) {
        // Don't pickup threw/spat items
        if (item.getThrower() == this.getUuid()) {
            return;
        }
        super.loot(item);
    }

    @Override
    public void stopAnger() {
        this.setAttacker(null);
        this.setAngryAt(null);
        this.setTarget(null);
        this.setAngerTime(0);
        world.sendEntityStatus(this, (byte)100);
    }

    @Override
    public boolean tryEquip(ItemStack equipment) {
        EquipmentSlot equipmentSlot = EquipmentSlot.MAINHAND;
        ItemStack itemStack = getMainHandStack();
        if (FOOD.test(equipment) || this.canPickupItem(equipment)) {
            if (!itemStack.isEmpty()) {
                ItemEntity itemEntity = this.dropStack(itemStack);
                if (itemEntity != null) {
                    itemEntity.setPickupDelay(60);
                }
            }

            this.equipLootStack(equipmentSlot, equipment);
            this.onEquipStack(equipment);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean canPickupItem(ItemStack stack) {
        ItemStack mainHandStack = getMainHandStack();

        if ((!FOOD.test(mainHandStack) && FOOD.test(stack))) return true;
        if (mainHandStack.isEmpty()) {
            return stack.getItem() != ModItems.getGooseEgg();
        }
        return false;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntityTypes.getGoose().create(world);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void registerControllers(AnimationData data) {
        AnimationController controller = new AnimationController<>(this, "controller", 2, this::predicate);
        controller.registerParticleListener(this);
        data.addAnimationController(controller);
    }

    public boolean lookingAround() {
        return getAnimation() != ANIMATION_CLEAN || getAnimation() != ANIMATION_EAT;
    }

    @SuppressWarnings("rawtypes")
    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        float limbSwingAmount = event.getLimbSwingAmount();
        boolean isMoving = !(limbSwingAmount > -0.05F && limbSwingAmount < 0.05F);
        boolean inWater = isTouchingWater();
        AnimationController controller = event.getController();
        if (isFlapping) {
            controller.setAnimation(FLY_ANIM);
            return PlayState.CONTINUE;
        }
        if (isInSittingPose()) {
            controller.setAnimation(SIT_ANIM);
            return PlayState.CONTINUE;
        }


        byte currentAnimation = getAnimation();
        switch (currentAnimation) {
            case ANIMATION_BITE:
                controller.setAnimation(BITE_ANIM);
                animationTimer = ANIMATION_BITE_LEN;
                break;
            case ANIMATION_INTIMIDATE:
                controller.setAnimation(INTIMIDATE_ANIM);
                break;
            case ANIMATION_EAT:
                controller.setAnimation(EAT_ANIM);
                break;
            case ANIMATION_CLEAN:
                controller.setAnimation(inWater ? SWIM_ANIM : CLEAN_ANIM);
                break;
            case ANIMATION_DANCE:
                controller.setAnimation(HONK_ANIM);
                break;
            case ANIMATION_PANIC:
                controller.setAnimation(PANIC_ANIM);
                break;
            default:
                if (inWater) {
                    controller.setAnimation(isMoving ? SWIM_ANIM : SWIM_IDLE_ANIM);
                } else {
                    if (isAttacking()) {
                        controller.setAnimation(CHARGE_ANIM);
                        return PlayState.CONTINUE;
                    }
                    controller.setAnimation(isMoving ? WALK_ANIM : IDLE_ANIM);
                }
                break;
        }

        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    public void tryEating() {
        assert !world.isClient();

        ItemStack stack = getMainHandStack();
        stack.decrement(1);
        playSound(getEatSound(stack), 0.5F + 0.5F * (float)this.random.nextInt(2), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
        if (stack.isEmpty()) {
            setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
        }
        if (isHungry()) {
            heal(0.5f);
        }
    }

    @Override
    public void playAmbientSound() {
        if (isBaby()) {
            this.playSound(ModSoundEvents.getDucklingAmbientSound(), 0.3f, getSoundPitch());
            return;
        }
        if (hasAngerTime()) {
            this.playSound(ModSoundEvents.getGooseHonkSound(), 0.6f, getSoundPitch());
        }
    }

    @Nullable
    @Override
    public ItemEntity dropStack(ItemStack stack, float yOffset) {
        ItemEntity droppedStack = super.dropStack(stack, yOffset);
        if (droppedStack == null) {
            return null;
        }
        droppedStack.setThrower(this.getUuid());
        return droppedStack;
    }

    @Override
    protected void playHurtSound(DamageSource source) {
        if (isBaby()) {
            this.playSound(ModSoundEvents.getGoslingHurtSound(), 0.3f, getSoundPitch() + 0.25F);
            return;
        }
        this.playSound(ModSoundEvents.getGooseHonkSound(), 0.7f, getSoundPitch());
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        if (isBaby()) {
            return ModSoundEvents.getGoslingDeathSound();
        }
        return ModSoundEvents.getGooseDeathSound();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(ModSoundEvents.getDuckStepSound(), 0.15F, 1.0F);
    }

    @Override
    protected void swimUpward(Tag<Fluid> fluid) {
        // This bypasses forge modifying jump depending on swim speed
        if (this.getNavigation().canSwim()) {
            this.setVelocity(this.getVelocity().add(0.0D, 0.03999999910593033D, 0.0D));
        } else {
            this.setVelocity(this.getVelocity().add(0.0D, 0.3D, 0.0D));
        }
    }

    @Override
    public int getAngerTime() {
        return dataTracker.get(ANGER_TIME);
    }

    @Override
    public void setAngerTime(int ticks) {
        dataTracker.set(ANGER_TIME, ticks);
    }

    @Nullable
    @Override
    public UUID getAngryAt() {
        return targetUuid;
    }

    @Override
    public void setAngryAt(@Nullable UUID uuid) {
        targetUuid = uuid;
    }

    @Override
    public void chooseRandomAngerTime() {
        this.setAngerTime(ANGER_TIME_RANGE.choose(this.random));
    }

    @Override
    public <A extends IAnimatable> void summonParticle(ParticleKeyFrameEvent<A> particleKeyFrameEvent) {
        ItemStack stack = getMainHandStack();
        if (stack == ItemStack.EMPTY) {
            return;
        }
        for(int i = 0; i < 8; ++i) {
            Vec3d vel = new Vec3d(((double)this.random.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
            vel = vel.rotateX(-this.pitch * 0.017453292F);
            vel = vel.rotateY(-this.yaw * 0.017453292F);

            Vec3d rotationVec = Vec3d.fromPolar(0, bodyYaw);
            Vec3d pos = new Vec3d(this.getX() + rotationVec.x / 2.0D, getEyeY() - 0.2D, this.getZ() + rotationVec.z/2.0D);
            this.world.addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, stack), pos.x, pos.y, pos.z,
                    vel.x, vel.y + 0.05D, vel.z);
        }
    }

    public boolean wantsToPickupItem() {
        return !isSitting();
    }

    public boolean isHungry() {
        return isAngry() || getHealth() <= getMaxHealth() - 0.5f;
    }
}
