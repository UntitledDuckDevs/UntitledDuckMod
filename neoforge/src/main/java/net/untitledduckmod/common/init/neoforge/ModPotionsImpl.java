package net.untitledduckmod.common.init.neoforge;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.untitledduckmod.common.init.ModPotions;

public class ModPotionsImpl {

    @SubscribeEvent
    public static void registerRecipes(RegisterBrewingRecipesEvent event) {
        ModPotions.registerRecipes(event.getBuilder());
    }
}
