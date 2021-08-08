package net.untitledduckmod.fabric;

import net.fabricmc.api.ModInitializer;
import net.untitledduckmod.*;
import net.untitledduckmod.items.DuckEggItem;
import net.untitledduckmod.items.DuckEggEntity;
import software.bernie.geckolib3.GeckoLib;

import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ProjectileDispenserBehavior;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.thrown.EggEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;

public class DuckModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        DuckMod.init();
        ModEntityTypes.register(null);
        ModEntityTypes.registerAttributes();
        ModEntityTypes.setupSpawning();
        ModSoundEvents.register(null);
        ModItems.register(null);
        ModStatusEffects.register();
        ModPotions.register();
        ModPotions.registerBrewing();
        GeckoLib.initialize();

        register((DuckEggItem) ModItems.getDuckEgg());
        register((DuckEggItem) ModItems.getGooseEgg());
    }

    static void register(DuckEggItem item) {
        DispenserBlock.registerBehavior(item, new ProjectileDispenserBehavior() {
            @Override
            protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
                DuckEggEntity eggEntity = new DuckEggEntity(item.thrownEntityType.get(), item.mobEntityType.get(), world, position.getX(), position.getY(), position.getZ());
                eggEntity.setItem(stack);
                return eggEntity;
            }
        });
    }
}
