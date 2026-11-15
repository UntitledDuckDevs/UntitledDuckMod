package net.untitledduckmod.common.config.fabric;

import com.google.common.collect.Lists;
import net.untitledduckmod.DuckMod;

import java.util.List;

public class UntitledConfigImpl extends TinyConfig {
    @Entry(category = "common")
    public static boolean enable_force_eat = false;
    @Entry(category = "common")
    public static int force_eat_random_min_tick = 500;
    @Entry(category = "common")
    public static int force_eat_random_max_tick = 1000;
    @Entry(category = "common")
    public static int min_egg_lay_time = 6000;
    @Entry(category = "common")
    public static int max_egg_lay_time = 12000;

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
    @Entry(category = "ducks")
    public static boolean duck_tamed_not_follow = false;
    @Entry(category = "ducks")
    public static boolean duck_baby_random_size = true;

    @Entry(category = "geese")
    public static int goose_spawn_weight = 4;
    @Entry(category = "geese")
    public static int goose_min_group_size = 2;
    @Entry(category = "geese")
    public static int goose_max_group_size = 4;
    @Entry(category = "geese")
    public static boolean goose_tamed_not_follow = false;
    @Entry(category = "geese")
    public static boolean goose_baby_random_size = true;

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

    public static boolean duckTamedNotFollow() {
        return duck_tamed_not_follow;
    }

    public static boolean duckBabyRandomSize() {
        return duck_baby_random_size;
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

    public static boolean gooseTamedNotFollow() {
        return goose_tamed_not_follow;
    }

    public static boolean gooseBabyRandomSize() {
        return goose_baby_random_size;
    }

    public static boolean enableForceEat() {
        return enable_force_eat;
    }

    public static int forceEatRandomMinTick() {
        return force_eat_random_min_tick;
    }

    public static int forceEatRandomMaxTick() {
        return force_eat_random_max_tick;
    }

    public static int minEggLayTime() {
        return min_egg_lay_time;
    }

    public static int maxEggLayTime() {
        return max_egg_lay_time;
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
