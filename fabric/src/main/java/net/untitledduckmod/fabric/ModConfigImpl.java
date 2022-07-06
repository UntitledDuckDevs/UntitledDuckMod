package net.untitledduckmod.fabric;

import net.untitledduckmod.DuckMod;
import net.untitledduckmod.ModConfig;
import net.untitledduckmod.fabric.config.TinyConfig;

public class ModConfigImpl extends TinyConfig {
    @Entry
    public static int GOOSE_SPAWN_WEIGHT = 4;
    @Entry
    public static int GOOSE_GROUP_SIZE = 4;

    @Entry
    public static int DUCK_SPAWN_WEIGHT = 4;
    @Entry
    public static int DUCK_GROUP_SIZE = 4;

    public static void setup() {
        TinyConfig.init(DuckMod.MOD_ID, ModConfigImpl.class);
        TinyConfig.write(DuckMod.MOD_ID);
        ModConfig.Goose.WEIGHT = () -> GOOSE_SPAWN_WEIGHT;
        ModConfig.Goose.GROUP_SIZE = () -> GOOSE_GROUP_SIZE;
        ModConfig.Duck.WEIGHT = () -> DUCK_SPAWN_WEIGHT;
        ModConfig.Duck.GROUP_SIZE = () -> DUCK_GROUP_SIZE;
    }
}
