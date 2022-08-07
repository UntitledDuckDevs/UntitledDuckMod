package net.untitledduckmod.fabric;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.untitledduckmod.DuckMod;
import net.untitledduckmod.ModConfig;
import net.untitledduckmod.ModEntityTypes;
import net.untitledduckmod.duck.DuckEntity;
import net.untitledduckmod.goose.GooseEntity;
import net.untitledduckmod.items.DuckEggEntity;
import net.untitledduckmod.registration.EntityTypeBuilders;

import static net.untitledduckmod.ModEntityTypes.DUCK_BIOMES;
import static net.untitledduckmod.ModEntityTypes.GOOSE_BIOMES;

public class ModEntityTypesImpl {
    public static EntityType<DuckEntity> DUCK;
    public static EntityType<DuckEggEntity> DUCK_EGG;
    public static EntityType<GooseEntity> GOOSE;
    public static EntityType<DuckEggEntity> GOOSE_EGG;

    public static void register(Object optionalEvent) {
        DUCK = Registry.register(Registry.ENTITY_TYPE, new Identifier(DuckMod.MOD_ID, "duck"), EntityTypeBuilders.DUCK.get());
        DUCK_EGG = Registry.register(Registry.ENTITY_TYPE, new Identifier(DuckMod.MOD_ID, "duck_egg"), EntityTypeBuilders.DUCK_EGG.get());
        GOOSE = Registry.register(Registry.ENTITY_TYPE, new Identifier(DuckMod.MOD_ID, "goose"), EntityTypeBuilders.GOOSE.get());
        GOOSE_EGG = Registry.register(Registry.ENTITY_TYPE, new Identifier(DuckMod.MOD_ID, "goose_egg"), EntityTypeBuilders.GOOSE_EGG.get());
    }

    public static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(ModEntityTypesImpl.DUCK, DuckEntity.getDefaultAttributes());
        FabricDefaultAttributeRegistry.register(ModEntityTypesImpl.GOOSE, GooseEntity.getDefaultAttributes());
    }

    public static void setupSpawning() {
        SpawnRestrictionAccessor.callRegister(DUCK, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, (a,b,c,d,e) -> true);
        SpawnRestrictionAccessor.callRegister(GOOSE, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, (a,b,c,d,e) -> true);
        // BiomeModifications is experimental but approved
        BiomeModifications.addSpawn(context -> context.hasTag(DUCK_BIOMES), SpawnGroup.CREATURE, ModEntityTypes.getDuck(),
                ModConfig.Duck.WEIGHT.get(),
                ModConfig.Duck.GROUP_SIZE.get(),
                ModConfig.Duck.GROUP_SIZE.get());
        BiomeModifications.addSpawn(context -> context.hasTag(GOOSE_BIOMES), SpawnGroup.CREATURE, ModEntityTypes.getGoose(),
                ModConfig.Goose.WEIGHT.get(),
                ModConfig.Goose.GROUP_SIZE.get(),
                ModConfig.Goose.GROUP_SIZE.get());
    }

    public static EntityType<DuckEntity> getDuck() {
        return DUCK;
    }

    public static EntityType<DuckEggEntity> getDuckEgg() {
        return DUCK_EGG;
    }

    public static EntityType<GooseEntity> getGoose() {
        return GOOSE;
    }

    public static EntityType<DuckEggEntity> getGooseEgg() {
        return GOOSE_EGG;
    }
}
