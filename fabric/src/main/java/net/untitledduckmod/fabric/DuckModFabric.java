package net.untitledduckmod.fabric;

import net.fabricmc.api.ModInitializer;
import net.untitledduckmod.*;
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
        ModStatusEffects.register();
        ModPotions.register();
        ModPotions.registerBrewing();
        GeckoLib.initialize();
    }
}
