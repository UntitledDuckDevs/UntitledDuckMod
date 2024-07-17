package net.untitledduckmod.common.platform.forge;

import com.mojang.serialization.Codec;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.sound.SoundEvent;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.untitledduckmod.DuckMod;
import net.untitledduckmod.common.entity.forge.DuckEntityForge;
import net.untitledduckmod.common.entity.forge.GooseEntityForge;

import java.util.function.Supplier;

public class RegistryHelperImpl {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DuckMod.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, DuckMod.MOD_ID);
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, DuckMod.MOD_ID);
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, DuckMod.MOD_ID);
    public static final DeferredRegister<StatusEffect> STATUS_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, DuckMod.MOD_ID);
    public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, DuckMod.MOD_ID);

    public static <T extends Item> Supplier<T> registerItem(String name, Supplier<T> item) {
        return ITEMS.register(name, item);
    }

    public static <T extends Item> Supplier<T> registerSpawnEggItem(
            String name, Supplier<? extends EntityType<? extends MobEntity>> type,
            int primaryColor, int secondaryColor, Item.Settings settings
    ) {
        return (Supplier<T>) registerItem(name, () -> new ForgeSpawnEggItem(type, primaryColor, secondaryColor, settings));
    }

    public static  <T extends EntityType<?>> Supplier<T> registerEntity(String name, Supplier<T> entityType) {
        if (name.equals("duck")) {
            entityType = () -> (T) EntityType.Builder.create(DuckEntityForge::new, SpawnGroup.CREATURE).setDimensions(0.6f, 0.6f).maxTrackingRange(10).build(DuckMod.stringID("duck"));
        } else if (name.equals("goose")) {
            entityType = () -> (T) EntityType.Builder.create(GooseEntityForge::new, SpawnGroup.CREATURE).setDimensions(0.7f, 1.2f).maxTrackingRange(10).build(DuckMod.stringID("goose"));
        }
        return ENTITY_TYPES.register(name, entityType);
    }

    public static Supplier<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.of(DuckMod.id(name)));
    }

    public static <T extends Potion> Supplier<T> registerPotion(String name, Supplier<T> potion) {
        return POTIONS.register(name, potion);
    }

    public static <T extends StatusEffect> Supplier<T> registerStatusEffect(String name, Supplier<T> statusEffect) {
        return STATUS_EFFECTS.register(name, statusEffect);
    }

    public static <T extends Codec<? extends BiomeModifier>> void registerBiomeModifier(String name, Supplier<T> biomeModifier) {
        BIOME_MODIFIERS.register(name, biomeModifier);
    }
}
