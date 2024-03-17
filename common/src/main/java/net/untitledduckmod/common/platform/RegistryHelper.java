package net.untitledduckmod.common.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.sound.SoundEvent;

import java.util.function.Supplier;

public class RegistryHelper {
    @ExpectPlatform
    public static <T extends Item> Supplier<T> registerItem(String name, Supplier<T> item) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends Item> Supplier<T> registerSpawnEggItem(
            String name, Supplier<? extends EntityType<? extends MobEntity>> type,
            int primaryColor, int secondaryColor, Item.Settings settings
    ) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends EntityType<?>> Supplier<T> registerEntity(String name, Supplier<T> entityType) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Supplier<SoundEvent> registerSoundEvent(String name) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends Potion> Supplier<T> registerPotion(String name, Supplier<T> potion) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends StatusEffect> Supplier<T> registerStatusEffect(String name, Supplier<T> statusEffect) {
        throw new AssertionError();
    }
}
