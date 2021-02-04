package net.untitledduckmod.fabric;

import net.untitledduckmod.DuckMod;
import net.untitledduckmod.fabric.registration.EntityTypes;
import net.fabricmc.api.ModInitializer;
import software.bernie.geckolib3.GeckoLib;


public class DuckModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        DuckMod.init();
        EntityTypes.init();
        GeckoLib.initialize();
    }
}
