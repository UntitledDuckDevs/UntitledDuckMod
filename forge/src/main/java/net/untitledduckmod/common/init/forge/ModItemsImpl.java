package net.untitledduckmod.common.init.forge;

import net.minecraft.item.ItemGroups;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.untitledduckmod.common.init.ModItems;

public class ModItemsImpl {
    public static void setupItemGroups(Object optionalEvent) {
        if (optionalEvent instanceof BuildCreativeModeTabContentsEvent event) {
            if (event.getTabKey() == ItemGroups.SPAWN_EGGS) {
                event.accept(ModItems.DUCK_SPAWN_EGG);
                event.accept(ModItems.GOOSE_SPAWN_EGG);
            } else if (event.getTabKey() == ItemGroups.INGREDIENTS) {
                event.accept(ModItems.DUCK_EGG);
                event.accept(ModItems.GOOSE_EGG);
                event.accept(ModItems.DUCK_FEATHER);
                event.accept(ModItems.GOOSE_FOOT);
            } else if (event.getTabKey() == ItemGroups.COMBAT) {
                event.accept(ModItems.DUCK_EGG);
                event.accept(ModItems.GOOSE_EGG);
            } else if (event.getTabKey() == ItemGroups.FOOD_AND_DRINK) {
                event.accept(ModItems.RAW_DUCK);
                event.accept(ModItems.COOKED_DUCK);
                event.accept(ModItems.RAW_GOOSE);
                event.accept(ModItems.COOKED_GOOSE);
            } else if (event.getTabKey() == ItemGroups.TOOLS) {
                event.accept(ModItems.DUCK_SACK);
                event.accept(ModItems.EMPTY_DUCK_SACK);
            }
        }
    }
}
