package net.untitledduckmod.forge;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.untitledduckmod.ModEntityTypes;
import net.untitledduckmod.ModItems;
import net.untitledduckmod.registration.ItemSuppliers;

public class ModItemsImpl {
    public static final RegistryObject<Item> DUCK_SPAWN_EGG = RegistryObject.of(ItemSuppliers.DUCK_SPAWN_EGG_ID, ForgeRegistries.ITEMS);
    public static final RegistryObject<Item> DUCK_EGG = RegistryObject.of(ItemSuppliers.DUCK_EGG_ID, ForgeRegistries.ITEMS);
    public static final RegistryObject<Item> GOOSE_SPAWN_EGG = RegistryObject.of(ItemSuppliers.GOOSE_SPAWN_EGG_ID, ForgeRegistries.ITEMS);

    public static void register(Object optionalEvent) {
        assert optionalEvent != null;
        RegistryEvent.Register<Item> event = (RegistryEvent.Register<Item>) optionalEvent;
        IForgeRegistry<Item> registry = event.getRegistry();

        registry.register(new ForgeSpawnEggItem(ModEntityTypes::getDuck, ModItems.DUCK_PRIMARY_COLOR, ModItems.DUCK_SECONDARY_COLOR, new Item.Settings().group(ItemGroup.MISC)).setRegistryName(ItemSuppliers.DUCK_SPAWN_EGG_ID));
        registry.register(ItemSuppliers.DUCK_EGG.get().setRegistryName(ItemSuppliers.DUCK_EGG_ID));
        registry.register(ItemSuppliers.RAW_DUCK.get().setRegistryName(ItemSuppliers.RAW_DUCK_ID));
        registry.register(ItemSuppliers.COOKED_DUCK.get().setRegistryName(ItemSuppliers.COOKED_DUCK_ID));
        registry.register(ItemSuppliers.DUCK_FEATHER.get().setRegistryName(ItemSuppliers.DUCK_FEATHER_ID));

        registry.register(new ForgeSpawnEggItem(ModEntityTypes::getGoose, ModItems.DUCK_PRIMARY_COLOR, ModItems.DUCK_SECONDARY_COLOR, new Item.Settings().group(ItemGroup.MISC)).setRegistryName(ItemSuppliers.GOOSE_SPAWN_EGG_ID));
    }

    public static void setup(Object optionalEvent) {
        ForgeSpawnEggItem.addModdedEggs();
    }

    public static Item getDuckSpawnEgg() {
        return DUCK_SPAWN_EGG.get();
    }

    public static Item getDuckEgg() {
        return DUCK_EGG.get();
    }

    public static Item getGooseSpawnEgg() {
        return GOOSE_SPAWN_EGG.get();
    }
}
