package net.untitledduckmod.common.init.neoforge;

import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnLocationTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.untitledduckmod.common.config.UntitledConfig;
import net.untitledduckmod.common.entity.DuckEntity;
import net.untitledduckmod.common.entity.GooseEntity;
import net.untitledduckmod.common.init.ModEntityTypes;
import net.untitledduckmod.common.init.ModTags;

public class ModEntityTypesImpl {

    public static void registerAttributes(Object optionalEvent) {
        if (optionalEvent instanceof EntityAttributeCreationEvent event) {
            event.put(ModEntityTypes.getDuck(), DuckEntity.getDefaultAttributes().add(NeoForgeMod.SWIM_SPEED, DuckEntity.SWIM_SPEED_MULTIPLIER).build());
            event.put(ModEntityTypes.getGoose(), GooseEntity.getDefaultAttributes().add(NeoForgeMod.SWIM_SPEED, GooseEntity.SWIM_SPEED_MULTIPLIER).build());
        }
    }

    public static void setupSpawning(Object optionalEvent) {
        if (optionalEvent instanceof RegisterSpawnPlacementsEvent event) {
            event.register(ModEntityTypes.getDuck(), SpawnLocationTypes.UNRESTRICTED, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DuckEntity::checkDuckSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
            event.register(ModEntityTypes.getGoose(), SpawnLocationTypes.UNRESTRICTED, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, GooseEntity::checkGooseSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        }
    }

    public static void addBiomeSpawns(RegistryEntry<Biome> biome, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (biome.isIn(ModTags.BiomeTags.DUCK_BIOMES)) {
            builder.getMobSpawnSettings().getSpawner(SpawnGroup.CREATURE).add(new SpawnSettings.SpawnEntry(ModEntityTypes.getDuck(), UntitledConfig.duckWeight(), UntitledConfig.duckMinGroupSize(), UntitledConfig.duckMaxGroupSize()));
        }
        if (biome.isIn(ModTags.BiomeTags.GOOSE_BIOMES)) {
            builder.getMobSpawnSettings().getSpawner(SpawnGroup.CREATURE).add(new SpawnSettings.SpawnEntry(ModEntityTypes.getGoose(), UntitledConfig.gooseWeight(), UntitledConfig.gooseMinGroupSize(), UntitledConfig.gooseMaxGroupSize()));
        }
    }

}
