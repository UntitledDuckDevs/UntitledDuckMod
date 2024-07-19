package net.untitledduckmod.common.init.neoforge;

import net.minecraft.item.ItemGroups;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.untitledduckmod.common.init.ModItems;

public class ModItemsImpl {
    public static void setupItemGroups(Object optionalEvent) {
        if (optionalEvent instanceof BuildCreativeModeTabContentsEvent event) {
            if (event.getTabKey() == ItemGroups.SPAWN_EGGS) {
                event.add(ModItems.DUCK_SPAWN_EGG.get());
                event.add(ModItems.GOOSE_SPAWN_EGG.get());
            } else if (event.getTabKey() == ItemGroups.INGREDIENTS) {
                event.add(ModItems.DUCK_EGG.get());
                event.add(ModItems.GOOSE_EGG.get());
                event.add(ModItems.DUCK_FEATHER.get());
                event.add(ModItems.GOOSE_FOOT.get());
            } else if (event.getTabKey() == ItemGroups.COMBAT) {
                event.add(ModItems.DUCK_EGG.get());
                event.add(ModItems.GOOSE_EGG.get());
            } else if (event.getTabKey() == ItemGroups.FOOD_AND_DRINK) {
                event.add(ModItems.RAW_DUCK.get());
                event.add(ModItems.COOKED_DUCK.get());
                event.add(ModItems.RAW_GOOSE.get());
                event.add(ModItems.COOKED_GOOSE.get());
            } else if (event.getTabKey() == ItemGroups.TOOLS) {
                event.add(ModItems.DUCK_SACK.get());
                event.add(ModItems.EMPTY_DUCK_SACK.get());
            }
        }
    }
}
