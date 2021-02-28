package net.untitledduckmod.fabric;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.untitledduckmod.DuckEntity;
import net.untitledduckmod.DuckMod;
import net.untitledduckmod.ModEntityTypes;
import net.untitledduckmod.ModSpawningSettings;
import net.untitledduckmod.items.DuckEggEntity;
import net.untitledduckmod.registration.EntityTypeBuilders;

public class ModEntityTypesImpl {
    public static EntityType<DuckEntity> DUCK;
    public static EntityType<DuckEggEntity> DUCK_EGG;

    public static void register(Object optionalEvent) {
        DUCK = Registry.register(Registry.ENTITY_TYPE, new Identifier(DuckMod.MOD_ID, "duck"), EntityTypeBuilders.DUCK.get());
        DUCK_EGG = Registry.register(Registry.ENTITY_TYPE, new Identifier(DuckMod.MOD_ID, "duck_egg"), EntityTypeBuilders.DUCK_EGG.get());
    }

    public static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(ModEntityTypesImpl.DUCK, net.untitledduckmod.DuckEntity.getDefaultAttributes());
    }

    public static void setupSpawning() {
        SpawnRestrictionAccessor.callRegister(DUCK, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
        BiomeModifications.addSpawn(context -> true, SpawnGroup.CREATURE, ModEntityTypes.getDuck(), ModSpawningSettings.WEIGHT, ModSpawningSettings.MIN_GROUP, ModSpawningSettings.MAX_GROUP);
    }

    public static EntityType<DuckEntity> getDuck() {
        return DUCK;
    }

    public static EntityType<DuckEggEntity> getDuckEgg() {
        return DUCK_EGG;
    }
}
