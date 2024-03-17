package net.untitledduckmod.common.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;
import net.untitledduckmod.DuckMod;

public class ModTags {

    public static class BiomeTags {

        public static TagKey<Biome> DUCK_BIOMES = create("duck_spawn");
        public static TagKey<Biome> GOOSE_BIOMES = create("goose_spawn");

        private static TagKey<Biome> create(String name) {
            return TagKey.of(RegistryKeys.BIOME, DuckMod.id(name));
        }

    }

    public static class BlockTags {
        public static final TagKey<Block> DUCKS_SPAWNABLE_ON = create("ducks_spawnable_on");
        public static final TagKey<Block> GEESE_SPAWNABLE_ON = create("geese_spawnable_on");

        private static TagKey<Block> create(String name) {
            return TagKey.of(RegistryKeys.BLOCK, DuckMod.id(name));
        }
    }

    public static class ItemTags {
        public static final TagKey<Item> DUCK_BREEDING_FOOD = create("duck_breeding_food");
        public static final TagKey<Item> GOOSE_FOOD = create("goose_food");
        public static final TagKey<Item> GOOSE_BREEDING_FOOD = create("goose_breeding_food");
        public static final TagKey<Item> GOOSE_TAMING_FOOD = create("goose_taming_food");
        private static TagKey<Item> create(String name) {
            return TagKey.of(RegistryKeys.ITEM, DuckMod.id(name));
        }
    }

}
