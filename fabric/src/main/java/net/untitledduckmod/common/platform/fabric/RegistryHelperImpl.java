package net.untitledduckmod.common.platform.fabric;

import net.minecraft.component.ComponentType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.untitledduckmod.DuckMod;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class RegistryHelperImpl {
    public static <T extends Item> Supplier<T> registerItem(String name, Supplier<T> item) {
        T registry = Registry.register(Registries.ITEM, DuckMod.id(name), item.get());
        return () -> registry;
    }

    public static <T extends Item> Supplier<T> registerSpawnEggItem
            (
            String name, Supplier<? extends EntityType<? extends MobEntity>> type,
            int primaryColor, int secondaryColor, Item.Settings settings
            ) {
        return (Supplier<T>) registerItem(name, () -> new SpawnEggItem(type.get(), primaryColor, secondaryColor, settings));
    }

    public static <T extends EntityType<?>> Supplier<T> registerEntity(String name, Supplier<T> entityType) {
        T registry = Registry.register(Registries.ENTITY_TYPE, DuckMod.id(name), entityType.get());
        return () -> registry;
    }

    public static Supplier<SoundEvent> registerSoundEvent(String name) {
        var registry = Registry.register(Registries.SOUND_EVENT, DuckMod.id(name), SoundEvent.of(DuckMod.id(name)));
        return () -> registry;
    }

    public static <T extends Potion> Supplier<T> registerPotion(String name, Supplier<T> potion) {
        var registry = Registry.register(Registries.POTION, DuckMod.id(name), potion.get());
        return () -> registry;
    }

    public static <T extends StatusEffect> Supplier<T> registerStatusEffect(String name, Supplier<T> statusEffect) {
        var registry = Registry.register(Registries.STATUS_EFFECT, DuckMod.id(name), statusEffect.get());
        return () -> registry;
    }
}
