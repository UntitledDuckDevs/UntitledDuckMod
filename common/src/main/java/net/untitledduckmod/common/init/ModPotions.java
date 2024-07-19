package net.untitledduckmod.common.init;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.untitledduckmod.common.platform.RegistryHelper;

import java.util.function.Supplier;

public class ModPotions {

    public final static Supplier<Potion> INTIMIDATION;

    public final static Supplier<Potion> LONG_INTIMIDATION;

    static {
        INTIMIDATION = RegistryHelper.registerPotion("intimidation", () -> new Potion(new StatusEffectInstance(RegistryHelper.getEntry(ModStatusEffects.intimidation.get()), 3600)));
        LONG_INTIMIDATION = RegistryHelper.registerPotion("long_intimidation", () -> new Potion("intimidation", new StatusEffectInstance(RegistryHelper.getEntry(ModStatusEffects.intimidation.get()), 9600)));
    }

    // Call during mod initialization to ensure registration
    public static void init() {
    }

    public static void registerRecipes(BrewingRecipeRegistry.Builder builder) {
        var intimidation = RegistryHelper.getEntry(INTIMIDATION.get());
        var longIntimidation = RegistryHelper.getEntry(LONG_INTIMIDATION.get());
        builder.registerPotionRecipe(Potions.AWKWARD, ModItems.GOOSE_FOOT.get(), intimidation);
        builder.registerPotionRecipe(intimidation, Items.REDSTONE, longIntimidation);
    }
}
