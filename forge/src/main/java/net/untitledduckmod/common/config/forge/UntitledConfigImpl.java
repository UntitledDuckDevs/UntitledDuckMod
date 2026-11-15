package net.untitledduckmod.common.config.forge;

import com.google.common.collect.Lists;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

import java.util.List;

public class UntitledConfigImpl {
    public static final ForgeConfigSpec COMMON_SPEC;

    public static final ForgeConfigSpec.IntValue DUCK_WEIGHT;
    public static final ForgeConfigSpec.IntValue DUCK_MIN_GROUP_SIZE;
    public static final ForgeConfigSpec.IntValue DUCK_MAX_GROUP_SIZE;
    public static final ForgeConfigSpec.DoubleValue DUCK_FISHING_CHANGE;
    public static final ForgeConfigSpec.BooleanValue DUCK_TAMED_NOT_FOLLOW;
    public static final ForgeConfigSpec.BooleanValue DUCK_BABY_RANDOM_SIZE;

    public static final ForgeConfigSpec.IntValue GOOSE_WEIGHT;
    public static final ForgeConfigSpec.IntValue GOOSE_MIN_GROUP_SIZE;
    public static final ForgeConfigSpec.IntValue GOOSE_MAX_GROUP_SIZE;
    public static final ForgeConfigSpec.BooleanValue GOOSE_TAMED_NOT_FOLLOW;
    public static final ForgeConfigSpec.BooleanValue GOOSE_BABY_RANDOM_SIZE;

    public static final ForgeConfigSpec.BooleanValue ENABLE_FORCE_EAT;
    public static final ForgeConfigSpec.IntValue FORCE_EAT_RANDOM_MIN_TICK;
    public static final ForgeConfigSpec.IntValue FORCE_EAT_RANDOM_MAX_TICK;
    public static final ForgeConfigSpec.IntValue MIN_EGG_LAY_TIME;
    public static final ForgeConfigSpec.IntValue MAX_EGG_LAY_TIME;
    public static final ForgeConfigSpec.DoubleValue FOOD_HEALING_VALUE;

    public static final ForgeConfigSpec.ConfigValue<List<? extends String>>  INTIMIDATION_BLACKLIST;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.comment("Untitled Duck Mod").push("duck");
        DUCK_WEIGHT = builder.comment("The spawn weight of the duck mob." +
                        "The higher it is, the higher are the spawn rates. See https://minecraft.fandom.com/wiki/Spawn#Animals for an explanation.")
                .worldRestart()
                .defineInRange("duck_spawn_weight", 6, 0, Integer.MAX_VALUE);
        DUCK_MIN_GROUP_SIZE = builder.comment("The minimum number of ducks that should be spawned at once in a group.")
                .worldRestart()
                .defineInRange("duck_min_group_size", 2, 0, Integer.MAX_VALUE);
        DUCK_MAX_GROUP_SIZE = builder
                .comment("The maximum number of ducks that should be spawned at once in a group.")
                .worldRestart()
                .defineInRange("duck_max_group_size", 4, 0, Integer.MAX_VALUE);
        DUCK_FISHING_CHANGE = builder
                .comment("Chance of ducks successfully fishing.")
                .worldRestart()
                .defineInRange("duck_fishing_change", 0.5D, 0.0D, 1);
        DUCK_TAMED_NOT_FOLLOW = builder.comment("No more following behavior when tamed.")
                .worldRestart()
                .define("duck_tamed_no_follow", false);
        DUCK_BABY_RANDOM_SIZE = builder
                .comment("Baby model random size (0.25-0.7).")
                .worldRestart()
                .define("duck_baby_random_size", true);
        builder.pop();

        builder.push("goose");
        GOOSE_WEIGHT = builder.comment("The spawn weight of the goose mob." +
                        "The higher it is, the higher are the spawn rates. See https://minecraft.fandom.com/wiki/Spawn#Animals for an explanation.")
                .worldRestart()
                .defineInRange("goose_spawn_weight", 4, 0 , Integer.MAX_VALUE);
        GOOSE_MIN_GROUP_SIZE = builder.comment("The minimum number of geese that should be spawned at once in a group.")
                .worldRestart()
                .defineInRange("goose_min_group_size", 2, 0, Integer.MAX_VALUE);
        GOOSE_MAX_GROUP_SIZE = builder.comment("The maximum number of geese that should be spawned at once in a group.")
                .worldRestart()
                .defineInRange("goose_max_group_size", 4, 0, Integer.MAX_VALUE);
        GOOSE_TAMED_NOT_FOLLOW = builder.comment("No more following behavior when tamed.")
                .worldRestart()
                .define("goose_tamed_no_follow", false);
        GOOSE_BABY_RANDOM_SIZE = builder
                .comment("Baby model random size (0.25-0.7).")
                .worldRestart()
                .define("goose_baby_random_size", true);
        builder.pop();

