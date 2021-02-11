package net.untitledduckmod.forge;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.attribute.DefaultAttributeRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.untitledduckmod.DuckEntity;
import net.untitledduckmod.DuckMod;
import net.untitledduckmod.SpawningSettings;
import net.untitledduckmod.items.DuckEggEntity;
import net.untitledduckmod.registration.EntityTypeBuilders;

import java.util.List;

public class EntityTypesImpl {
    public static final RegistryObject<EntityType<DuckEntity>> DUCK = RegistryObject.of(new Identifier(DuckMod.MOD_ID, "duck"), ForgeRegistries.ENTITIES);
    public static final RegistryObject<EntityType<DuckEggEntity>> DUCK_EGG = RegistryObject.of(new Identifier(DuckMod.MOD_ID, "duck_egg"), ForgeRegistries.ENTITIES);

    public static void register(Object optionalEvent) {
        assert optionalEvent != null;
        RegistryEvent.Register<EntityType<?>> event = (RegistryEvent.Register<EntityType<?>>) optionalEvent;

        event.getRegistry().register(EntityTypeBuilders.DUCK.get().setRegistryName(DuckMod.MOD_ID, "duck"));
        event.getRegistry().register(EntityTypeBuilders.DUCK_EGG.get().setRegistryName(DuckMod.MOD_ID, "duck_egg"));
    }

    public static void registerAttributes() {
        DefaultAttributeRegistry.put(DUCK.get(), DuckEntity.getDefaultAttributes().build());
    }

    public static void setupSpawning() {
        SpawnRestriction.register(DUCK.get(), SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
        MinecraftForge.EVENT_BUS.addListener(EntityTypesImpl::onBiomeLoading);
    }

    private static void onBiomeLoading(BiomeLoadingEvent event) {
        if (event.isCanceled()) {
            return;
        }
        if (event.getName() == null)
            return;
        List<SpawnSettings.SpawnEntry> spawner = event.getSpawns().getSpawner(SpawnGroup.CREATURE);
        spawner.add(new SpawnSettings.SpawnEntry(DUCK.get(), SpawningSettings.WEIGHT, SpawningSettings.MIN_GROUP, SpawningSettings.MAX_GROUP));
    }

    public static EntityType<DuckEntity> getDuck() {
        return DUCK.get();
    }

    public static EntityType<DuckEggEntity> getDuckEgg() {
        return DUCK_EGG.get();
    }
}
