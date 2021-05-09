package net.untitledduckmod.items;

import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.untitledduckmod.ModEntityTypes;
import net.untitledduckmod.ModItems;
import net.untitledduckmod.ModSoundEvents;
import net.untitledduckmod.duck.DuckEntity;

import java.util.UUID;

public class DuckSackItem extends Item {
    public DuckSackItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        final World world = context.getWorld();
        final BlockPos pos = context.getBlockPos();
        final PlayerEntity player = context.getPlayer();
        final Direction blockSide = context.getSide();
        final Hand hand = context.getHand();

        if(!world.isClient) {
            placeCreature((ServerWorld) world, pos.offset(blockSide), context.getStack().getTag());
            if (player != null) {
                player.swingHand(hand);
                if(!player.isCreative()) {
                    player.setStackInHand(hand, new ItemStack(ModItems.getEmptyDuckSack()));
                }
            }
        }
        world.playSound(player, pos, ModSoundEvents.getDuckSackUse(), SoundCategory.NEUTRAL, 1.0F, 1.0F);
        return ActionResult.SUCCESS;
    }

    private void placeCreature(ServerWorld world, BlockPos pos, CompoundTag entityData) {
        // Remove uuid when there already is a creature with same uuid.
        // This makes it possible to use the bucket in creative, cloning every tag except the uuid.
        if (entityData != null && entityData.contains("EntityTag") && entityData.getCompound("EntityTag").containsUuid("UUID")) {
            UUID uuid = entityData.getCompound("EntityTag").getUuid("UUID");
            if (world.getEntity(uuid) != null) {
                entityData.getCompound("EntityTag").remove("UUID");
            }
        }
        DuckEntity newDuck = ModEntityTypes.getDuck().create(world, entityData, null, null, pos, SpawnReason.BUCKET, true, false);
        assert newDuck != null : "newDuck is null? This should not happen :(";
        newDuck.setFromSack(true);
        newDuck.setPos(pos.getX(), pos.getY(), pos.getZ());

        newDuck.refreshPositionAndAngles((double)pos.getX() + 0.5D, (double)pos.getY() + 0.4D, (double)pos.getZ() + 0.5D, MathHelper.wrapDegrees(world.random.nextFloat() * 360.0F), 0.0F);
        world.spawnEntity(newDuck);
    }

    @Override
    public Text getName(ItemStack stack) {
        if (stack.hasTag()) {
            CompoundTag duckData = stack.getTag();
            if (duckData != null && duckData.contains("EntityTag") && duckData.getCompound("EntityTag").contains("CustomName")) {
                Text duckName = Text.Serializer.fromJson(duckData.getCompound("EntityTag").getString("CustomName"));
                return new TranslatableText("item.untitledduckmod.duck_sack.named", duckName);
            }
        }
        return super.getName(stack);
    }
}
