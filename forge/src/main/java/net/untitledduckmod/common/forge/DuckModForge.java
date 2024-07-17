package net.untitledduckmod.common.forge;

import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.untitledduckmod.DuckMod;
import net.untitledduckmod.common.init.ModEntityTypes;
import net.untitledduckmod.common.init.ModItems;
import net.untitledduckmod.common.init.forge.ModBiomeModifier;
import net.untitledduckmod.common.platform.forge.RegistryHelperImpl;

@Mod(DuckMod.MOD_ID)
public class DuckModForge {
    public DuckModForge() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        DuckMod.preInit();
        ModBiomeModifier.init();
        RegistryHelperImpl.ITEMS.register(bus);
        RegistryHelperImpl.ENTITY_TYPES.register(bus);
        RegistryHelperImpl.SOUND_EVENTS.register(bus);
        RegistryHelperImpl.BIOME_MODIFIERS.register(bus);
        RegistryHelperImpl.STATUS_EFFECTS.register(bus);
        RegistryHelperImpl.POTIONS.register(bus);
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModSetup {
        @SubscribeEvent
        public static void commonSetup(FMLCommonSetupEvent event) {
            DuckMod.postInit();
            DuckMod.postEntityInit();
        }

        @SubscribeEvent
        public static void spawnSetting(SpawnPlacementRegisterEvent event) {
            ModEntityTypes.setupSpawning(event);
        }

        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void onRegisterAttributes(EntityAttributeCreationEvent event) {
            ModEntityTypes.registerAttributes(event);
        }

        @SubscribeEvent
        public static void buildContentsOfCreativeModeTab(BuildCreativeModeTabContentsEvent event) {
            ModItems.setupItemGroups(event);
        }
    }
}
