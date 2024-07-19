package net.untitledduckmod.common.init.fabric;

import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.untitledduckmod.common.init.ModPotions;

public class ModPotionsImpl {

    public static void registerRecipes() {
        FabricBrewingRecipeRegistryBuilder.BUILD.register(ModPotions::registerRecipes);
    }
}
