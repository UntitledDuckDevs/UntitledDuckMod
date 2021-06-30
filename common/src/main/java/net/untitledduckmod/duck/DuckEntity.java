package net.untitledduckmod.duck;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.Tag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.untitledduckmod.ModEntityTypes;
import net.untitledduckmod.ModItems;
import net.untitledduckmod.ModSoundEvents;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class DuckEntity extends AnimalEntity implements IAnimatable {
    public static final String EGG_LAY_TIME_TAG = "duckEggLayTime";
    public static final String VARIANT_TAG = "duckVariant";
    public static final String IS_FROM_SACK_TAG = "isFromSack";

    public static final float SWIM_SPEED_MULTIPLIER = 3.0f;
    protected static final TrackedData<Byte> VARIANT = DataTracker.registerData(TameableEntity.class, TrackedDataHandlerRegistry.BYTE);
    protected static final TrackedData<Byte> ANIMATION = DataTracker.registerData(TameableEntity.class, TrackedDataHandlerRegistry.BYTE);
    protected static final byte ANIMATION_IDLE = 0;
    protected static final byte ANIMATION_CLEAN = 1;
    protected static final byte ANIMATION_DIVE = 2;
    protected static final byte ANIMATION_DANCE = 3;
    protected static final byte ANIMATION_PANIC = 4;
    private static final AnimationBuilder WALK_ANIM = new AnimationBuilder().addAnimation("walk", true);
    private static final AnimationBuilder IDLE_ANIM = new AnimationBuilder().addAnimation("idle", true);
    private static final AnimationBuilder SWIM_ANIM = new AnimationBuilder().addAnimation("swim", true);
    private static final AnimationBuilder SWIM_IDLE_ANIM = new AnimationBuilder().addAnimation("idle_swim", true);
    private static final AnimationBuilder CLEAN_ANIM = new AnimationBuilder().addAnimation("clean");
    private static final AnimationBuilder SWIM_CLEAN_ANIM = new AnimationBuilder().addAnimation("clean_swim");
    private static final AnimationBuilder DIVE_ANIM = new AnimationBuilder().addAnimation("dive");
    private static final AnimationBuilder DANCE_ANIM = new AnimationBuilder().addAnimation("dance", true);
    private static final AnimationBuilder FLY_ANIM = new AnimationBuilder().addAnimation("fly", true);
    private static final AnimationBuilder PANIC_ANIM = new AnimationBuilder().addAnimation("panic", true);

    private static final Ingredient BREEDING_INGREDIENT = Ingredient.ofItems(Items.WHEAT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.BEETROOT_SEEDS);
    private static final int MIN_EGG_LAY_TIME = 6000;
    private static final int MAX_EGG_LAY_TIME = 12000;
    private final AnimationFactory factory = new AnimationFactory(this);
    private int eggLayTime;
    private boolean isFlapping;
    private boolean wasSongPlaying = false;
    private boolean panicked = false;
    private boolean isFromSack = false;

    public DuckEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        eggLayTime = random.nextInt(MIN_EGG_LAY_TIME) + (MAX_EGG_LAY_TIME - MIN_EGG_LAY_TIME);
        this.setPathfindingPenalty(PathNodeType.WATER, 0.0f);
    }

    public static DefaultAttributeContainer.Builder getDefaultAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 7.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2D);
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        byte variant = (byte) random.nextInt(2); // Chooses between 0 and 1 randomly
        this.dataTracker.startTracking(VARIANT, variant);
        this.dataTracker.startTracking(ANIMATION, ANIMATION_IDLE);
    }

    public void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);
        tag.putByte(VARIANT_TAG, getVariant());
        tag.putInt(EGG_LAY_TIME_TAG, eggLayTime);
        tag.putBoolean(IS_FROM_SACK_TAG, isFromSack);
    }

    public void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);
        setVariant(tag.getByte(VARIANT_TAG));
        if (tag.contains(EGG_LAY_TIME_TAG)) {
            this.eggLayTime = tag.getInt(EGG_LAY_TIME_TAG);
        }
        setFromSack(tag.getBoolean(IS_FROM_SACK_TAG));
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

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new DuckSwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.6D));
        this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D));
        this.goalSelector.add(3, new TemptGoal(this, 1.0D, false, BREEDING_INGREDIENT));
        this.goalSelector.add(4, new FollowParentGoal(this, 1.1D));
        this.goalSelector.add(5, new WanderAroundGoal(this, 1.0D));
        this.goalSelector.add(5, new DuckCleanGoal(this));
        this.goalSelector.add(5, new DuckDiveGoal(this));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(7, new LookAroundGoal(this));
    }

    public boolean isBreedingItem(ItemStack stack) {
        return BREEDING_INGREDIENT.test(stack);
    }

    @Override
    public void tickMovement() {
        super.tickMovement();

        if (!world.isClient) {
            // Lay egg
            if (isAlive() && !isBaby() && --eggLayTime <= 0) {
                this.playSound(ModSoundEvents.getDuckEggSound(), 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                this.dropItem(ModItems.getDuckEgg());
                this.eggLayTime = random.nextInt(MIN_EGG_LAY_TIME) + (MAX_EGG_LAY_TIME - MIN_EGG_LAY_TIME);
            }

            // Slow fall speed when flapping
            Vec3d velocity = this.getVelocity();
            if (!this.onGround && velocity.y < 0.0D) {
                this.setVelocity(velocity.multiply(1.0D, 0.6D, 1.0D));
            }

            // Trigger panic animation when being attacked or being on fire
            if (!panicked && getAttacker() != null || isOnFire()) {
                setAnimation(ANIMATION_PANIC);
                panicked = true;
            } else if (panicked && getAttacker() == null && !isOnFire()) {
                setAnimation(ANIMATION_IDLE);
                panicked = false;
            }
        }

        // Play flapping/fly animation when falling
        isFlapping = world.isClient && !isTouchingWater() && !this.onGround;
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack stackInHand = player.getStackInHand(hand);
        if (stackInHand.getItem() == ModItems.getEmptyDuckSack()) {
            NbtCompound duckData = new NbtCompound();
            if (saveSelfNbt(duckData)) {
                stackInHand.decrement(1);

                ItemStack duckSack = new ItemStack(ModItems.getDuckSack());
                NbtCompound sackData = new NbtCompound();
                sackData.put("EntityTag", duckData);
                duckSack.setTag(sackData);

                if (stackInHand.isEmpty()) {
                    player.setStackInHand(hand, duckSack);
                } else if (!player.getInventory().insertStack(duckSack)) {
                    player.dropItem(duckSack, false);
                }
                world.playSound(null, getBlockPos(), ModSoundEvents.getDuckSackUse(), SoundCategory.NEUTRAL, 1.0F, 1.0F);
            } else {
                LOGGER.error("Could not save duck data to duck sack!");
            }

            remove();
            return ActionResult.success(world.isClient);
        }
        return super.interactMob(player, hand);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntityTypes.getDuck().create(world);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 2, this::predicate));
    }

    public boolean lookingAround() {
        return getAnimation() != ANIMATION_CLEAN;
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

        byte currentAnimation = getAnimation();
        switch (currentAnimation) {
            case ANIMATION_CLEAN:
                controller.setAnimation(inWater ? SWIM_CLEAN_ANIM : CLEAN_ANIM);
                break;
            case ANIMATION_DIVE:
                controller.setAnimation(DIVE_ANIM);
                break;
            case ANIMATION_DANCE:
                controller.setAnimation(DANCE_ANIM);
                break;
            case ANIMATION_PANIC:
                controller.setAnimation(PANIC_ANIM);
                break;
            default:
                if (inWater) {
                    controller.setAnimation(isMoving ? SWIM_ANIM : SWIM_IDLE_ANIM);
                } else {
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

    @Override
    public void playAmbientSound() {
        if (isBaby()) {
            this.playSound(ModSoundEvents.getDucklingAmbientSound(), 0.3f, getSoundPitch());
            return;
        }
        this.playSound(ModSoundEvents.getDuckAmbientSound(), 0.10f, getSoundPitch());
    }

    @Override
    protected void playHurtSound(DamageSource source) {
        if (isBaby()) {
            this.playSound(ModSoundEvents.getDucklingHurtSound(), 0.3f, getSoundPitch() + 0.25F);
            return;
        }
        this.playSound(ModSoundEvents.getDuckHurtSound(), 0.10f, getSoundPitch() + 0.5F);
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        if (isBaby()) {
            return ModSoundEvents.getDucklingDeathSound();
        }
        return ModSoundEvents.getDuckDeathSound();
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
    public boolean cannotDespawn() {
        return super.cannotDespawn() || isFromSack;
    }

    public boolean isFromSack() {
        return isFromSack;
    }

    public void setFromSack(boolean fromSack) {
        isFromSack = fromSack;
    }
}
