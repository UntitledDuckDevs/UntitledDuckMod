package net.untitledduckmod.common.config;

import dev.architectury.injectables.annotations.ExpectPlatform;

import java.util.List;

public class UntitledConfig {

    @ExpectPlatform
    public static int duckWeight() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static int duckMinGroupSize() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static int duckMaxGroupSize() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static double duckFishingChange() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static int gooseWeight() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static int gooseMinGroupSize() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static int gooseMaxGroupSize() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static float foodHealingValue() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static List<? extends String> intimidationBlacklist() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void setup() {
        throw new AssertionError();
    }
}
