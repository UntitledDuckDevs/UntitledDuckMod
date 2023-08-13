package net.untitledduckmod.duck;

import com.mojang.logging.LogUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.untitledduckmod.ModEntityTypes;
import net.untitledduckmod.ModItems;
import net.untitledduckmod.ModSoundEvents;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class DuckEntity extends AnimalEntity implements GeoAnimatable {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String EGG_LAY_TIME_TAG = "duckEggLayTime";
    public static final String VARIANT_TAG = "duckVariant";
    public static final String IS_FROM_SACK_TAG = "isFromSack";

    public static final float SWIM_SPEED_MULTIPLIER = 3.0f;
    protected static final TrackedData<Byte> VARIANT = DataTracker.registerData(DuckEntity.class, TrackedDataHandlerRegistry.BYTE);
    protected static final TrackedData<Byte> ANIMATION = DataTracker.registerData(DuckEntity.class, TrackedDataHandlerRegistry.BYTE);
    protected static final byte ANIMATION_IDLE = 0;
    protected static final byte ANIMATION_CLEAN = 1;
    protected static final byte ANIMATION_DIVE = 2;
    protected static final byte ANIMATION_DANCE = 3;
    protected static final byte ANIMATION_PANIC = 4;
    private static final RawAnimation WALK_ANIM = RawAnimation.begin().thenPlay("walk");
    private static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenPlay("idle");
    private static final RawAnimation SWIM_ANIM = RawAnimation.begin().thenPlay("swim");
    private static final RawAnimation SWIM_IDLE_ANIM = RawAnimation.begin().thenPlay("idle_swim");
    private static final RawAnimation CLEAN_ANIM = RawAnimation.begin().thenPlay("clean");
    private static final RawAnimation SWIM_CLEAN_ANIM = RawAnimation.begin().thenPlay("clean_swim").thenPlay("idle_swim");
    private static final RawAnimation DIVE_ANIM = RawAnimation.begin().thenPlay("dive").thenPlay("idle_swim");
    private static final RawAnimation DANCE_ANIM = RawAnimation.begin().thenPlay("dance");
    private static final RawAnimation FLY_ANIM = RawAnimation.begin().thenPlay("fly");
    private static final RawAnimation PANIC_ANIM = RawAnimation.begin().thenPlay("panic");

    private static final Ingredient BREEDING_INGREDIENT = Ingredient.ofItems(Items.WHEAT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.BEETROOT_SEEDS);
    private static final int MIN_EGG_LAY_TIME = 6000;
    private static final int MAX_EGG_LAY_TIME = 12000;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
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
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        setVariant((byte) random.nextInt(2)); // Randomly choose between the two variants
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(VARIANT, (byte) 0);
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
        this.goalSelector.add(3, new TemptGoal(this, 1.0D, BREEDING_INGREDIENT, false));
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
                duckSack.setNbt(sackData);

                if (stackInHand.isEmpty()) {
                    player.setStackInHand(hand, duckSack);
                } else if (!player.getInventory().insertStack(duckSack)) {
                    player.dropItem(duckSack, false);
                }
                world.playSound(null, getBlockPos(), ModSoundEvents.getDuckSackUse(), SoundCategory.NEUTRAL, 1.0F, 1.0F);
            } else {
                LOGGER.error("Could not save duck data to duck sack!");
            }

            discard();
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
    public void registerControllers(ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 2, this::predicate));
    }

    public boolean lookingAround() {
        return getAnimation() != ANIMATION_CLEAN;
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

        byte currentAnimation = getAnimation();
        switch (currentAnimation) {
            case ANIMATION_CLEAN -> controller.setAnimation(inWater ? SWIM_CLEAN_ANIM : CLEAN_ANIM);
            case ANIMATION_DIVE -> controller.setAnimation(DIVE_ANIM);
            case ANIMATION_DANCE -> controller.setAnimation(DANCE_ANIM);
            case ANIMATION_PANIC -> controller.setAnimation(PANIC_ANIM);
            default -> {
                if (inWater) {
                    controller.setAnimation(isMoving ? SWIM_ANIM : SWIM_IDLE_ANIM);
                } else {
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
    public double getTick(Object o) {
        return this.age;
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
    protected void swimUpward(TagKey<Fluid> fluid) {
        // This bypasses forge modifying jump depending on swim speed
        if (this.getNavigation().canSwim()) {
            this.setVelocity(this.getVelocity().add(0.0D, 0.03999999910593033D, 0.0D));
        } else {
            this.setVelocity(this.getVelocity().add(0.0D, 0.3D, 0.0D));
        }
    }

    public boolean canSpawn(WorldView world) {
        return world.doesNotIntersectEntities(this);
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
