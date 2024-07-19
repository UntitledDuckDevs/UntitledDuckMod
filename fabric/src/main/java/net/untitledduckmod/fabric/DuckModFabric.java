package net.untitledduckmod.fabric;

import net.fabricmc.api.ModInitializer;
import net.untitledduckmod.DuckMod;
import net.untitledduckmod.common.init.ModEntityTypes;
import net.untitledduckmod.common.init.ModItems;
import net.untitledduckmod.common.init.fabric.ModPotionsImpl;

public class DuckModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        DuckMod.preInit();
        ModEntityTypes.registerAttributes(null);
        ModEntityTypes.setupSpawning(null);
        ModItems.setupItemGroups(null);
        ModPotionsImpl.registerRecipes();
        DuckMod.postInit();
        DuckMod.postEntityInit();
    }
}