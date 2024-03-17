package net.untitledduckmod.common;

import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ProjectileDispenserBehavior;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import net.untitledduckmod.common.entity.DuckEggEntity;

public class CommonSetup {
    public static void setupDispenserProjectile(Item item, EntityType<? extends ThrownItemEntity> thrownEntity, EntityType<? extends PassiveEntity> mobType) {
        // Setup projectile spawning for dispensers
        DispenserBlock.registerBehavior(item, new ProjectileDispenserBehavior() {
            protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
                return Util.make(new DuckEggEntity(thrownEntity, world, position.getX(), position.getY(), position.getZ(), mobType), (entity) -> {
                    entity.setItem(stack);
                });
            }
        });
    }
}
