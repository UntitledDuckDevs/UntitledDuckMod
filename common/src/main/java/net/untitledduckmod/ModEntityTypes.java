package net.untitledduckmod;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.untitledduckmod.duck.DuckEntity;
import net.untitledduckmod.goose.GooseEntity;
import net.untitledduckmod.items.DuckEggEntity;

public class ModEntityTypes {
    public static TagKey<Biome> DUCK_BIOMES = TagKey.of(RegistryKeys.BIOME, new Identifier(DuckMod.MOD_ID, "duck_spawn"));
    public static TagKey<Biome> GOOSE_BIOMES = TagKey.of(RegistryKeys.BIOME, new Identifier(DuckMod.MOD_ID, "goose_spawn"));

    @ExpectPlatform
    public static void register(Object optionalEvent) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerAttributes(Object optionalEvent) {
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
