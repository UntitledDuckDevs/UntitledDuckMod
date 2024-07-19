package net.untitledduckmod.common.entity;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.RawAnimation;

import java.util.Objects;

public abstract class WaterfowlEntity extends TameableEntity implements GeoAnimatable {
    public static final String EGG_LAY_TIME_TAG = "EggLayTime";
    public static final String VARIANT_TAG = "Variant";
    public static final float SWIM_SPEED_MULTIPLIER = 3.0f;
    protected static final TrackedData<Byte> VARIANT = DataTracker.registerData(WaterfowlEntity.class, TrackedDataHandlerRegistry.BYTE);
    protected static final TrackedData<Byte> ANIMATION = DataTracker.registerData(WaterfowlEntity.class, TrackedDataHandlerRegistry.BYTE);
    public static final byte ANIMATION_IDLE = 0;
    public static final byte ANIMATION_CLEAN = 1;
    public static final byte ANIMATION_DANCE = 3;
    public static final byte ANIMATION_PANIC = 4;
    public static final byte ANIMATION_EAT = 5;

    protected static final RawAnimation WALK_ANIM = RawAnimation.begin().thenPlay("walk");
    protected static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenPlay("idle");
    protected static final RawAnimation SWIM_ANIM = RawAnimation.begin().thenPlay("swim");
    protected static final RawAnimation SWIM_IDLE_ANIM = RawAnimation.begin().thenPlay("idle_swim");
    protected static final RawAnimation PANIC_ANIM = RawAnimation.begin().thenPlay("panic");
    protected static final RawAnimation FLY_ANIM = RawAnimation.begin().thenPlay("fly");
    protected static final RawAnimation CLEAN_ANIM = RawAnimation.begin().thenPlay("clean");
    protected static final RawAnimation EAT_ANIM = RawAnimation.begin().thenPlay("eat");
    protected static final RawAnimation SIT_ANIM = RawAnimation.begin().thenPlay("sit");

    private static final int MIN_EGG_LAY_TIME = 6000;
    private static final int MAX_EGG_LAY_TIME = 12000;

    protected int eggLayTime;
    protected boolean isFlapping;
    protected boolean panicked = false;
    protected WaterfowlEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
        eggLayTime = random.nextInt(MIN_EGG_LAY_TIME) + (MAX_EGG_LAY_TIME - MIN_EGG_LAY_TIME);
        this.setPathfindingPenalty(PathNodeType.WATER, 0.0f);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        this.setVariant((byte) this.getWorld().getRandom().nextInt(2)); // Randomly choose between the two variants
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(VARIANT, (byte) 0);
        builder.add(ANIMATION, ANIMATION_IDLE);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);
        tag.putByte(VARIANT_TAG, getVariant());
        tag.putInt(EGG_LAY_TIME_TAG, eggLayTime);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);
        setVariant(tag.getByte(VARIANT_TAG));
        if (tag.contains(EGG_LAY_TIME_TAG)) {
            this.eggLayTime = tag.getInt(EGG_LAY_TIME_TAG);
        }
    }

    @Override
    public void setTamed(boolean tamed, boolean updateAttributes) {
        super.setTamed(tamed, updateAttributes);
        if (tamed) {
            Objects.requireNonNull(getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(20.0);
            setHealth(20.0F);
        } else {
            Objects.requireNonNull(getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(7.0);
        }
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
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

    public boolean isHungry() {
        return getHealth() <= getMaxHealth() - 0.5f;
    }

    public void tryEating() {
        assert !this.getWorld().isClient();

        ItemStack stack = getMainHandStack();
        stack.decrement(1);
        playSound(getEatSound(stack), 0.5F + 0.5F * (float) this.random.nextInt(2), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
        if (stack.isEmpty()) {
            setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
        }
        if (isHungry()) {
            heal(0.5f);
        }
    }

    public boolean lookingAround() {
        return getAnimation() != ANIMATION_CLEAN || getAnimation() != ANIMATION_EAT;
    }

    protected abstract SoundEvent getLayEggSound();

    public abstract Item getEggItem();

    @Override
    public void tickMovement() {
        super.tickMovement();

        if (!this.getWorld().isClient) {
            // Lay egg
            if (isAlive() && !isBaby() && --eggLayTime <= 0) {
                this.playSound(this.getLayEggSound(), 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                this.dropItem(this.getEggItem());
                this.eggLayTime = random.nextInt(MIN_EGG_LAY_TIME) + (MAX_EGG_LAY_TIME - MIN_EGG_LAY_TIME);
            }

            // Slow fall speed when flapping
            Vec3d velocity = this.getVelocity();
            if (!this.isOnGround() && velocity.y < 0.0D) {
                this.setVelocity(velocity.multiply(1.0D, 0.6D, 1.0D));
            }

            // Trigger panic animation when being attacked or being on fire
            this.handlePanicAnimation();
        }

        // Play flapping/fly animation when falling
        isFlapping = this.getWorld().isClient && !isTouchingWater() && !this.isOnGround();
    }

    protected abstract void handlePanicAnimation();

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        // TODO: Cleanup
        ItemStack stack = player.getStackInHand(hand);
        if (this.getWorld().isClient && (!this.isBaby() || !this.isBreedingItem(stack))) {
            if (this.isTamed() && this.isOwner(player)) {
                return ActionResult.SUCCESS;
            } else {
                return !isTamableItem(stack) || !(this.getHealth() < this.getMaxHealth()) && this.isTamed() ? ActionResult.PASS : ActionResult.SUCCESS;
            }
        } else {
            if (isTamed() && this.isOwner(player)) {
                if (this.isBreedingItem(stack) && this.getHealth() < this.getMaxHealth()) {
                    this.eat(player, hand, stack);
                    this.heal(0.5F);
                    return ActionResult.CONSUME;
                }
                ActionResult actionResult = super.interactMob(player, hand);
                if ((!actionResult.isAccepted() || this.isBaby())) {
                    this.setSitting(!this.isSitting());
                    this.jumping = false;
                    this.navigation.stop();
                    this.setTarget(null);
                }
                return actionResult;
            } else if (tryTaming(player, stack)) {
                if (!player.getAbilities().creativeMode) {
                    stack.decrement(1);
                }
                if (this.random.nextInt(3) == 0) {
                    this.setOwner(player);
                    this.navigation.stop();
                    this.setTarget(null);
                    this.setSitting(true);
                    this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_POSITIVE_PLAYER_REACTION_PARTICLES);
                } else {
                    this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_NEGATIVE_PLAYER_REACTION_PARTICLES);
                }
                return ActionResult.CONSUME;
            } else {
                return super.interactMob(player, hand);
            }
        }
    }

    protected boolean tryTaming(PlayerEntity player, ItemStack stack) {
        return this.isTamable(player, stack);
    }

    protected abstract boolean isTamableItem(ItemStack stack);

    protected boolean isTamable(PlayerEntity player, ItemStack stack) {
        return this.isTamableItem(stack) && !this.isTamed();
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

    @Override
    public boolean canSpawn(WorldView world) {
        return world.doesNotIntersectEntities(this);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        this.setSitting(false);
        return super.damage(source, amount);
    }

    @Override
    public double getTick(Object o) {
        return this.age;
    }

    public int getEggLayTime() {
        return this.eggLayTime;
    }

}
