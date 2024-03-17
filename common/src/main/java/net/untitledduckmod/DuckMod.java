package net.untitledduckmod;

import net.untitledduckmod.common.CommonSetup;
import net.untitledduckmod.common.config.UntitledConfig;
import net.untitledduckmod.common.init.*;
import net.minecraft.util.Identifier;

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
        ModPotions.registerRecipe();
    }

    public static void postEntityInit() {
        CommonSetup.setupDispenserProjectile(ModItems.DUCK_EGG.get(), ModEntityTypes.getDuckEgg(), ModEntityTypes.getDuck());
        CommonSetup.setupDispenserProjectile(ModItems.GOOSE_EGG.get(), ModEntityTypes.getGooseEgg(), ModEntityTypes.getGoose());
    }

    public static Identifier id(String id) {
        return new Identifier(MOD_ID, id);
    }

    public static String stringID(String name) {
        return MOD_ID + ":" + name;
    }
}