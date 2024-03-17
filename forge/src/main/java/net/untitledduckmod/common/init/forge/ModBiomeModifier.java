package net.untitledduckmod.common.init.forge;

import com.mojang.serialization.Codec;
import net.untitledduckmod.DuckMod;
import net.untitledduckmod.common.platform.forge.RegistryHelperImpl;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBiomeModifier implements BiomeModifier {
    private static final String BIOME_MODIFIER_NAME = "untitled_spawns";
    private static final RegistryObject<Codec<? extends BiomeModifier>> SERIALIZER;

    static {
        SERIALIZER = RegistryObject.create(DuckMod.id(BIOME_MODIFIER_NAME), ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, DuckMod.MOD_ID);
    }

    @Override
    public void modify(RegistryEntry<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.ADD) {
            ModEntityTypesImpl.addBiomeSpawns(biome, builder);
        }
    }

    public Codec<? extends BiomeModifier> codec()
    {
        return SERIALIZER.get();
    }

    public static Codec<ModBiomeModifier> makeCodec() {
        return Codec.unit(ModBiomeModifier::new);
    }

    public static void init() {
        RegistryHelperImpl.registerBiomeModifier(BIOME_MODIFIER_NAME, ModBiomeModifier::makeCodec);
    }
}
