package net.untitledduckmod.fabric;

import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.registry.Registry;
import net.untitledduckmod.registration.ItemSuppliers;

@SuppressWarnings("FieldCanBeLocal")
public class ModItemsImpl {
    public static SpawnEggItem DUCK_SPAWN_EGG;
    public static Item DUCK_EGG;
    public static Item RAW_DUCK;
    public static Item COOKED_DUCK;
    public static Item DUCK_FEATHER;
    public static Item DUCK_SACK;
    public static Item EMPTY_DUCK_SACK;

    public static SpawnEggItem GOOSE_SPAWN_EGG;
    public static Item GOOSE_EGG;
    public static Item RAW_GOOSE;
    public static Item COOKED_GOOSE;
    public static Item GOOSE_FOOT;

    public static void register(Object optionalEvent) {
        DUCK_SPAWN_EGG = Registry.register(Registry.ITEM, ItemSuppliers.DUCK_SPAWN_EGG_ID, ItemSuppliers.DUCK_SPAWN_EGG.get());
        DUCK_EGG = Registry.register(Registry.ITEM, ItemSuppliers.DUCK_EGG_ID, ItemSuppliers.DUCK_EGG.get());
        RAW_DUCK = Registry.register(Registry.ITEM, ItemSuppliers.RAW_DUCK_ID, ItemSuppliers.RAW_DUCK.get());
        COOKED_DUCK = Registry.register(Registry.ITEM, ItemSuppliers.COOKED_DUCK_ID, ItemSuppliers.COOKED_DUCK.get());
        DUCK_FEATHER = Registry.register(Registry.ITEM, ItemSuppliers.DUCK_FEATHER_ID, ItemSuppliers.DUCK_FEATHER.get());
        DUCK_SACK = Registry.register(Registry.ITEM, ItemSuppliers.DUCK_SACK_ID, ItemSuppliers.DUCK_SACK.get());
        EMPTY_DUCK_SACK = Registry.register(Registry.ITEM, ItemSuppliers.EMPTY_DUCK_SACK_ID, ItemSuppliers.EMPTY_DUCK_SACK.get());

        GOOSE_SPAWN_EGG = Registry.register(Registry.ITEM, ItemSuppliers.GOOSE_SPAWN_EGG_ID, ItemSuppliers.GOOSE_SPAWN_EGG.get());
        GOOSE_EGG = Registry.register(Registry.ITEM, ItemSuppliers.GOOSE_EGG_ID, ItemSuppliers.GOOSE_EGG.get());
        RAW_GOOSE = Registry.register(Registry.ITEM, ItemSuppliers.RAW_GOOSE_ID, ItemSuppliers.RAW_GOOSE.get());
        COOKED_GOOSE = Registry.register(Registry.ITEM, ItemSuppliers.COOKED_GOOSE_ID, ItemSuppliers.COOKED_GOOSE.get());
        GOOSE_FOOT = Registry.register(Registry.ITEM, ItemSuppliers.GOOSE_FOOT_ID, ItemSuppliers.GOOSE_FOOT.get());
    }

    public static void setup(Object optionalEvent) {
    }

    public static Item getDuckSpawnEgg() {
        return DUCK_SPAWN_EGG;
    }

    public static Item getDuckEgg() {
        return DUCK_EGG;
    }

    public static Item getGooseSpawnEgg() {
        return GOOSE_SPAWN_EGG;
    }

    public static Item getGooseEgg() {
        return GOOSE_EGG;
    }

    public static Item getGooseFoot() {
        return GOOSE_FOOT;
    }

    public static Item getDuckSack() {
        return DUCK_SACK;
    }

    public static Item getEmptyDuckSack() {
        return EMPTY_DUCK_SACK;
    }
}
