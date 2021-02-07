package net.untitledduckmod.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.biome.BiomeKeys;
import net.untitledduckmod.DuckMod;
import net.untitledduckmod.EntityTypes;
import net.untitledduckmod.ModItems;
import software.bernie.geckolib3.GeckoLib;

import java.util.function.Predicate;


public class DuckModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        DuckMod.init();
        EntityTypes.register(null);
        EntityTypes.registerAttributes();
        EntityTypes.setupSpawning();
        ModItems.register(null);
        GeckoLib.initialize();

    }
}
