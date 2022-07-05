package net.untitledduckmod;

public class DuckMod {
    public static final String MOD_ID = "untitledduckmod";

    public static void preInit() {
        ModConfig.setup();
    }

    public static void postEntityInit() {
        CommonSetup.setupDispenserProjectile(ModItems.getDuckEgg(), ModEntityTypes.getDuckEgg(), ModEntityTypes.getDuck());
        CommonSetup.setupDispenserProjectile(ModItems.getGooseEgg(), ModEntityTypes.getGooseEgg(), ModEntityTypes.getGoose());
    }
}
