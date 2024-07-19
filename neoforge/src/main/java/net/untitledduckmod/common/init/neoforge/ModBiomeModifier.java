package net.untitledduckmod.common.init.neoforge;

import com.mojang.serialization.MapCodec;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.untitledduckmod.DuckMod;
import net.untitledduckmod.common.platform.neoforge.RegistryHelperImpl;

public class ModBiomeModifier implements BiomeModifier {
    private static final String BIOME_MODIFIER_NAME = "untitled_spawns";
    private static final DeferredHolder<MapCodec<? extends BiomeModifier>, MapCodec<ModBiomeModifier>> SERIALIZER;

    static {
        SERIALIZER = DeferredHolder.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, DuckMod.id(BIOME_MODIFIER_NAME));
    }

    @Override
    public void modify(RegistryEntry<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.ADD) {
            ModEntityTypesImpl.addBiomeSpawns(biome, builder);
        }
    }

    public MapCodec<? extends BiomeModifier> codec()
    {
        return SERIALIZER.get();
    }

    public static MapCodec<ModBiomeModifier> makeCodec() {
        return MapCodec.unit(ModBiomeModifier::new);
    }

    public static void init() {
        RegistryHelperImpl.registerBiomeModifier(BIOME_MODIFIER_NAME, ModBiomeModifier::makeCodec);
    }
}
