package net.untitledduckmod.fabric;

import net.fabricmc.api.ModInitializer;
import net.untitledduckmod.DuckMod;
import net.untitledduckmod.EntityTypes;
import net.untitledduckmod.ModItems;
import software.bernie.geckolib3.GeckoLib;


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
