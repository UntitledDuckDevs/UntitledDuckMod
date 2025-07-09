package net.untitledduckmod.common.config.neoforge;

import com.google.common.collect.Lists;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public class UntitledConfigImpl {
    public static final ModConfigSpec SERVER_SPEC;

    public static final ModConfigSpec.IntValue DUCK_WEIGHT;
    public static final ModConfigSpec.IntValue DUCK_MIN_GROUP_SIZE;
    public static final ModConfigSpec.IntValue DUCK_MAX_GROUP_SIZE;
    public static final ModConfigSpec.DoubleValue DUCK_FISHING_CHANGE;
    public static final ModConfigSpec.BooleanValue DUCK_TAMED_NOT_FOLLOW;
    public static final ModConfigSpec.BooleanValue DUCK_BABY_RANDOM_SIZE;

    public static final ModConfigSpec.IntValue GOOSE_WEIGHT;
    public static final ModConfigSpec.IntValue GOOSE_MIN_GROUP_SIZE;
    public static final ModConfigSpec.IntValue GOOSE_MAX_GROUP_SIZE;
    public static final ModConfigSpec.BooleanValue GOOSE_TAMED_NOT_FOLLOW;
    public static final ModConfigSpec.BooleanValue GOOSE_BABY_RANDOM_SIZE;

    public static final ModConfigSpec.DoubleValue FOOD_HEALING_VALUE;

    public static final ModConfigSpec.ConfigValue<List<? extends String>>  INTIMIDATION_BLACKLIST;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

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
        DUCK_TAMED_NOT_FOLLOW = builder
                .comment("No more following behavior when tamed.")
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
        GOOSE_TAMED_NOT_FOLLOW = builder
                .comment("No more following behavior when tamed.")
                .worldRestart()
                .define("goose_tamed_no_follow", false);
        GOOSE_BABY_RANDOM_SIZE = builder
                .comment("Baby model random size (0.25-0.7).")
                .worldRestart()
                .define("goose_baby_random_size", true);
        builder.pop();

        builder.push("common");
        FOOD_HEALING_VALUE = builder.comment("Food can heal the health value of duck & goose")
                .worldRestart()
                .defineInRange("food_healing_value", 0.5D, 0D, 100D);
        builder.pop();

        builder.push("intimidation");
        INTIMIDATION_BLACKLIST = builder.comment("Intimidate effects don't work on mobs in the list")
                .worldRestart()
                .defineList("intimidation_blacklist", Lists.newArrayList("modid:test"), s -> s instanceof String);
        builder.pop();

        SERVER_SPEC = builder.build();
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
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.SERVER, SERVER_SPEC);
    }
}
