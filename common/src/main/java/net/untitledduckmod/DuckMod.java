package net.untitledduckmod;

import net.minecraft.util.Identifier;
import net.untitledduckmod.common.CommonSetup;
import net.untitledduckmod.common.config.UntitledConfig;
import net.untitledduckmod.common.init.*;

public class DuckMod {
    public static final String MOD_ID = "untitledduckmod";

    public static void preInit() {
        UntitledConfig.setup();
        ModItems.init();
        ModEntityTypes.init();
        ModSoundEvents.init();
        ModStatusEffects.init();
        ModPotions.init();
    }

    public static void postInit() {
    }

    public static void postEntityInit() {
        CommonSetup.setupDispenserProjectile(ModItems.DUCK_EGG.get());
        CommonSetup.setupDispenserProjectile(ModItems.GOOSE_EGG.get());
    }

    public static Identifier id(String id) {
        return Identifier.of(MOD_ID, id);
    }

    public static String stringID(String name) {
        return MOD_ID + ":" + name;
    }
}