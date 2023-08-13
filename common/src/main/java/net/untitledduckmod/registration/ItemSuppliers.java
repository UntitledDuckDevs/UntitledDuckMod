package net.untitledduckmod.registration;

import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.untitledduckmod.DuckMod;
import net.untitledduckmod.ModEntityTypes;
import net.untitledduckmod.ModItems;
import net.untitledduckmod.items.DuckEggItem;
import net.untitledduckmod.items.DuckSackItem;

import java.util.function.Supplier;

public class ItemSuppliers {
    // Duck
    public static final String DUCK_SPAWN_EGG_NAME = "duck_spawn_egg";
    public static final Identifier DUCK_SPAWN_EGG_ID = new Identifier(DuckMod.MOD_ID, DUCK_SPAWN_EGG_NAME);
    public static final Supplier<SpawnEggItem> DUCK_SPAWN_EGG = () -> new SpawnEggItem(ModEntityTypes.getDuck(),
            ModItems.DUCK_PRIMARY_COLOR, ModItems.DUCK_SECONDARY_COLOR, new Item.Settings());

    public static final String DUCK_EGG_NAME = "duck_egg";
    public static final Identifier DUCK_EGG_ID = new Identifier(DuckMod.MOD_ID, DUCK_EGG_NAME);
    public static final Supplier<DuckEggItem> DUCK_EGG = () -> new DuckEggItem(new Item.Settings().maxCount(16), ModEntityTypes::getDuckEgg, ModEntityTypes::getDuck);

    public static final String RAW_DUCK_NAME = "raw_duck";
    public static final Identifier RAW_DUCK_ID = new Identifier(DuckMod.MOD_ID, RAW_DUCK_NAME);
    public static final Supplier<Item> RAW_DUCK = () -> new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(2).saturationModifier(0.4F).meat().build()));

    public static final String COOKED_DUCK_NAME = "cooked_duck";
    public static final Identifier COOKED_DUCK_ID = new Identifier(DuckMod.MOD_ID, COOKED_DUCK_NAME);
    public static final Supplier<Item> COOKED_DUCK = () -> new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(6).saturationModifier(0.8F).meat().build()));

    public static final String DUCK_FEATHER_NAME = "duck_feather";
    public static final Identifier DUCK_FEATHER_ID = new Identifier(DuckMod.MOD_ID, DUCK_FEATHER_NAME);
    public static final Supplier<Item> DUCK_FEATHER = () -> new Item(new Item.Settings());

    public static final String DUCK_SACK_NAME = "duck_sack";
    public static final Identifier DUCK_SACK_ID = new Identifier(DuckMod.MOD_ID, DUCK_SACK_NAME);
    public static final Supplier<Item> DUCK_SACK = () -> new DuckSackItem(new Item.Settings());

    public static final String EMPTY_DUCK_SACK_NAME = "empty_duck_sack";
    public static final Identifier EMPTY_DUCK_SACK_ID = new Identifier(DuckMod.MOD_ID, EMPTY_DUCK_SACK_NAME);
    public static final Supplier<Item> EMPTY_DUCK_SACK = () -> new Item(new Item.Settings());

    // Goose
    public static final String GOOSE_SPAWN_EGG_NAME = "goose_spawn_egg";
    public static final Identifier GOOSE_SPAWN_EGG_ID = new Identifier(DuckMod.MOD_ID, GOOSE_SPAWN_EGG_NAME);
    public static final Supplier<SpawnEggItem> GOOSE_SPAWN_EGG = () -> new SpawnEggItem(ModEntityTypes.getGoose(),
            ModItems.GOOSE_PRIMARY_COLOR, ModItems.GOOSE_SECONDARY_COLOR, new Item.Settings());

    public static final String GOOSE_EGG_NAME = "goose_egg";
    public static final Identifier GOOSE_EGG_ID = new Identifier(DuckMod.MOD_ID, GOOSE_EGG_NAME);
    public static final Supplier<DuckEggItem> GOOSE_EGG = () -> new DuckEggItem(new Item.Settings().maxCount(16), ModEntityTypes::getGooseEgg, ModEntityTypes::getGoose);

    public static final String RAW_GOOSE_NAME = "raw_goose";
    public static final Identifier RAW_GOOSE_ID = new Identifier(DuckMod.MOD_ID, RAW_GOOSE_NAME);
    public static final Supplier<Item> RAW_GOOSE = () -> new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(3).saturationModifier(0.6F).meat().build()));

    public static final String COOKED_GOOSE_NAME = "cooked_goose";
    public static final Identifier COOKED_GOOSE_ID = new Identifier(DuckMod.MOD_ID, COOKED_GOOSE_NAME);
    public static final Supplier<Item> COOKED_GOOSE = () -> new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(8).saturationModifier(1.0F).meat().build()));

    public static final String GOOSE_FOOT_NAME = "goose_foot";
    public static final Identifier GOOSE_FOOT_ID = new Identifier(DuckMod.MOD_ID, GOOSE_FOOT_NAME);
    public static final Supplier<Item> GOOSE_FOOT = () -> new Item(new Item.Settings());
}
