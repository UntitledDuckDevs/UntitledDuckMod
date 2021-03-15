package net.untitledduckmod;

import me.shedaniel.architectury.annotations.ExpectPlatform;
import net.minecraft.entity.EntityType;
import net.untitledduckmod.items.DuckEggEntity;

public class ModEntityTypes {
    @ExpectPlatform
    public static void register(Object optionalEvent) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerAttributes() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void setupSpawning() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static EntityType<DuckEntity> getDuck() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static EntityType<DuckEggEntity> getDuckEgg() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static EntityType<GooseEntity> getGoose() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static EntityType<DuckEggEntity> getGooseEgg() {
        throw new AssertionError();
    }
}
