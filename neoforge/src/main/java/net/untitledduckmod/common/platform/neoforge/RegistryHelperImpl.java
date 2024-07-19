package net.untitledduckmod.common.platform.neoforge;

import com.mojang.serialization.MapCodec;
import net.minecraft.component.ComponentType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundEvent;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.untitledduckmod.DuckMod;
import net.untitledduckmod.common.entity.neoforge.DuckEntityForge;
import net.untitledduckmod.common.entity.neoforge.GooseEntityForge;

import java.util.function.Supplier;

public class RegistryHelperImpl {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(DuckMod.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, DuckMod.MOD_ID);
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, DuckMod.MOD_ID);
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(Registries.POTION, DuckMod.MOD_ID);
    public static final DeferredRegister<StatusEffect> STATUS_EFFECTS = DeferredRegister.create(Registries.STATUS_EFFECT, DuckMod.MOD_ID);
    public static final DeferredRegister<ComponentType<?>> DATA_COMPONENT_TYPES = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, DuckMod.MOD_ID);
    public static final DeferredRegister<MapCodec<? extends BiomeModifier>> BIOME_MODIFIERS = DeferredRegister.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, DuckMod.MOD_ID);

    public static <T extends Item> Supplier<T> registerItem(String name, Supplier<T> item) {
        return ITEMS.register(name, item);
    }

    public static <T extends Item> Supplier<T> registerSpawnEggItem(
            String name, Supplier<? extends EntityType<? extends MobEntity>> type,
            int primaryColor, int secondaryColor, Item.Settings settings
    ) {
        return (Supplier<T>) registerItem(name, () -> new DeferredSpawnEggItem(type, primaryColor, secondaryColor, settings));
    }

    public static  <T extends EntityType<?>> Supplier<T> registerEntity(String name, Supplier<T> entityType) {
        if (name.equals("duck")) {
            entityType = () -> (T) EntityType.Builder.create(DuckEntityForge::new, SpawnGroup.CREATURE).dimensions(0.6f, 0.6f).maxTrackingRange(10).build(DuckMod.stringID("duck"));
        } else if (name.equals("goose")) {
            entityType = () -> (T) EntityType.Builder.create(GooseEntityForge::new, SpawnGroup.CREATURE).dimensions(0.7f, 1.2f).maxTrackingRange(10).build(DuckMod.stringID("goose"));
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

    public static <T extends MapCodec<? extends BiomeModifier>> void registerBiomeModifier(String name, Supplier<T> biomeModifier) {
        BIOME_MODIFIERS.register(name, biomeModifier);
    }
}
