package net.untitledduckmod.registration;

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
    public static final Identifier DUCK_EGG_ID = new Identifier(DuckMod.MOD_ID, "duck_egg");
    public static Identifier DUCK_SPAWN_EGG_ID = new Identifier(DuckMod.MOD_ID, "duck_spawn_egg");
    public static Supplier<SpawnEggItem> DUCK_SPAWN_EGG = () -> new SpawnEggItem(EntityTypes.getDuck(), ModItems.DUCK_PRIMARY_COLOR, ModItems.DUCK_SECONDARY_COLOR, new Item.Settings().group(ItemGroup.MISC));
    public static Supplier<DuckEggItem> DUCK_EGG = () -> new DuckEggItem(new Item.Settings().maxCount(16).group(ItemGroup.MATERIALS));
}
