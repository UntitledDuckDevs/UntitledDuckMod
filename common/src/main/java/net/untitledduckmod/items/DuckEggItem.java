package net.untitledduckmod.items;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class DuckEggItem extends Item {
    private final Supplier<EntityType<? extends ThrownItemEntity>> thrownEntityType;
    private final Supplier<EntityType<? extends PassiveEntity>> mobEntityType;

    public DuckEggItem(Settings settings, Supplier<EntityType<? extends ThrownItemEntity>> entityType, Supplier<EntityType<? extends PassiveEntity>> mobEntityType) {
        super(settings);
        this.thrownEntityType = entityType;
        this.mobEntityType = mobEntityType;
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_EGG_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!world.isClient) {
            DuckEggEntity eggEntity = new DuckEggEntity(thrownEntityType.get(), world, user, mobEntityType.get());
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
}
