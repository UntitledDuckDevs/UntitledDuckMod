package net.untitledduckmod.fabric;

import net.untitledduckmod.DuckMod;
import net.untitledduckmod.ModConfig;
import net.untitledduckmod.fabric.config.TinyConfig;

public class ModConfigImpl extends TinyConfig {
    @Comment
    public static Comment ducks;
    @Entry
    public static int duck_spawn_weight = 6;
    @Entry
    public static int duck_group_size = 4;

    @Comment
    public static Comment geese;
    @Entry
    public static int goose_spawn_weight = 4;
    @Entry
    public static int goose_group_size = 4;

    public static void setup() {
        TinyConfig.init(DuckMod.MOD_ID, ModConfigImpl.class);
        TinyConfig.write(DuckMod.MOD_ID);
        ModConfig.Goose.WEIGHT = () -> goose_spawn_weight;
        ModConfig.Goose.GROUP_SIZE = () -> goose_group_size;
        ModConfig.Duck.WEIGHT = () -> duck_spawn_weight;
        ModConfig.Duck.GROUP_SIZE = () -> duck_group_size;
    }
}