        builder.push("common");
        ENABLE_FORCE_EAT = builder.comment("Enable/disable automatic force-eating for duck/goose")
                .worldRestart()
                .define("enable_force_eat", false);
        FORCE_EAT_RANDOM_MIN_TICK = builder.comment("Min tick before duck/goose auto-eat held food")
                .worldRestart()
                .defineInRange("force_eat_random_min_tick", 500, 0, Integer.MAX_VALUE);
        FORCE_EAT_RANDOM_MAX_TICK = builder.comment("Max tick before duck/goose auto-eat held food")
                .worldRestart()
                .defineInRange("force_eat_random_max_tick", 1000, 0, Integer.MAX_VALUE);
        MIN_EGG_LAY_TIME = builder.comment("Min egg lay time (Ticks)")
                .worldRestart()
                .defineInRange("min_egg_lay_time", 6000, 0, Integer.MAX_VALUE);
        MAX_EGG_LAY_TIME = builder.comment("Max egg lay time (Ticks)")
                .worldRestart()
                .defineInRange("max_egg_lay_time", 12000, 0, Integer.MAX_VALUE);
        FOOD_HEALING_VALUE = builder.comment("Food can heal the health value of duck & goose")
                .worldRestart()
                .defineInRange("food_healing_value", 0.5D, 0D, 100D);
        builder.pop();

        builder.push("intimidation");
        INTIMIDATION_BLACKLIST = builder.comment("Intimidate effects don't work on mobs in the list")
                .worldRestart()
                .defineList("intimidation_blacklist", Lists.newArrayList("modid:test"), s -> s instanceof String);
        builder.pop();

        COMMON_SPEC = builder.build();
    }

    public static int duckWeight() {
        return DUCK_WEIGHT.get();
    }

    public static int duckMinGroupSize() {
        return DUCK_MIN_GROUP_SIZE.get();
    }

    public static int duckMaxGroupSize() {
        return DUCK_MAX_GROUP_SIZE.get();
    }

    public static double duckFishingChange() {
        return DUCK_FISHING_CHANGE.get();
    }

    public static boolean duckTamedNotFollow() {
        return DUCK_TAMED_NOT_FOLLOW.get();
    }

    public static boolean duckBabyRandomSize() {
        return DUCK_BABY_RANDOM_SIZE.get();
    }

    public static int gooseWeight() {
        return GOOSE_WEIGHT.get();
    }

    public static int gooseMinGroupSize() {
        return GOOSE_MIN_GROUP_SIZE.get();
    }

    public static int gooseMaxGroupSize() {
        return GOOSE_MAX_GROUP_SIZE.get();
    }

    public static boolean gooseTamedNotFollow() {
        return GOOSE_TAMED_NOT_FOLLOW.get();
    }

    public static boolean gooseBabyRandomSize() {
        return GOOSE_BABY_RANDOM_SIZE.get();
    }

    public static boolean enableForceEat() {
        return ENABLE_FORCE_EAT.get();
    }

    public static int forceEatRandomMinTick() {
        return FORCE_EAT_RANDOM_MIN_TICK.get();
    }

    public static int forceEatRandomMaxTick() {
        return FORCE_EAT_RANDOM_MAX_TICK.get();
    }

    public static int minEggLayTime() {
        return MIN_EGG_LAY_TIME.get();
    }

    public static int maxEggLayTime() {
        return MAX_EGG_LAY_TIME.get();
    }


    public static float foodHealingValue() {
        try {
            return FOOD_HEALING_VALUE.get().floatValue();
        } catch (Exception e) {
            return 0.5F;
        }
    }

    public static List<? extends String> intimidationBlacklist() {
        return INTIMIDATION_BLACKLIST.get();
    }

    public static void setup() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_SPEC);
    }
}
