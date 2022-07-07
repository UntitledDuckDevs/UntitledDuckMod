package net.untitledduckmod;

import dev.architectury.injectables.annotations.ExpectPlatform;

import java.util.function.Supplier;

public class ModConfig {
    public static class Duck {
        public static Supplier<Integer> WEIGHT;
        public static Supplier<Integer> GROUP_SIZE;
    }

    public static class Goose {
        public static Supplier<Integer> WEIGHT;
        public static Supplier<Integer> GROUP_SIZE;
    }

    @ExpectPlatform
    public static void setup() {
        throw new AssertionError();
    }
}
