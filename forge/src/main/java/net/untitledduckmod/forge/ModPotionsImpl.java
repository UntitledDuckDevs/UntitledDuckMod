package net.untitledduckmod.forge;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.potion.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.untitledduckmod.DuckMod;
import net.untitledduckmod.ModPotions;

public class ModPotionsImpl {
    private static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTION_TYPES, DuckMod.MOD_ID);
    public static final RegistryObject<Potion> INTIMIDATION = POTIONS.register(ModPotions.INTIMIDATION_NAME, () -> new Potion(new StatusEffectInstance(ModStatusEffectsImpl.getIntimidationEffect(), ModPotions.INTIMIDATION_DURATION)));
    public static final RegistryObject<Potion> LONG_INTIMIDATION = POTIONS.register(ModPotions.LONG_INTIMIDATION_NAME, () -> new Potion(ModPotions.INTIMIDATION_NAME, new StatusEffectInstance(ModStatusEffectsImpl.getIntimidationEffect(), ModPotions.LONG_INTIMIDATION_DURATION)));

    public static void register() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        POTIONS.register(bus);
    }

    public static Potion getIntimidationPotion() {
        return INTIMIDATION.get();
    }

    public static Potion getLongIntimidationPotion() {
        return LONG_INTIMIDATION.get();
    }
}
