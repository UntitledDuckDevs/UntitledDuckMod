package net.untitledduckmod.fabric;

import net.untitledduckmod.DuckMod;
import net.fabricmc.api.ModInitializer;
import net.untitledduckmod.EntityTypes;
import software.bernie.geckolib3.GeckoLib;


public class DuckModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        DuckMod.init();
        EntityTypes.register(null);
        EntityTypes.registerAttributes();
        GeckoLib.initialize();
    }
}
