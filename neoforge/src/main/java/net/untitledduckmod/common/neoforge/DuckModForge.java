package net.untitledduckmod.common.neoforge;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.untitledduckmod.DuckMod;
import net.untitledduckmod.common.init.ModEntityTypes;
import net.untitledduckmod.common.init.ModItems;
import net.untitledduckmod.common.init.neoforge.ModBiomeModifier;
import net.untitledduckmod.common.platform.neoforge.RegistryHelperImpl;

@Mod(DuckMod.MOD_ID)
public class DuckModForge {

    public DuckModForge(IEventBus bus, Dist dist) {
        DuckMod.preInit();
        ModBiomeModifier.init();
        RegistryHelperImpl.ITEMS.register(bus);
        RegistryHelperImpl.ENTITY_TYPES.register(bus);
        RegistryHelperImpl.SOUND_EVENTS.register(bus);
        RegistryHelperImpl.BIOME_MODIFIERS.register(bus);
        RegistryHelperImpl.STATUS_EFFECTS.register(bus);
        RegistryHelperImpl.POTIONS.register(bus);
    }

    @EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
    public static class ModSetup {
        @SubscribeEvent
        public static void commonSetup(FMLCommonSetupEvent event) {
            DuckMod.postInit();
            DuckMod.postEntityInit();
        }

        @SubscribeEvent
        public static void spawnSetting(RegisterSpawnPlacementsEvent event) {
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
