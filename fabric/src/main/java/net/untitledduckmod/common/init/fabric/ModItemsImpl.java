package net.untitledduckmod.common.init.fabric;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.untitledduckmod.common.init.ModItems;

@SuppressWarnings("FieldCanBeLocal")
public class ModItemsImpl {
    public static void setupItemGroups(Object optionalEvent) {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(content -> {
            content.add(ModItems.DUCK_SPAWN_EGG.get());
            content.add(ModItems.GOOSE_SPAWN_EGG.get());
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(content -> {
            content.add(ModItems.DUCK_EGG.get());
            content.add(ModItems.GOOSE_EGG.get());
            content.add(ModItems.DUCK_FEATHER.get());
            content.add(ModItems.GOOSE_FOOT.get());
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(content -> {
            content.add(ModItems.DUCK_EGG.get());
            content.add(ModItems.GOOSE_EGG.get());
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(content -> {
            content.addAfter(Items.COOKED_RABBIT, ModItems.COOKED_DUCK.get());
            content.addAfter(Items.COOKED_RABBIT, ModItems.RAW_DUCK.get());
            content.addAfter(Items.COOKED_RABBIT, ModItems.COOKED_GOOSE.get());
            content.addAfter(Items.COOKED_RABBIT, ModItems.RAW_GOOSE.get());
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
            content.add(ModItems.DUCK_SACK.get());
            content.add(ModItems.EMPTY_DUCK_SACK.get());
        });
    }
}
