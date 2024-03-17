package net.untitledduckmod.common.init;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.untitledduckmod.DuckMod;
import net.untitledduckmod.common.entity.DuckEntity;
import net.untitledduckmod.common.entity.GooseEntity;
import net.untitledduckmod.common.entity.DuckEggEntity;
import net.untitledduckmod.common.platform.RegistryHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;

import java.util.function.Supplier;

public class ModEntityTypes {

    public final static Supplier<EntityType<DuckEntity>> DUCK;
    public final static Supplier<EntityType<DuckEggEntity>> DUCK_EGG;
    public final static Supplier<EntityType<GooseEntity>> GOOSE;
    public final static Supplier<EntityType<DuckEggEntity>> GOOSE_EGG;

    static {
        DUCK = RegistryHelper.registerEntity("duck", () -> EntityType.Builder.create(DuckEntity::new, SpawnGroup.CREATURE).setDimensions(0.6f, 0.6f).maxTrackingRange(10).build(DuckMod.stringID("duck")));
        DUCK_EGG = RegistryHelper.registerEntity("duck_egg", () -> EntityType.Builder.<DuckEggEntity>create(DuckEggEntity::new, SpawnGroup.MISC).setDimensions(0.25F, 0.25F).maxTrackingRange(4).trackingTickInterval(10).build(DuckMod.stringID("duck_egg")));
        GOOSE = RegistryHelper.registerEntity("goose", () -> EntityType.Builder.create(GooseEntity::new, SpawnGroup.CREATURE).setDimensions(0.7f, 1.2f).maxTrackingRange(10).build(DuckMod.stringID("goose")));
        GOOSE_EGG = RegistryHelper.registerEntity("goose_egg", () -> EntityType.Builder.<DuckEggEntity>create(DuckEggEntity::new, SpawnGroup.MISC).setDimensions(0.25F, 0.25F).maxTrackingRange(4).trackingTickInterval(10).build(DuckMod.stringID("goose_egg")));
    }

    // Call during mod initialization to ensure registration
    public static void init() {
    }

    @ExpectPlatform
    public static void registerAttributes(Object optionalEvent) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void setupSpawning(Object optionalEvent) {
        throw new AssertionError();
    }

    public static EntityType<DuckEntity> getDuck() {
        return DUCK.get();
    }

    public static EntityType<DuckEggEntity> getDuckEgg() {
        return DUCK_EGG.get();
    }

    public static EntityType<GooseEntity> getGoose() {
        return GOOSE.get();
    }

    public static EntityType<DuckEggEntity> getGooseEgg() {
        return GOOSE_EGG.get();
    }
}
