package net.untitledduckmod.common.init;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.registry.entry.RegistryEntry;
import net.untitledduckmod.common.platform.RegistryHelper;

public class ModPotions {

    public final static RegistryEntry<Potion> INTIMIDATION;

    public final static RegistryEntry<Potion> LONG_INTIMIDATION;

    static {
        INTIMIDATION = RegistryHelper.registerPotion("intimidation", () -> new Potion(new StatusEffectInstance(ModStatusEffects.intimidation, 3600)));
        LONG_INTIMIDATION = RegistryHelper.registerPotion("long_intimidation", () -> new Potion("intimidation", new StatusEffectInstance(ModStatusEffects.intimidation, 9600)));
    }

    // Call during mod initialization to ensure registration
    public static void init() {
    }

    public static void registerRecipes(BrewingRecipeRegistry.Builder builder) {
        builder.registerPotionRecipe(Potions.AWKWARD, ModItems.GOOSE_FOOT.get(), INTIMIDATION);
        builder.registerPotionRecipe(INTIMIDATION, Items.REDSTONE, LONG_INTIMIDATION);
    }
}
