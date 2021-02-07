package net.untitledduckmod.registration;

import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.untitledduckmod.DuckMod;
import net.untitledduckmod.EntityTypes;
import net.untitledduckmod.ModItems;
import net.untitledduckmod.items.DuckEggItem;

import java.util.function.Supplier;

public class ItemSuppliers {
    public static final Identifier DUCK_SPAWN_EGG_ID = new Identifier(DuckMod.MOD_ID, "duck_spawn_egg");
    public static final Supplier<SpawnEggItem> DUCK_SPAWN_EGG = () -> new SpawnEggItem(EntityTypes.getDuck(), ModItems.DUCK_PRIMARY_COLOR, ModItems.DUCK_SECONDARY_COLOR, new Item.Settings().group(ItemGroup.MISC));

    public static final Identifier DUCK_EGG_ID = new Identifier(DuckMod.MOD_ID, "duck_egg");
    public static final Supplier<DuckEggItem> DUCK_EGG = () -> new DuckEggItem(new Item.Settings().maxCount(16).group(ItemGroup.MATERIALS));

    public static final Identifier RAW_DUCK_ID = new Identifier(DuckMod.MOD_ID, "raw_duck");
    public static final Supplier<Item> RAW_DUCK = () -> new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(2).saturationModifier(0.4F).meat().build()).group(ItemGroup.FOOD));

    public static final Identifier COOKED_DUCK_ID = new Identifier(DuckMod.MOD_ID, "cooked_duck");
    public static final Supplier<Item> COOKED_DUCK = () -> new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(6).saturationModifier(0.8F).meat().build()).group(ItemGroup.FOOD));

    public static final Identifier DUCK_FEATHER_ID = new Identifier(DuckMod.MOD_ID, "duck_feather");
    public static final Supplier<Item> DUCK_FEATHER = () -> new Item(new Item.Settings().group(ItemGroup.MATERIALS));
}
