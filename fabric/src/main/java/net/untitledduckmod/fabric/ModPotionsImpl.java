package net.untitledduckmod.fabric;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.untitledduckmod.DuckMod;
import net.untitledduckmod.ModPotions;
import net.untitledduckmod.ModStatusEffects;

public class ModPotionsImpl {
    public static Potion LONG_INTIMIDATION_POTION;
    public static Potion INTIMIDATION_POTION;

    public static void register() {
        INTIMIDATION_POTION = Registry.register(Registry.POTION, new Identifier(DuckMod.MOD_ID, ModPotions.INTIMIDATION_NAME), new Potion(new StatusEffectInstance(ModStatusEffects.getIntimidationEffect(), ModPotions.INTIMIDATION_DURATION)));
        LONG_INTIMIDATION_POTION = Registry.register(Registry.POTION, new Identifier(DuckMod.MOD_ID, ModPotions.LONG_INTIMIDATION_NAME), new Potion(ModPotions.INTIMIDATION_NAME, new StatusEffectInstance(ModStatusEffects.getIntimidationEffect(), ModPotions.LONG_INTIMIDATION_DURATION)));
    }

    public static Potion getIntimidationPotion() {
        return INTIMIDATION_POTION;
    }

    public static Potion getLongIntimidationPotion() {
        return LONG_INTIMIDATION_POTION;
    }
}
