package net.untitledduckmod.common.item;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ProjectileItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import net.untitledduckmod.common.entity.WaterfowlEggEntity;
import net.untitledduckmod.common.entity.WaterfowlEntity;

import java.util.function.Supplier;

public class WaterfowlEggItem extends Item implements ProjectileItem {
    private final Supplier<EntityType<? extends ThrownItemEntity>> thrownEntityType;
    private final Supplier<EntityType<? extends WaterfowlEntity>> mobEntityType;

    public WaterfowlEggItem(Item.Settings settings, Supplier<EntityType<? extends ThrownItemEntity>> entityType, Supplier<EntityType<? extends WaterfowlEntity>> mobEntityType) {
        super(settings);
        this.thrownEntityType = entityType;
        this.mobEntityType = mobEntityType;
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_EGG_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!world.isClient) {
            WaterfowlEggEntity eggEntity = new WaterfowlEggEntity(thrownEntityType.get(), world, user, mobEntityType.get());
            eggEntity.setItem(itemStack);
            eggEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
            world.spawnEntity(eggEntity);
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }

    @Override
    public ProjectileEntity createEntity(World world, Position pos, ItemStack stack, Direction direction) {
        WaterfowlEggEntity eggEntity = new WaterfowlEggEntity(thrownEntityType.get(), world, pos.getX(), pos.getY(), pos.getZ(), mobEntityType.get());
        eggEntity.setItem(stack);
        return eggEntity;
    }
}