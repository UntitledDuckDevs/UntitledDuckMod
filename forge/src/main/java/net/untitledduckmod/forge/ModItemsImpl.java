package net.untitledduckmod.forge;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.untitledduckmod.DuckMod;
import net.untitledduckmod.ModEntityTypes;
import net.untitledduckmod.ModItems;
import net.untitledduckmod.registration.ItemSuppliers;

public class ModItemsImpl {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DuckMod.MOD_ID);

    public static final RegistryObject<Item> DUCK_SPAWN_EGG = ITEMS.register(ItemSuppliers.DUCK_SPAWN_EGG_NAME,
        () -> new ForgeSpawnEggItem(ModEntityTypes::getDuck, ModItems.DUCK_PRIMARY_COLOR, ModItems.DUCK_SECONDARY_COLOR, new Item.Settings()));
    public static final RegistryObject<Item> DUCK_EGG = ITEMS.register(ItemSuppliers.DUCK_EGG_NAME, ItemSuppliers.DUCK_EGG);
    public static final RegistryObject<Item> RAW_DUCK = ITEMS.register(ItemSuppliers.RAW_DUCK_NAME, ItemSuppliers.RAW_DUCK);
    public static final RegistryObject<Item> COOKED_DUCK = ITEMS.register(ItemSuppliers.COOKED_DUCK_NAME, ItemSuppliers.COOKED_DUCK);
    public static final RegistryObject<Item> DUCK_FEATHER = ITEMS.register(ItemSuppliers.DUCK_FEATHER_NAME, ItemSuppliers.DUCK_FEATHER);
    public static final RegistryObject<Item> DUCK_SACK = ITEMS.register(ItemSuppliers.DUCK_SACK_NAME, ItemSuppliers.DUCK_SACK);
    public static final RegistryObject<Item> EMPTY_DUCK_SACK = ITEMS.register(ItemSuppliers.EMPTY_DUCK_SACK_NAME, ItemSuppliers.EMPTY_DUCK_SACK);

    public static final RegistryObject<Item> GOOSE_SPAWN_EGG = ITEMS.register(ItemSuppliers.GOOSE_SPAWN_EGG_NAME,
        () -> new ForgeSpawnEggItem(ModEntityTypes::getGoose, ModItems.GOOSE_PRIMARY_COLOR, ModItems.GOOSE_SECONDARY_COLOR, new Item.Settings()));
    public static final RegistryObject<Item> GOOSE_EGG = ITEMS.register(ItemSuppliers.GOOSE_EGG_NAME, ItemSuppliers.GOOSE_EGG);
    public static final RegistryObject<Item> RAW_GOOSE = ITEMS.register(ItemSuppliers.RAW_GOOSE_NAME, ItemSuppliers.RAW_GOOSE);
    public static final RegistryObject<Item> COOKED_GOOSE = ITEMS.register(ItemSuppliers.COOKED_GOOSE_NAME, ItemSuppliers.COOKED_GOOSE);
    public static final RegistryObject<Item> GOOSE_FOOT = ITEMS.register(ItemSuppliers.GOOSE_FOOT_NAME, ItemSuppliers.GOOSE_FOOT);

    public static void register(Object optionalEvent) {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(bus);
    }

    public static void setupItemGroups(Object optionalEvent) {
        if (optionalEvent instanceof CreativeModeTabEvent.BuildContents event) {
            if (event.getTab() == ItemGroups.SPAWN_EGGS) {
                event.accept(DUCK_SPAWN_EGG);
                event.accept(GOOSE_SPAWN_EGG);
            } else if (event.getTab() == ItemGroups.INGREDIENTS) {
                event.accept(DUCK_EGG);
                event.accept(GOOSE_EGG);
                event.accept(DUCK_FEATHER);
                event.accept(GOOSE_FOOT);
            } else if (event.getTab() == ItemGroups.COMBAT) {
                event.accept(DUCK_EGG);
                event.accept(GOOSE_EGG);
            } else if (event.getTab() == ItemGroups.FOOD_AND_DRINK) {
                event.accept(RAW_DUCK);
                event.accept(COOKED_DUCK);
                event.accept(RAW_GOOSE);
                event.accept(COOKED_GOOSE);
            } else if (event.getTab() == ItemGroups.TOOLS) {
                event.accept(DUCK_SACK);
                event.accept(EMPTY_DUCK_SACK);
            }
        }
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

    public static Item getGooseEgg() {
        return GOOSE_EGG.get();
    }

    public static Item getGooseFoot() {
        return GOOSE_FOOT.get();
    }

    public static Item getDuckSack() {
        return DUCK_SACK.get();
    }

    public static Item getEmptyDuckSack() {
        return EMPTY_DUCK_SACK.get();
    }
}
