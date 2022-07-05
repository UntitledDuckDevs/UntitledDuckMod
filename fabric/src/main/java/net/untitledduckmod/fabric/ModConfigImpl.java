package net.untitledduckmod.fabric;

import net.untitledduckmod.ModConfig;

public class ModConfigImpl {
    public static void setup() {
        ModConfig.Duck.WEIGHT = () -> 5;
        ModConfig.Goose.WEIGHT = () -> 4;
        ModConfig.Goose.GROUP_SIZE = () -> 4;
        ModConfig.Duck.GROUP_SIZE = () -> 4;
    }
}
