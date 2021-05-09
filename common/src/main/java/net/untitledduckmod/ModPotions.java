package net.untitledduckmod;

import me.shedaniel.architectury.annotations.ExpectPlatform;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import static net.untitledduckmod.mixin.BrewingRecipeRegistryInvoker.registerPotionRecipe;

public class ModPotions {

    public static String INTIMIDATION_NAME = "intimidation";
    public static int INTIMIDATION_DURATION = 3600; // = 3 minutes
    public static String LONG_INTIMIDATION_NAME = "long_intimidation";
    public static int LONG_INTIMIDATION_DURATION = 9600; // = 8 minutes

    @ExpectPlatform
    public static void register() {
        throw new AssertionError();
    }

    public static void registerBrewing() {
        registerPotionRecipe(Potions.AWKWARD, ModItems.getGooseFoot(), ModPotions.getIntimidationPotion());
        registerPotionRecipe(ModPotions.getIntimidationPotion(), Items.REDSTONE, ModPotions.getLongIntimidationPotion());
    }

    @ExpectPlatform
    public static Potion getIntimidationPotion() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Potion getLongIntimidationPotion() {
        throw new AssertionError();
    }
}
