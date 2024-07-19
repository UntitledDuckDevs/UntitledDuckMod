package net.untitledduckmod.common.item;

import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.untitledduckmod.common.entity.DuckEntity;
import net.untitledduckmod.common.init.ModEntityTypes;
import net.untitledduckmod.common.init.ModItems;
import net.untitledduckmod.common.init.ModSoundEvents;

import java.util.UUID;

public class DuckSackItem extends Item {
    public DuckSackItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (!world.isClient) {
            BlockPos pos = context.getBlockPos();
            BlockState blockState = world.getBlockState(pos);
            PlayerEntity user = context.getPlayer();
            Direction blockSide = context.getSide();
            Hand hand = context.getHand();
            ItemStack stack = context.getStack();

            if (user != null) {
                user.swingHand(hand);

                BlockPos placePos;
                if (blockState.getCollisionShape(world, pos).isEmpty()) {
                    placePos = pos;
                } else {
                    placePos = pos.offset(blockSide);
                }

                if (placeCreature((ServerWorld) world, placePos, stack.getOrCreateNbt())) {
                    world.emitGameEvent(user, GameEvent.ENTITY_PLACE, pos);

                    ItemStack emptySack = new ItemStack(ModItems.EMPTY_DUCK_SACK.get());
                    if (!user.getAbilities().creativeMode) {
                        stack.decrement(1);
                    }
                    if (stack.isEmpty()) {
                        user.setStackInHand(hand, emptySack);
                    } else if (!user.giveItemStack(emptySack)) {
                        user.dropItem(emptySack, false);
                    }

                    world.playSound(user, pos, ModSoundEvents.DUCK_SACK_USE.get(), SoundCategory.NEUTRAL, 1.0F, 1.0F);
                    return ActionResult.CONSUME;
                }
            }
            return ActionResult.CONSUME;
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        BlockHitResult blockHitResult = raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
        if (blockHitResult.getType() != HitResult.Type.BLOCK) {
            return TypedActionResult.pass(stack);
        } else if (!(world instanceof ServerWorld)) {
            return TypedActionResult.success(stack);
        } else {
            BlockPos pos = blockHitResult.getBlockPos();
            if (!(world.getBlockState(pos).getBlock() instanceof FluidBlock)) {
                return TypedActionResult.pass(stack);
            } else if (world.canPlayerModifyAt(user, pos) &&
                    user.canPlaceOn(pos, blockHitResult.getSide(), stack)) {
                if (placeCreature((ServerWorld) world, pos, stack.getOrCreateNbt())) {
                    user.incrementStat(Stats.USED.getOrCreateStat(this));
                    world.emitGameEvent(user, GameEvent.ENTITY_PLACE, pos);

                    ItemStack emptySack = new ItemStack(ModItems.EMPTY_DUCK_SACK.get());
                    if (!user.getAbilities().creativeMode) {
                        stack.decrement(1);
                    }
                    if (stack.isEmpty()) {
                        user.setStackInHand(hand, emptySack);
                    } else if (!user.giveItemStack(emptySack)) {
                        user.dropItem(emptySack, false);
                    }

                    world.playSound(user, pos, ModSoundEvents.DUCK_SACK_USE.get(), SoundCategory.NEUTRAL, 1.0F, 1.0F);
                    return TypedActionResult.consume(stack);
                } else {
                    return TypedActionResult.pass(stack);
                }
            } else {
                return TypedActionResult.fail(stack);
            }
        }
    }

    private boolean placeCreature(ServerWorld world, BlockPos pos, NbtCompound itemData) {
        NbtCompound entityData = itemData.getCompound("EntityTag");
        // Remove uuid when there already is a creature with same uuid.
        // This makes it possible to use the duck sack in creative, cloning every tag except the uuid.
        if (entityData.containsUuid(Entity.UUID_KEY)) {
            UUID uuid = entityData.getUuid(Entity.UUID_KEY);
            if (world.getEntity(uuid) != null) {
                entityData.remove(Entity.UUID_KEY);
            }
        }

        // This makes it possible to use duck sack with an empty nbt
        if (!entityData.contains(Entity.ID_KEY)) {
            entityData.putString(Entity.ID_KEY, EntityType.getId(ModEntityTypes.getDuck()).toString());
        }

        return EntityType.getEntityFromNbt(entityData, world).map((newDuck) -> {
            if (newDuck instanceof DuckEntity duck) {
                duck.readNbt(entityData);
                duck.setFromSack(true);
                duck.refreshPositionAndAngles((double) pos.getX() + 0.5D, (double) pos.getY() + 0.4D, (double) pos.getZ() + 0.5D, MathHelper.wrapDegrees(world.random.nextFloat() * 360.0F), 0.0F);
                world.spawnEntity(duck);
            }
            return newDuck;
        }).isPresent();
    }

    @Override
    public Text getName(ItemStack stack) {
        if (stack.hasNbt()) {
            NbtCompound itemData = stack.getNbt();
            if (itemData != null && itemData.contains("EntityTag") && itemData.getCompound("EntityTag").contains("CustomName")) {
                Text duckName = Text.Serializer.fromJson(itemData.getCompound("EntityTag").getString("CustomName"));
                return Text.translatable("item.untitledduckmod.duck_sack.named", duckName);
            }
        }
        return super.getName(stack);
    }
}

