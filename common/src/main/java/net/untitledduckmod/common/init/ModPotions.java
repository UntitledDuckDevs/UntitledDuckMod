package net.untitledduckmod.common.init;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.untitledduckmod.common.platform.RegistryHelper;

import java.util.function.Supplier;

import static net.untitledduckmod.mixin.BrewingRecipeRegistryInvoker.registerPotionRecipe;

public class ModPotions {

    public final static Supplier<Potion> INTIMIDATION;

    public final static Supplier<Potion> LONG_INTIMIDATION;

    static {
        INTIMIDATION = RegistryHelper.registerPotion("intimidation", () -> new Potion(new StatusEffectInstance(ModStatusEffects.intimidation.get(), 3600)));
        LONG_INTIMIDATION = RegistryHelper.registerPotion("long_intimidation", () -> new Potion("intimidation", new StatusEffectInstance(ModStatusEffects.intimidation.get(), 9600)));
    }

    // Call during mod initialization to ensure registration
    public static void init() {
    }

    public static void registerRecipe() {
        registerPotionRecipe(Potions.AWKWARD, ModItems.GOOSE_FOOT.get(), INTIMIDATION.get());
        registerPotionRecipe(INTIMIDATION.get(), Items.REDSTONE, LONG_INTIMIDATION.get());
    }
}
