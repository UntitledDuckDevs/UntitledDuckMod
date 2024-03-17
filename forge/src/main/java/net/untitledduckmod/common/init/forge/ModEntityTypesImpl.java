package net.untitledduckmod.common.init.forge;

import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.untitledduckmod.common.config.UntitledConfig;
import net.untitledduckmod.common.init.ModEntityTypes;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.world.Heightmap;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.untitledduckmod.common.entity.DuckEntity;
import net.untitledduckmod.common.entity.GooseEntity;
import net.untitledduckmod.common.init.ModTags;

public class ModEntityTypesImpl {

    public static void registerAttributes(Object optionalEvent) {
        if (optionalEvent instanceof EntityAttributeCreationEvent event) {
            event.put(ModEntityTypes.getDuck(), DuckEntity.getDefaultAttributes().add(ForgeMod.SWIM_SPEED.get(), DuckEntity.SWIM_SPEED_MULTIPLIER).build());
            event.put(ModEntityTypes.getGoose(), GooseEntity.getDefaultAttributes().add(ForgeMod.SWIM_SPEED.get(), GooseEntity.SWIM_SPEED_MULTIPLIER).build());
        }
    }

    public static void setupSpawning(Object optionalEvent) {
        if (optionalEvent instanceof SpawnPlacementRegisterEvent event) {
            event.register(ModEntityTypes.getDuck(), SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DuckEntity::checkDuckSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
            event.register(ModEntityTypes.getGoose(), SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, GooseEntity::checkGooseSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
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
