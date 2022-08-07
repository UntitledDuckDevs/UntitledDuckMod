package net.untitledduckmod.forge;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.untitledduckmod.duck.DuckEntity;
import net.untitledduckmod.DuckMod;
import net.untitledduckmod.forge.duck.DuckEntityForge;
import net.untitledduckmod.forge.goose.GooseEntityForge;
import net.untitledduckmod.goose.GooseEntity;
import net.untitledduckmod.ModConfig;
import net.untitledduckmod.items.DuckEggEntity;
import net.untitledduckmod.registration.EntityTypeBuilders;

import java.util.List;
import java.util.function.Supplier;

import static net.untitledduckmod.registration.EntityTypeBuilders.build;

public class ModEntityTypesImpl {
    private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, DuckMod.MOD_ID);

    public final static Supplier<EntityType<DuckEntityForge>> DUCK_FORGE_BUILDER = () -> (EntityType<DuckEntityForge>) build("duck", EntityType.Builder.create(DuckEntityForge::new, SpawnGroup.CREATURE).setDimensions(0.6f, 0.6f).maxTrackingRange(10));
    public final static Supplier<EntityType<GooseEntityForge>> GOOSE_FORGE_BUILDER = () -> (EntityType<GooseEntityForge>) build("goose", EntityType.Builder.create(GooseEntityForge::new, SpawnGroup.CREATURE).setDimensions(0.7f, 1.2f).maxTrackingRange(10));
    public static final RegistryObject<EntityType<DuckEntityForge>> DUCK = ENTITIES.register("duck", DUCK_FORGE_BUILDER);
    public static final RegistryObject<EntityType<DuckEggEntity>> DUCK_EGG = ENTITIES.register("duck_egg", EntityTypeBuilders.DUCK_EGG);
    public static final RegistryObject<EntityType<GooseEntityForge>> GOOSE = ENTITIES.register("goose", GOOSE_FORGE_BUILDER);
    public static final RegistryObject<EntityType<DuckEggEntity>> GOOSE_EGG = ENTITIES.register("goose_egg", EntityTypeBuilders.GOOSE_EGG);

    public static void register(Object optionalEvent) {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ENTITIES.register(bus);
    }

    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(DUCK.get(), DuckEntity.getDefaultAttributes().add(ForgeMod.SWIM_SPEED.get(), DuckEntity.SWIM_SPEED_MULTIPLIER).build());
        event.put(GOOSE.get(), GooseEntity.getDefaultAttributes().add(ForgeMod.SWIM_SPEED.get(), GooseEntity.SWIM_SPEED_MULTIPLIER).build());
    }

    public static void setupSpawning() {
        SpawnRestriction.register(DUCK.get(), SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, (a,b,c,d,e) -> true);
        SpawnRestriction.register(GOOSE.get(), SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, (a,b,c,d,e) -> true);
    }

    public static EntityType<DuckEntityForge> getDuck() {
        return DUCK.get();
    }

    public static EntityType<DuckEggEntity> getDuckEgg() {
        return DUCK_EGG.get();
    }

    public static EntityType<GooseEntityForge> getGoose() {
        return GOOSE.get();
    }

    public static EntityType<DuckEggEntity> getGooseEgg() {
        return GOOSE_EGG.get();
    }
}
