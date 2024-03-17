package net.untitledduckmod.fabric;

import net.fabricmc.api.ModInitializer;
import net.untitledduckmod.DuckMod;
import net.untitledduckmod.common.init.ModEntityTypes;
import net.untitledduckmod.common.init.ModItems;
import software.bernie.geckolib.GeckoLib;

public class DuckModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        DuckMod.preInit();
        ModEntityTypes.registerAttributes(null);
        ModEntityTypes.setupSpawning(null);
        ModItems.setupItemGroups(null);
        GeckoLib.initialize();
        DuckMod.postInit();
        DuckMod.postEntityInit();
    }
}