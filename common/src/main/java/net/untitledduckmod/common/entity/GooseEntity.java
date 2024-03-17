package net.untitledduckmod.common.entity;

import net.minecraft.item.Item;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.random.Random;
import net.untitledduckmod.common.entity.ai.goal.common.EatGoal;
import net.untitledduckmod.common.entity.ai.goal.goose.*;
import net.untitledduckmod.common.init.ModEntityTypes;
import net.untitledduckmod.common.init.ModItems;
import net.untitledduckmod.common.init.ModSoundEvents;
import net.untitledduckmod.common.entity.ai.goal.common.SwimGoal;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.*;
import net.untitledduckmod.common.init.ModTags;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.keyframe.event.ParticleKeyframeEvent;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Objects;
import java.util.UUID;

public class GooseEntity extends WaterfowlEntity implements Angerable, AnimationController.ParticleKeyframeHandler<GooseEntity> {
    private static final TrackedData<Integer> ANGER_TIME = DataTracker.registerData(GooseEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final UniformIntProvider ANGER_TIME_RANGE = UniformIntProvider.create(20, 39);
    private UUID targetUuid;

    public static final byte ANIMATION_BITE = 2;
    public static final int ANIMATION_BITE_LEN = 22;
    public static final byte ANIMATION_INTIMIDATE = 6;

    private static final RawAnimation INTIMIDATE_ANIM = RawAnimation.begin().thenPlay("intimidate");
    private static final RawAnimation HONK_ANIM = RawAnimation.begin().thenPlay("honk");
    private static final RawAnimation BITE_ANIM = RawAnimation.begin().thenPlay("bite");
    private static final RawAnimation CHARGE_ANIM = RawAnimation.begin().thenPlay("charge");

    public static final Ingredient FOOD = Ingredient.fromTag(ModTags.ItemTags.GOOSE_FOOD);
    private static final Ingredient BREEDING_INGREDIENT = Ingredient.fromTag(ModTags.ItemTags.GOOSE_BREEDING_FOOD);
    private static final Ingredient TAMING_INGREDIENT = Ingredient.fromTag(ModTags.ItemTags.GOOSE_TAMING_FOOD);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private boolean wasSongPlaying = false;
    private int animationTimer = 0;

    public GooseEntity(EntityType<? extends WaterfowlEntity> entityType, World world) {
        super(entityType, world);
        this.setCanPickUpLoot(true);
    }

    public static boolean checkGooseSpawnRules(EntityType<GooseEntity> duck, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getBlockState(pos.down()).isIn(ModTags.BlockTags.GEESE_SPAWNABLE_ON) || world.getBlockState(pos.down()).getFluidState().isIn(FluidTags.WATER);
    }

    public static DefaultAttributeContainer.Builder getDefaultAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 7.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0D);
    }

    @Override
    public void setTamed(boolean tamed) {
        super.setTamed(tamed);
        Objects.requireNonNull(getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE)).setBaseValue(2.0);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ANGER_TIME, 0);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);
        this.writeAngerToNbt(tag);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);
        this.readAngerFromNbt(this.getWorld(), tag);
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
            for (int i = 0; i < 7; ++i) {
                double d = this.random.nextGaussian() * 0.02D;
                double e = this.random.nextGaussian() * 0.02D;
                double f = this.random.nextGaussian() * 0.02D;
                this.getWorld().addParticle(particleEffect, this.getParticleX(1.0D), this.getRandomBodyY() + 0.5D, this.getParticleZ(1.0D), d, e, f);
            }
        }
        super.handleStatus(status);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new GooseEscapeDangerGoal(this, 1.7D));

        this.goalSelector.add(2, new GooseIntimidateMobsGoal(this));

        this.goalSelector.add(3, new AnimalMateGoal(this, 1.0D));
        this.goalSelector.add(4, new SitGoal(this));

        this.goalSelector.add(3, new EatGoal(this));

        this.goalSelector.add(4, new GooseStealItemGoal(this));

        this.goalSelector.add(5, new GoosePickupFoodGoal(this));
        this.goalSelector.add(6, new GooseMeleeAttackGoal(this, 1.5D, true));

        this.goalSelector.add(7, new TemptGoal(this, 1.0D, BREEDING_INGREDIENT, false));
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

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return !isAngry() && BREEDING_INGREDIENT.test(stack);
    }

    @Override
    public void onEquipStack(EquipmentSlot slot, ItemStack oldStack, ItemStack newStack) {
        if (this.getWorld().isClient) {
            return;
        }
        Entity holder = newStack.getHolder();
        if (holder != null && isAngry() && holder instanceof ItemEntity ie) {
            UUID thrower = this.getThrower(ie);
            if (thrower != null) {
                stopAnger();
            }
        }
        super.onEquipStack(slot, oldStack, newStack);
    }

    private UUID getThrower(ItemEntity ie) {
        NbtCompound nbt = new NbtCompound();
        ie.writeCustomDataToNbt(nbt);
        if (nbt.containsUuid("Thrower")) return nbt.getUuid("Thrower");
        else return null;
    }

    @Override
    public void tickMovement() {
        super.tickMovement();

        if (!this.getWorld().isClient) {
            // Tick animation timer
            if (animationTimer > 0) {
                animationTimer--;
                if (animationTimer == 0) {
                    setAnimation(ANIMATION_IDLE);
                }
            }
        }
    }

    @Override
    protected void handlePanicAnimation() {
        if (!panicked && (((getHealth() < getMaxHealth() / 2) || isBaby()) && (getAttacker() != null || isOnFire()))) {
            setAnimation(ANIMATION_PANIC);
            panicked = true;
        } else if (panicked && getAttacker() == null && !isOnFire()) {
            setAnimation(ANIMATION_IDLE);
            panicked = false;
        }
    }

    protected boolean tryTaming(PlayerEntity player, ItemStack stack) {
        if (isAngry()) {
            // Peace goose when angry with food
            if (FOOD.test(stack)) {
                ItemStack newStack = stack.copy();
                newStack.setCount(1);
                if (!player.getAbilities().creativeMode) {
                    stack.decrement(1);
                }
                if (!tryEquip(newStack).isEmpty()) {
                    stopAnger();
                }
            }
            return false;
        }
        return true;
    }

    protected boolean isTamableItem(ItemStack stack) {
        return TAMING_INGREDIENT.test(stack);
    }

    protected boolean isTamable(PlayerEntity player, ItemStack stack) {
        return super.isTamable(player, stack) && !this.hasAngerTime();
    }

    private boolean isAngry() {
        return getTarget() != null;
    }

    @Override
    public void stopAnger() {
        this.setAttacker(null);
        this.setAngryAt(null);
        this.setTarget(null);
        this.setAngerTime(0);
        this.getWorld().sendEntityStatus(this, (byte) 100);
    }

    @Override
    public ItemStack tryEquip(ItemStack equipment) {
        EquipmentSlot equipmentSlot = EquipmentSlot.MAINHAND;
        ItemStack itemStack = getMainHandStack();
        if (FOOD.test(equipment) || this.canPickupItem(equipment)) {
            if (!itemStack.isEmpty()) {
                ItemEntity itemEntity = this.dropStack(itemStack);
                if (itemEntity != null) {
                    itemEntity.setPickupDelay(40);
                }
            }

            this.equipLootStack(equipmentSlot, equipment);
            return equipment;
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    protected void loot(ItemEntity item) {
        // Don't pick up threw/spat items
        if (this.getThrower(item) == this.getUuid()) {
            return;
        }
        super.loot(item);
    }

    @Override
    public boolean canPickupItem(ItemStack stack) {
        ItemStack mainHandStack = getMainHandStack();

        if ((!FOOD.test(mainHandStack) && FOOD.test(stack))) {
            return true;
        }
        if (mainHandStack.isEmpty()) {
            return !stack.isOf(ModItems.GOOSE_EGG.get());
        }
        return false;
    }

    @Override
    public boolean canPickUpLoot() {
        // Gosling shouldn't pick up items
        if (isBaby()) {
            return false;
        }
        return super.canPickUpLoot();
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        GooseEntity gooseEntity = ModEntityTypes.getGoose().create(world);
        if (gooseEntity != null) {
            gooseEntity.setVariant((byte) this.getWorld().getRandom().nextInt(2));
            UUID uuid = this.getOwnerUuid();
            if (uuid != null) {
                gooseEntity.setOwnerUuid(uuid);
                gooseEntity.setTamed(true);
            }
        }
        return gooseEntity;
    }

    @Override
    public void registerControllers(ControllerRegistrar controllerRegistrar) {
        AnimationController<GooseEntity> controller = new AnimationController<>(this, "controller", 2, this::predicate);
        controller.setParticleKeyframeHandler(this);
        controllerRegistrar.add(controller);
    }

    @Override
    protected SoundEvent getLayEggSound() {
        return ModSoundEvents.GOOSE_LAY_EGG.get();
    }

    @Override
    public Item getEggItem() {
        return ModItems.GOOSE_EGG.get();
    }

    @SuppressWarnings("rawtypes")
    private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
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
            case ANIMATION_BITE -> {
                controller.setAnimation(BITE_ANIM);
                animationTimer = ANIMATION_BITE_LEN;
            }
            case ANIMATION_INTIMIDATE -> controller.setAnimation(INTIMIDATE_ANIM);
            case ANIMATION_EAT -> controller.setAnimation(EAT_ANIM);
            case ANIMATION_CLEAN -> controller.setAnimation(inWater ? SWIM_ANIM : CLEAN_ANIM);
            case ANIMATION_DANCE -> controller.setAnimation(HONK_ANIM);
            case ANIMATION_PANIC -> controller.setAnimation(PANIC_ANIM);
            default -> {
                if (inWater) {
                    controller.setAnimation(isMoving ? SWIM_ANIM : SWIM_IDLE_ANIM);
                } else {
                    if (isAttacking()) {
                        controller.setAnimation(CHARGE_ANIM);
                        return PlayState.CONTINUE;
                    }
                    controller.setAnimation(isMoving ? WALK_ANIM : IDLE_ANIM);
                }
            }
        }

        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void playAmbientSound() {
        if (isBaby()) {
            this.playSound(ModSoundEvents.GOSLING_AMBIENT.get(), 0.3f, getSoundPitch());
            return;
        }
        if (hasAngerTime()) {
            this.playSound(ModSoundEvents.GOOSE_HONK.get(), 0.6f, getSoundPitch());
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
            this.playSound(ModSoundEvents.GOSLING_HURT.get(), 0.3f, getSoundPitch() + 0.25F);
            return;
        }
        this.playSound(ModSoundEvents.GOOSE_HONK.get(), 0.7f, getSoundPitch());
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        if (isBaby()) {
            return ModSoundEvents.GOSLING_DEATH.get();
        }
        return ModSoundEvents.GOOSE_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(ModSoundEvents.DUCK_STEP.get(), 0.15F, 1.0F);
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
        this.setAngerTime(ANGER_TIME_RANGE.get(this.random));
    }

    @Override
    public void handle(ParticleKeyframeEvent particleKeyframeEvent) {
        ItemStack stack = getMainHandStack();
        if (stack == ItemStack.EMPTY) {
            return;
        }
        for (int i = 0; i < 8; ++i) {
            Vec3d vel = new Vec3d(((double) this.random.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
            vel = vel.rotateX(-this.getPitch() * 0.017453292F);
            vel = vel.rotateY(-this.getYaw() * 0.017453292F);

            Vec3d rotationVec = Vec3d.fromPolar(0, bodyYaw);
            Vec3d pos = new Vec3d(this.getX() + rotationVec.x / 2.0D, getEyeY() - 0.2D, this.getZ() + rotationVec.z / 2.0D);
            this.getWorld().addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, stack), pos.x, pos.y, pos.z,
                    vel.x, vel.y + 0.05D, vel.z);
        }
    }

    public boolean wantsToPickupItem() {
        return !isSitting();
    }

    public boolean isHungry() {
        return isAngry() || super.isHungry();
    }
}