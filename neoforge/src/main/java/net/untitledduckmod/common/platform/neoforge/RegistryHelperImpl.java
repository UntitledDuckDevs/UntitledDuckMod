package net.untitledduckmod.common.platform.neoforge;

import com.mojang.serialization.MapCodec;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.untitledduckmod.DuckMod;
import net.untitledduckmod.common.entity.neoforge.DuckEntityForge;
import net.untitledduckmod.common.entity.neoforge.GooseEntityForge;
import net.untitledduckmod.common.init.ModEntityTypes;

import java.util.function.Function;
import java.util.function.Supplier;

public class RegistryHelperImpl {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(DuckMod.MOD_ID);
    public static final DeferredRegister.Entities ENTITY_TYPES = DeferredRegister.createEntities(DuckMod.MOD_ID);
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, DuckMod.MOD_ID);
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(Registries.POTION, DuckMod.MOD_ID);
    public static final DeferredRegister<StatusEffect> STATUS_EFFECTS = DeferredRegister.create(Registries.STATUS_EFFECT, DuckMod.MOD_ID);
    public static final DeferredRegister<MapCodec<? extends BiomeModifier>> BIOME_MODIFIERS = DeferredRegister.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, DuckMod.MOD_ID);

    public static <T extends Item> Supplier<T> registerItem(String name, Function<Item.Settings, T> factory, Item.Settings settings) {
        return ITEMS.registerItem(name, factory);
    }

    public static Supplier<SpawnEggItem> registerSpawnEggItem(
            String name, Supplier<? extends EntityType<? extends MobEntity>> type
    ) {
        return registerItem(name, (settings) -> new SpawnEggItem(type.get(), settings), new Item.Settings());
    }

    public static  <T extends EntityType<?>> Supplier<T> registerEntity(String name, Supplier<T> entityType) {
        if (name.equals("duck")) {
            entityType = () -> (T) EntityType.Builder.create(DuckEntityForge::new, SpawnGroup.CREATURE).dimensions(0.6f, 0.6f).maxTrackingRange(10).build(ModEntityTypes.duckKey);
        } else if (name.equals("goose")) {
            entityType = () -> (T) EntityType.Builder.create(GooseEntityForge::new, SpawnGroup.CREATURE).dimensions(0.7f, 1.2f).maxTrackingRange(10).build(ModEntityTypes.gooseKey);
        }
        return ENTITY_TYPES.register(name, entityType);
    }

    public static Supplier<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.of(DuckMod.id(name)));
    }

    public static RegistryEntry<Potion> registerPotion(String name, Supplier<Potion> potion) {
        return POTIONS.register(name, potion);
    }

    public static RegistryEntry<StatusEffect> registerStatusEffect(String name, Supplier<StatusEffect> statusEffect) {
        return STATUS_EFFECTS.register(name, statusEffect);
    }

    public static <T extends MapCodec<? extends BiomeModifier>> void registerBiomeModifier(String name, Supplier<T> biomeModifier) {
        BIOME_MODIFIERS.register(name, biomeModifier);
    }
}
