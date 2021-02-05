package net.untitledduckmod.forge;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.untitledduckmod.EntityTypes;
import net.untitledduckmod.ModItems;
import net.untitledduckmod.registration.ItemSuppliers;

public class ModItemsImpl {
    public static final RegistryObject<Item> DUCK_SPAWN_EGG = RegistryObject.of(ItemSuppliers.DUCK_SPAWN_EGG_ID, ForgeRegistries.ITEMS);

    public static void register(Object optionalEvent) {
        assert optionalEvent != null;
        RegistryEvent.Register<Item> event = (RegistryEvent.Register<Item>) optionalEvent;
        event.getRegistry().register(new ForgeSpawnEggItem(EntityTypes::getDuck, ModItems.DUCK_PRIMARY_COLOR, ModItems.DUCK_SECONDARY_COLOR, new Item.Settings().group(ItemGroup.MISC)).setRegistryName(ItemSuppliers.DUCK_SPAWN_EGG_ID));
    }

    public static void setup(Object optionalEvent) {
        ForgeSpawnEggItem.addModdedEggs();
    }

    public static Item getDuckSpawnEgg() {
        return DUCK_SPAWN_EGG.get();
    }
}
