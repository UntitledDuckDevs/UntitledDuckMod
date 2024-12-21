package net.untitledduckmod.common.config.fabric;

import com.google.common.collect.Lists;
import net.untitledduckmod.DuckMod;

import java.util.List;

public class UntitledConfigImpl extends TinyConfig {
    @Entry(category = "common", min = 0F, max = 100F)
    public static float food_healing_value = 0.5F;

    @Entry(category = "ducks")
    public static int duck_spawn_weight = 6;
    @Entry(category = "ducks")
    public static int duck_min_group_size = 2;
    @Entry(category = "ducks")
    public static int duck_max_group_size = 4;
    @Entry(category = "ducks", max = 1.0D)
    public static double duck_fishing_change = 0.5D;

    @Entry(category = "geese")
    public static int goose_spawn_weight = 4;
    @Entry(category = "geese")
    public static int goose_min_group_size = 2;
    @Entry(category = "geese")
    public static int goose_max_group_size = 4;

    @Entry(category = "intimidation")
    public static List<String> intimidation_blacklist = Lists.newArrayList("modid:test");

    public static int duckWeight() {
        return duck_spawn_weight;
    }

    public static int duckMinGroupSize() {
        return duck_min_group_size;
    }

    public static int duckMaxGroupSize() {
        return duck_max_group_size;
    }

    public static double duckFishingChange() {
        return duck_fishing_change;
    }

    public static int gooseWeight() {
        return goose_spawn_weight;
    }

    public static int gooseMinGroupSize() {
        return goose_min_group_size;
    }

    public static int gooseMaxGroupSize() {
        return goose_max_group_size;
    }

    public static float foodHealingValue() {
        return food_healing_value;
    }

    public static List<? extends String> intimidationBlacklist() {
        return intimidation_blacklist;
    }

    public static void setup() {
        TinyConfig.init(DuckMod.MOD_ID, UntitledConfigImpl.class);
        TinyConfig.write(DuckMod.MOD_ID);
    }

}
