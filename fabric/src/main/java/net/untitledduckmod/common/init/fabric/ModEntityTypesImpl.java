package net.untitledduckmod.common.init.fabric;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnLocationTypes;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.world.Heightmap;
import net.untitledduckmod.common.config.UntitledConfig;
import net.untitledduckmod.common.entity.DuckEntity;
import net.untitledduckmod.common.entity.GooseEntity;
import net.untitledduckmod.common.init.ModEntityTypes;
import net.untitledduckmod.common.init.ModTags;

public class ModEntityTypesImpl {

    public static void registerAttributes(Object optionalEvent) {
        FabricDefaultAttributeRegistry.register(ModEntityTypes.getDuck(), DuckEntity.getDefaultAttributes());
        FabricDefaultAttributeRegistry.register(ModEntityTypes.getGoose(), GooseEntity.getDefaultAttributes());
    }

    public static void setupSpawning(Object optionalEvent) {
        SpawnRestriction.register(ModEntityTypes.getDuck(), SpawnLocationTypes.UNRESTRICTED, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DuckEntity::checkDuckSpawnRules);
        SpawnRestriction.register(ModEntityTypes.getGoose(), SpawnLocationTypes.UNRESTRICTED, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, GooseEntity::checkGooseSpawnRules);
        // BiomeModifications is experimental but approved
        BiomeModifications.addSpawn(context -> context.hasTag(ModTags.BiomeTags.DUCK_BIOMES), SpawnGroup.CREATURE, ModEntityTypes.getDuck(),
                UntitledConfig.duckWeight(),
                UntitledConfig.duckMinGroupSize(),
                UntitledConfig.duckMaxGroupSize());
        BiomeModifications.addSpawn(context -> context.hasTag(ModTags.BiomeTags.GOOSE_BIOMES), SpawnGroup.CREATURE, ModEntityTypes.getGoose(),
                UntitledConfig.gooseWeight(),
                UntitledConfig.gooseMinGroupSize(),
                UntitledConfig.gooseMaxGroupSize());
    }
}
