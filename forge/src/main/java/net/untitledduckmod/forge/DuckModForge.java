package net.untitledduckmod.forge;

import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.RegisterEvent;
import net.untitledduckmod.*;

@Mod(DuckMod.MOD_ID)
public class DuckModForge {
    public DuckModForge() {
        DuckMod.preInit();
        ModItems.register(null);
        ModEntityTypes.register(null);
        ModStatusEffects.register();
        ModPotions.register();
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModSetup {
        @SubscribeEvent
        public static void registerSoundEvents(RegisterEvent event) {
            ModSoundEvents.register(event);
        }

        @SubscribeEvent
        public static void commonSetup(FMLCommonSetupEvent event) {
            ModPotions.registerBrewing();
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
        public static void buildContentsOfCreativeModeTab(CreativeModeTabEvent.BuildContents event) {
            ModItems.setupItemGroups(event);
        }
    }
}
