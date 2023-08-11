package net.untitledduckmod.fabric;

import net.fabricmc.api.ModInitializer;
import net.untitledduckmod.*;
import software.bernie.geckolib.GeckoLib;


public class DuckModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        DuckMod.preInit();
        ModEntityTypes.register(null);
        ModEntityTypes.registerAttributes();
        ModEntityTypes.setupSpawning();
        ModSoundEvents.register(null);
        ModItems.register(null);
        ModStatusEffects.register();
        ModPotions.register();
        ModPotions.registerBrewing();
        GeckoLib.initialize();
        DuckMod.postEntityInit();
    }
}
