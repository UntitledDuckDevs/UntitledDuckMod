package net.untitledduckmod.common.init;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.untitledduckmod.common.item.DuckSackItem;
import net.untitledduckmod.common.item.WaterfowlEggItem;
import net.untitledduckmod.common.platform.RegistryHelper;

import java.util.function.Supplier;

public class ModItems {
    // Duck
    public final static Supplier<SpawnEggItem> DUCK_SPAWN_EGG;
    public final static Supplier<Item> DUCK_EGG;
    public final static Supplier<Item> RAW_DUCK;
    public final static Supplier<Item> COOKED_DUCK;
    public final static Supplier<Item> DUCK_FEATHER;
    public final static Supplier<Item> DUCK_SACK;
    public final static Supplier<Item> EMPTY_DUCK_SACK;
    // Goose
    public final static Supplier<SpawnEggItem> GOOSE_SPAWN_EGG;
    public final static Supplier<Item> GOOSE_EGG;
    public final static Supplier<Item> RAW_GOOSE;
    public final static Supplier<Item> COOKED_GOOSE;
    public final static Supplier<Item> GOOSE_FOOT;

    static {
        // Food
        RAW_DUCK = RegistryHelper.registerItem("raw_duck", Item::new, new Item.Settings().food(new FoodComponent.Builder().nutrition(2).saturationModifier(0.4F).build()));
        COOKED_DUCK = RegistryHelper.registerItem("cooked_duck", Item::new, new Item.Settings().food(new FoodComponent.Builder().nutrition(6).saturationModifier(0.8F).build()));
        RAW_GOOSE = RegistryHelper.registerItem("raw_goose", Item::new, new Item.Settings().food(new FoodComponent.Builder().nutrition(3).saturationModifier(0.6F).build()));
        COOKED_GOOSE = RegistryHelper.registerItem("cooked_goose", Item::new, new Item.Settings().food(new FoodComponent.Builder().nutrition(8).saturationModifier(1.0F).build()));
        // Item
        DUCK_EGG = RegistryHelper.registerItem("duck_egg", (settings) -> new WaterfowlEggItem(settings, ModEntityTypes::getDuckEgg, ModEntityTypes::getDuck), new Item.Settings().maxCount(16));
        DUCK_FEATHER = RegistryHelper.registerItem("duck_feather", Item::new, new Item.Settings());
        DUCK_SACK = RegistryHelper.registerItem("duck_sack", DuckSackItem::new, new Item.Settings());
        EMPTY_DUCK_SACK = RegistryHelper.registerItem("empty_duck_sack", Item::new, new Item.Settings());
        GOOSE_EGG = RegistryHelper.registerItem("goose_egg", (settings) -> new WaterfowlEggItem(settings, ModEntityTypes::getGooseEgg, ModEntityTypes::getGoose), new Item.Settings().maxCount(16));
        GOOSE_FOOT = RegistryHelper.registerItem("goose_foot", Item::new, new Item.Settings());
        // Spawn Egg
        DUCK_SPAWN_EGG = RegistryHelper.registerSpawnEggItem("duck_spawn_egg", ModEntityTypes.DUCK);
        GOOSE_SPAWN_EGG = RegistryHelper.registerSpawnEggItem("goose_spawn_egg", ModEntityTypes.GOOSE);
    }

    // Call during mod initialization to ensure registration
    public static void init() {
    }

    @ExpectPlatform
    public static void setupItemGroups(Object optionalEvent) {
        throw new AssertionError();
    }

}
