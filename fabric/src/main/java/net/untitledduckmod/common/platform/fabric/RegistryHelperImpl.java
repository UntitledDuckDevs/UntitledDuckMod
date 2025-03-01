package net.untitledduckmod.common.platform.fabric;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.untitledduckmod.DuckMod;

import java.util.function.Function;
import java.util.function.Supplier;

public class RegistryHelperImpl {
    public static <T extends Item> Supplier<T> registerItem(String name, Function<Item.Settings, T> factory, Item.Settings settings) {
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, DuckMod.id(name));
        T item = factory.apply(settings.registryKey(key));
        T registry = Registry.register(Registries.ITEM, key, item);
        return () -> registry;
    }

    public static Supplier<SpawnEggItem> registerSpawnEggItem
            (
            String name, Supplier<? extends EntityType<? extends MobEntity>> type
            ) {
        return registerItem(name, (settings) -> new SpawnEggItem(type.get(), settings), new Item.Settings());
    }

    public static <T extends EntityType<?>> Supplier<T> registerEntity(String name, Supplier<T> entityType) {
        T registry = Registry.register(Registries.ENTITY_TYPE, DuckMod.id(name), entityType.get());
        return () -> registry;
    }

    public static Supplier<SoundEvent> registerSoundEvent(String name) {
        var registry = Registry.register(Registries.SOUND_EVENT, DuckMod.id(name), SoundEvent.of(DuckMod.id(name)));
        return () -> registry;
    }

    public static RegistryEntry<Potion> registerPotion(String name, Supplier<Potion> potion) {
        return Registry.registerReference(Registries.POTION, DuckMod.id(name), potion.get());
    }

    public static RegistryEntry<StatusEffect> registerStatusEffect(String name, Supplier<StatusEffect> statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, DuckMod.id(name), statusEffect.get());
    }
}
