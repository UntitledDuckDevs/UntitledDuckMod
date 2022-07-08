package net.untitledduckmod.forge;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fml.ModLoadingContext;
import net.untitledduckmod.ModConfig;

public class ModConfigImpl {
    public static ForgeConfigSpec.IntValue GOOSE_WEIGHT;
    public static ForgeConfigSpec.IntValue DUCK_WEIGHT;

    public static ForgeConfigSpec.IntValue GOOSE_GROUP_SIZE;
    public static ForgeConfigSpec.IntValue DUCK_GROUP_SIZE;

    public static final String CATEGORY_DUCK = "duck";
    public static final String CATEGORY_GOOSE = "goose";

    public static final ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();

    public static void duckConfig(ForgeConfigSpec.Builder configBuilder) {
        configBuilder.comment("Settings for the duck").push(CATEGORY_DUCK);
        DUCK_WEIGHT = configBuilder.comment("The spawn weight of the duck mob." +
                        "The higher it is, the higher are the spawn rates. See https://minecraft.fandom.com/wiki/Spawn#Animals for an explanation.")
                .worldRestart()
                .defineInRange("duck_weight", 5, 0, Integer.MAX_VALUE);
        DUCK_GROUP_SIZE = configBuilder.comment("How many ducks should spawn at once in a group.").worldRestart()
                .defineInRange("duck_group_size", 4, 0, Integer.MAX_VALUE);
        configBuilder.pop();

        ModConfig.Duck.WEIGHT = () -> DUCK_WEIGHT.get();
        ModConfig.Duck.GROUP_SIZE = () -> DUCK_GROUP_SIZE.get();
    }
    public static void gooseConfig(ForgeConfigSpec.Builder configBuilder) {
        configBuilder.comment("Settings for the goose").push(CATEGORY_GOOSE);
        GOOSE_WEIGHT = configBuilder.comment("The spawn weight of the goose mob." +
                        "The higher it is, the higher are the spawn rates. See https://minecraft.fandom.com/wiki/Spawn#Animals for an explanation.")
                .worldRestart()
                .defineInRange("goose_weight", 4, 0, Integer.MAX_VALUE);

        GOOSE_GROUP_SIZE = configBuilder.comment("How many geese should spawn at once in a group.").worldRestart()
                .defineInRange("goose_group_size", 4, 0, Integer.MAX_VALUE);
        configBuilder.pop();

        ModConfig.Goose.GROUP_SIZE = () -> GOOSE_GROUP_SIZE.get();
        ModConfig.Goose.WEIGHT = () -> GOOSE_WEIGHT.get();
    }

    public static void setup() {
        duckConfig(SERVER_BUILDER);
        gooseConfig(SERVER_BUILDER);
        ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.SERVER, SERVER_BUILDER.build());
    }
}
