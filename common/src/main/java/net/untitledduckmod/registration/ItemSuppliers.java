package net.untitledduckmod.registration;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.untitledduckmod.DuckMod;
import net.untitledduckmod.EntityTypes;
import net.untitledduckmod.ModItems;

import java.util.function.Supplier;

public class ItemSuppliers {
    public static Identifier DUCK_SPAWN_EGG_ID = new Identifier(DuckMod.MOD_ID, "duck_spawn_egg");
    public static Supplier<SpawnEggItem> DUCK_SPAWN_EGG = () -> new SpawnEggItem(EntityTypes.getDuck(), ModItems.DUCK_PRIMARY_COLOR, ModItems.DUCK_SECONDARY_COLOR, new Item.Settings().group(ItemGroup.MISC));
}
