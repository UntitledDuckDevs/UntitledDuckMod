package net.untitledduckmod.fabric;

import net.fabricmc.api.ModInitializer;
import net.untitledduckmod.DuckMod;
import net.untitledduckmod.ModEntityTypes;
import net.untitledduckmod.ModItems;
import net.untitledduckmod.ModSoundEvents;
import software.bernie.geckolib3.GeckoLib;


public class DuckModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        DuckMod.init();
        ModEntityTypes.register(null);
        ModEntityTypes.registerAttributes();
        ModEntityTypes.setupSpawning();
        ModSoundEvents.register(null);
        ModItems.register(null);
        GeckoLib.initialize();
    }
}
