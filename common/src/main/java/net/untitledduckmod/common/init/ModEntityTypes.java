package net.untitledduckmod.common.init;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.untitledduckmod.DuckMod;
import net.untitledduckmod.common.entity.DuckEntity;
import net.untitledduckmod.common.entity.GooseEntity;
import net.untitledduckmod.common.entity.WaterfowlEggEntity;
import net.untitledduckmod.common.platform.RegistryHelper;

import java.util.function.Supplier;

public class ModEntityTypes {

    public final static Supplier<EntityType<DuckEntity>> DUCK;
    public final static Supplier<EntityType<WaterfowlEggEntity>> DUCK_EGG;
    public final static Supplier<EntityType<GooseEntity>> GOOSE;
    public final static Supplier<EntityType<WaterfowlEggEntity>> GOOSE_EGG;

    public final static RegistryKey<EntityType<?>> duckKey = RegistryKey.of(RegistryKeys.ENTITY_TYPE, DuckMod.id("duck"));
    public final static RegistryKey<EntityType<?>> duckEggKey = RegistryKey.of(RegistryKeys.ENTITY_TYPE, DuckMod.id("duck_egg"));
    public final static RegistryKey<EntityType<?>> gooseKey = RegistryKey.of(RegistryKeys.ENTITY_TYPE, DuckMod.id("goose"));
    public final static RegistryKey<EntityType<?>> gooseEggKey = RegistryKey.of(RegistryKeys.ENTITY_TYPE, DuckMod.id("goose_egg"));

    static {
        DUCK = RegistryHelper.registerEntity("duck", () -> EntityType.Builder.create(DuckEntity::new, SpawnGroup.CREATURE).dimensions(0.6f, 0.6f).maxTrackingRange(10).build(duckKey));
        DUCK_EGG = RegistryHelper.registerEntity("duck_egg", () -> EntityType.Builder.<WaterfowlEggEntity>create(WaterfowlEggEntity::new, SpawnGroup.MISC).dimensions(0.25F, 0.25F).maxTrackingRange(4).trackingTickInterval(10).dropsNothing().build(duckEggKey));
        GOOSE = RegistryHelper.registerEntity("goose", () -> EntityType.Builder.create(GooseEntity::new, SpawnGroup.CREATURE).dimensions(0.7f, 1.2f).maxTrackingRange(10).build(gooseKey));
        GOOSE_EGG = RegistryHelper.registerEntity("goose_egg", () -> EntityType.Builder.<WaterfowlEggEntity>create(WaterfowlEggEntity::new, SpawnGroup.MISC).dimensions(0.25F, 0.25F).maxTrackingRange(4).trackingTickInterval(10).dropsNothing().build(gooseEggKey));
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

    public static EntityType<WaterfowlEggEntity> getDuckEgg() {
        return DUCK_EGG.get();
    }

    public static EntityType<GooseEntity> getGoose() {
        return GOOSE.get();
    }

    public static EntityType<WaterfowlEggEntity> getGooseEgg() {
        return GOOSE_EGG.get();
    }
}
