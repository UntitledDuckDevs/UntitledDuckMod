package net.untitledduckmod.common;

import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ProjectileDispenserBehavior;
import net.minecraft.item.Item;

public class CommonSetup {
    public static void setupDispenserProjectile(Item item) {
        // Setup projectile spawning for dispensers
        DispenserBlock.registerBehavior(item, new ProjectileDispenserBehavior(item));
    }
}
