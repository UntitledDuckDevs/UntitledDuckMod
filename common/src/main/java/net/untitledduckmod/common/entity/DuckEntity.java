package net.untitledduckmod.common.entity;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.EntityPositionSource;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.PositionSource;
import net.minecraft.world.event.Vibrations;
import net.minecraft.world.event.listener.EntityGameEventHandler;
import net.minecraft.world.event.listener.GameEventListener;
import net.untitledduckmod.common.config.UntitledConfig;
import net.untitledduckmod.common.entity.ai.goal.common.EatGoal;
import net.untitledduckmod.common.entity.ai.goal.common.FollowParentGoal;
import net.untitledduckmod.common.entity.ai.goal.common.SwimGoal;
import net.untitledduckmod.common.init.*;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.keyframe.event.ParticleKeyframeEvent;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

public class DuckEntity extends WaterfowlEntity implements Vibrations, AnimationController.ParticleKeyframeHandler<DuckEntity> {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String IS_FROM_SACK_TAG = "isFromSack";

    private static final TrackedData<Boolean> DANCING = DataTracker.registerData(DuckEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public static final byte ANIMATION_DIVE = 2;
    private static final RawAnimation SWIM_CLEAN_ANIM = RawAnimation.begin().thenPlay("clean_swim").thenPlay("idle_swim");
    private static final RawAnimation DIVE_ANIM = RawAnimation.begin().thenPlay("dive").thenPlay("idle_swim");
    private static final RawAnimation DANCE_ANIM = RawAnimation.begin().thenPlay("dance");

    public static final Ingredient BREEDING_INGREDIENT = Ingredient.fromTag(ModTags.ItemTags.DUCK_BREEDING_FOOD);
    public static final Ingredient TAMING_INGREDIENT = Ingredient.fromTag(ModTags.ItemTags.DUCK_TAMING_FOOD);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private boolean isFromSack = false;
    private @Nullable BlockPos jukeboxPos;
    private Vibrations.ListenerData vibrationListenerData;
    private final Vibrations.Callback vibrationCallback;
    private final EntityGameEventHandler<DuckEntity.JukeboxEventListener> jukeboxEventHandler;

    public DuckEntity(EntityType<? extends WaterfowlEntity> entityType, World world) {
        super(entityType, world);
        this.vibrationCallback = new VibrationCallback();
        this.vibrationListenerData = new Vibrations.ListenerData();
        this.jukeboxEventHandler = new EntityGameEventHandler<>(new DuckEntity.JukeboxEventListener(this.vibrationCallback.getPositionSource(), GameEvent.JUKEBOX_PLAY.value().notificationRadius()));
    }

    public static DefaultAttributeContainer.Builder getDefaultAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 7.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2D)
                .add(EntityAttributes.GENERIC_LUCK);
    }

    @Override
    public void setTamed(boolean tamed, boolean updateAttributes) {
        super.setTamed(tamed, updateAttributes);
        Objects.requireNonNull(getAttributeInstance(EntityAttributes.GENERIC_LUCK)).setBaseValue(2.0);
    }

    public static boolean checkDuckSpawnRules(EntityType<DuckEntity> duck, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getBlockState(pos.down()).isIn(ModTags.BlockTags.DUCKS_SPAWNABLE_ON) || world.getBlockState(pos.down()).getFluidState().isIn(FluidTags.WATER);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(DANCING, false);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);
        tag.putBoolean(IS_FROM_SACK_TAG, isFromSack);
        DataResult<NbtElement> result = ListenerData.CODEC.encodeStart(NbtOps.INSTANCE, this.vibrationListenerData);
        result.resultOrPartial(LOGGER::error).ifPresent((nbtElement) -> tag.put("listener", nbtElement));
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);
        setFromSack(tag.getBoolean(IS_FROM_SACK_TAG));
        if (tag.contains("listener", NbtElement.COMPOUND_TYPE)) {
            DataResult<ListenerData> result = ListenerData.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, tag.getCompound("listener")));
            result.resultOrPartial(LOGGER::error).ifPresent((listenerData) -> this.vibrationListenerData = listenerData);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.getWorld().isClient()) {
            Ticker.tick(this.getWorld(), this.vibrationListenerData, this.vibrationCallback);
        }
    }

    @Override
    public void setNearbySongPlaying(BlockPos jukeboxPos, boolean playing) {
        if (playing) {
            if (!this.isDancing()) {
                this.jukeboxPos = jukeboxPos;
                this.setDancing(true);
            }
        } else if (jukeboxPos.equals(this.jukeboxPos) || this.jukeboxPos == null) {
            this.jukeboxPos = null;
            this.setDancing(false);
        }
    }

    public boolean isDancing() {
        return this.dataTracker.get(DANCING);
    }

    public void setDancing(boolean dancing) {
        if (!this.getWorld().isClient && this.canMoveVoluntarily() && (!dancing || !this.panicked)) {
            if (dancing) {
                setAnimation(ANIMATION_DANCE);
            } else {
                setAnimation(ANIMATION_IDLE);
            }
            this.dataTracker.set(DANCING, dancing);
        }
    }

    private boolean shouldStopDancing() {
        return this.jukeboxPos == null
                || !this.jukeboxPos.isWithinDistance(this.getPos(), GameEvent.JUKEBOX_PLAY.value().notificationRadius())
                || !this.getWorld().getBlockState(this.jukeboxPos).isOf(Blocks.JUKEBOX)
                || this.panicked;
    }

    @Override
    public Vibrations.ListenerData getVibrationListenerData() {
        return this.vibrationListenerData;
    }

    @Override
    public Vibrations.Callback getVibrationCallback() {
        return this.vibrationCallback;
    }

    @Override
    public void updateEventHandler(BiConsumer<EntityGameEventHandler<?>, ServerWorld> callback) {
        World world = this.getWorld();
        if (world instanceof ServerWorld serverWorld) {
            callback.accept(this.jukeboxEventHandler, serverWorld);
        }
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.6D));
        this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D));
        this.goalSelector.add(2, new EatGoal(this));
        this.goalSelector.add(3, new SitGoal(this));
        this.goalSelector.add(4, new TemptGoal(this, 1.0D, BREEDING_INGREDIENT, false));
        this.goalSelector.add(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.add(6, new FollowOwnerGoal(this, 1.6D, 10.0F, 2.0F));
        this.goalSelector.add(6, new CleanGoal(this));
        this.goalSelector.add(6, new DiveGoal(this));
        this.goalSelector.add(7, new WanderAroundGoal(this, 1.0D));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        if (this.isTamed()) {
            return TAMING_INGREDIENT.test(stack) || BREEDING_INGREDIENT.test(stack);
        }
        return BREEDING_INGREDIENT.test(stack);
    }

    @Override
    public void tickMovement() {
        super.tickMovement();

        if (!this.getWorld().isClient) {
            // Stop dancing under certain conditions
            if (this.isDancing() && this.shouldStopDancing() && this.age % 20 == 0) {
                this.setDancing(false);
                this.jukeboxPos = null;
            }
        }
    }

    @Override
    protected void handlePanicAnimation() {
        // Trigger panic animation when being attacked or being on fire
        if (!panicked && getAttacker() != null || isOnFire()) {
            setAnimation(ANIMATION_PANIC);
            panicked = true;
        } else if (panicked && getAttacker() == null && !isOnFire()) {
            setAnimation(ANIMATION_IDLE);
            panicked = false;
        }
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack stackInHand = player.getStackInHand(hand);
        if (stackInHand.getItem() == ModItems.EMPTY_DUCK_SACK.get()) {
            NbtCompound duckData = new NbtCompound();
            if (saveSelfNbt(duckData)) {
                stackInHand.decrementUnlessCreative(1, player);

                ItemStack duckSack = new ItemStack(ModItems.DUCK_SACK.get());
                duckSack.set(DataComponentTypes.ENTITY_DATA, NbtComponent.of(duckData));

                if (stackInHand.isEmpty()) {
                    player.setStackInHand(hand, duckSack);
                } else if (!player.giveItemStack(duckSack)) {
                    player.dropItem(duckSack, false);
                }
                this.getWorld().playSound(null, getBlockPos(), ModSoundEvents.DUCK_SACK_USE.get(), SoundCategory.NEUTRAL, 1.0F, 1.0F);
            } else {
                LOGGER.error("Could not save duck data to duck sack!");
            }

            discard();
            return ActionResult.success(this.getWorld().isClient);
        }
        return super.interactMob(player, hand);
    }

    protected boolean isTamableItem(ItemStack stack) {
        return TAMING_INGREDIENT.test(stack);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        DuckEntity duckEntity = ModEntityTypes.getDuck().create(world);
        if (duckEntity != null && entity instanceof DuckEntity duck) {
            if (this.random.nextBoolean()) {
                duckEntity.setVariant(this.getVariant());
            } else {
                duckEntity.setVariant(duck.getVariant());
            }
            if (this.isTamed()) {
                duckEntity.setOwnerUuid(this.getOwnerUuid());
                duckEntity.setTamed(true, true);
            }
        }
        return duckEntity;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        AnimationController<DuckEntity> controller = new AnimationController<>(this, "controller", 2, this::predicate);
        controller.setParticleKeyframeHandler(this);
        controllerRegistrar.add(controller);
    }

    @Override
    protected SoundEvent getLayEggSound() {
        return ModSoundEvents.DUCK_LAY_EGG.get();
    }

    @Override
    public Item getEggItem() {
        return ModItems.DUCK_EGG.get();
    }

    @SuppressWarnings("rawtypes")
    private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
        float limbSwingAmount = event.getLimbSwingAmount();
        boolean isMoving = !(limbSwingAmount > -0.05F && limbSwingAmount < 0.05F);
        boolean inWater = isTouchingWater();
        AnimationController<P> controller = event.getController();
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
            case ANIMATION_CLEAN -> controller.setAnimation(inWater ? SWIM_CLEAN_ANIM : CLEAN_ANIM);
            case ANIMATION_DIVE -> controller.setAnimation(DIVE_ANIM);
            case ANIMATION_DANCE -> controller.setAnimation(DANCE_ANIM);
            case ANIMATION_PANIC -> controller.setAnimation(PANIC_ANIM);
            case ANIMATION_EAT -> controller.setAnimation(EAT_ANIM);
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
    public void playAmbientSound() {
        if (isBaby()) {
            this.playSound(ModSoundEvents.DUCKLING_AMBIENT.get(), 0.3f, getSoundPitch());
            return;
        }
        this.playSound(ModSoundEvents.DUCK_AMBIENT.get(), 0.10f, getSoundPitch());
    }

    @Override
    protected void playHurtSound(DamageSource source) {
        if (isBaby()) {
            this.playSound(ModSoundEvents.DUCKLING_HURT.get(), 0.3f, getSoundPitch() + 0.25F);
            return;
        }
        this.playSound(ModSoundEvents.DUCK_HURT.get(), 0.10f, getSoundPitch() + 0.5F);
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        if (isBaby()) {
            return ModSoundEvents.DUCKLING_DEATH.get();
        }
        return ModSoundEvents.DUCK_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(ModSoundEvents.DUCK_STEP.get(), 0.15F, 1.0F);
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

    @Override
    public ItemStack tryEquip(ItemStack equipment) {
        EquipmentSlot equipmentSlot = EquipmentSlot.MAINHAND;
        ItemStack itemStack = getMainHandStack();

        if (canPickupItem(equipment)) {
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
        // Tamed duck should not be traded with non-owners
        if (this.isTamed() && !this.getMainHandStack().isEmpty() && item.getOwner() != null && !item.getOwner().getUuid().equals(this.getOwnerUuid()))
            return;
        super.loot(item);
    }

    @Override
    public boolean canPickupItem(ItemStack stack) {
        ItemStack mainHandStack = getMainHandStack();
        // If the main hand is empty, allow pickup if it's a breeding or fish item
        if (mainHandStack.isEmpty()) {
            return isBreedingItem(stack) || isTamableItem(stack);
        }

        // If the entity is tamed and the main hand is not empty, allow pickup if it's a breeding item
        if (this.isTamed() && !mainHandStack.isEmpty()) {
            return isBreedingItem(stack);
        }

        // If the main hand has a fish item, allow pickup of a breeding item
        return isTamableItem(mainHandStack) && isBreedingItem(stack);
    }

    @Override
    public boolean canPickUpLoot() {
        // Duckling shouldn't pick up items
        if (isBaby()) {
            return false;
        }
        return !getMainHandStack().isEmpty() || isHungry();
    }

    public void fishing() {
        MinecraftServer server = this.getWorld().getServer();
        if (!this.getWorld().isClient && server != null) {
            LootContextParameterSet lootBuilder = new LootContextParameterSet
                    .Builder((ServerWorld)this.getWorld())
                    .add(LootContextParameters.ORIGIN, this.getPos())
                    .add(LootContextParameters.TOOL, Items.FISHING_ROD.getDefaultStack())
                    .add(LootContextParameters.THIS_ENTITY, this)
                    .luck((float) this.getAttributeValue(EntityAttributes.GENERIC_LUCK))
                    .build(LootContextTypes.FISHING);
            LootTable lootTable = server.getReloadableRegistries().getLootTable(LootTables.FISHING_GAMEPLAY);
            List<ItemStack> list = lootTable.generateLoot(lootBuilder);
            for (ItemStack stack : list) {
                if (this.isTamed() || isTamableItem(stack)) {
                    this.setStackInHand(Hand.MAIN_HAND, stack);
                    this.equipLootStack(EquipmentSlot.MAINHAND, stack);
                    break;
                }
            }
        }
    }

    private class VibrationCallback implements Vibrations.Callback {

        private final PositionSource positionSource = new EntityPositionSource(DuckEntity.this, DuckEntity.this.getStandingEyeHeight());

        @Override
        public int getRange() {
            return 10;
        }

        @Override
        public PositionSource getPositionSource() {
            return positionSource;
        }

        @Override
        public boolean accepts(ServerWorld world, BlockPos pos, RegistryEntry<GameEvent> event, GameEvent.Emitter emitter) {
            return !DuckEntity.this.isAiDisabled();
        }

        @Override
        public void accept(ServerWorld world, BlockPos pos, RegistryEntry<GameEvent> event, @Nullable Entity sourceEntity, @Nullable Entity entity, float distance) {
        }
    }

    private class JukeboxEventListener implements GameEventListener {
        private final PositionSource positionSource;
        private final int range;

        public JukeboxEventListener(PositionSource positionSource, int range) {
            this.positionSource = positionSource;
            this.range = range;
        }

        public PositionSource getPositionSource() {
            return this.positionSource;
        }

        public int getRange() {
            return this.range;
        }

        public boolean listen(ServerWorld world, RegistryEntry<GameEvent> event, GameEvent.Emitter emitter, Vec3d emitterPos) {
            if (event == GameEvent.JUKEBOX_PLAY) {
                DuckEntity.this.setNearbySongPlaying(BlockPos.ofFloored(emitterPos), true);
                return true;
            } else if (event == GameEvent.JUKEBOX_STOP_PLAY) {
                DuckEntity.this.setNearbySongPlaying(BlockPos.ofFloored(emitterPos), false);
                return true;
            } else {
                return false;
            }
        }
    }

    static class CleanGoal extends Goal {
        private static final int ANIMATION_LENGTH = 32;
        private final DuckEntity duck;
        private int cleanTime;
        private int nextCleanTime;

        public CleanGoal(DuckEntity duck) {
            this.duck = duck;
            this.setControls(EnumSet.of(Control.LOOK, Control.MOVE));
            nextCleanTime = duck.age + (10 * 20 + duck.getRandom().nextInt(10) * 20);
        }

        @Override
        public boolean canStart() {
            // Don't clean if not near player
            if (nextCleanTime > duck.age || duck.getDespawnCounter() >= 100 || duck.getAnimation() != DuckEntity.ANIMATION_IDLE) {
                return false;
            }
            return duck.getRandom().nextInt(40) == 0;
        }

        @Override
        public void start() {
            cleanTime = ANIMATION_LENGTH;
            duck.setAnimation(DuckEntity.ANIMATION_CLEAN);
            nextCleanTime = duck.age + (10 * 20 + duck.getRandom().nextInt(10) * 20);
        }

        @Override
        public void stop() {
            duck.setAnimation(DuckEntity.ANIMATION_IDLE);
        }

        @Override
        public boolean shouldContinue() {
            return cleanTime >= 0;
        }

        @Override
        public void tick() {
            cleanTime--;
        }
    }

    static class DiveGoal extends Goal {
        private static final int ANIMATION_LENGTH = 32;
        private final DuckEntity duck;
        private int diveTime;
        private int nextDiveTime;

        public DiveGoal(DuckEntity duck) {
            this.duck = duck;
            this.setControls(EnumSet.of(Control.LOOK, Control.MOVE));
            nextDiveTime = duck.age + (8 * 20 + duck.getRandom().nextInt(10) * 20);
        }

        @Override
        public boolean canStart() {
            // Don't dive if not in water
            if (nextDiveTime > duck.age || duck.getDespawnCounter() >= 100 || !duck.isTouchingWater() || duck.getAnimation() != DuckEntity.ANIMATION_IDLE) {
                return false;
            }
            return duck.getRandom().nextInt(40) == 0 && duck.getMainHandStack().isEmpty();
        }

        @Override
        public void start() {
            //System.out.printf("[%d:%d] Start diving\n", nextDiveTime, duck.age);
            diveTime = ANIMATION_LENGTH;
            duck.setAnimation(DuckEntity.ANIMATION_DIVE);
            nextDiveTime = duck.age + (8 * 20 + duck.getRandom().nextInt(10) * 20);
        }

        @Override
        public void stop() {
            duck.setAnimation(DuckEntity.ANIMATION_IDLE);
        }

        @Override
        public boolean shouldContinue() {
            return diveTime >= 0;
        }

        @Override
        public void tick() {
            diveTime--;
            // Play splash sound 10 ticks = 0.5 seconds into the animation
            if (diveTime == 32 - 10) {
                if (duck.getRandom().nextDouble() < UntitledConfig.duckFishingChange()) {
                    duck.fishing();
                }
                duck.playSound(SoundEvents.ENTITY_GENERIC_SPLASH, 1.0f, 1.0f);
            }
        }
    }
}