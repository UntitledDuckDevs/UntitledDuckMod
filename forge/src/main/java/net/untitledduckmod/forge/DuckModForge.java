package net.untitledduckmod.forge;

import net.minecraft.entity.EntityType;
import net.minecraft.potion.Potion;
import net.minecraft.sound.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.untitledduckmod.*;

@Mod(DuckMod.MOD_ID)
public class DuckModForge {
    public DuckModForge() {
        DuckMod.init();
        ModItems.register(null);
        ModEntityTypes.register(null);
        ModStatusEffects.register();
        ModPotions.register();
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModSetup {
        @SubscribeEvent
        public static void registerSoundEvents(RegistryEvent.Register<SoundEvent> event) {
            ModSoundEvents.register(event);
        }

        @SubscribeEvent
        public static void commonSetup(FMLCommonSetupEvent event) {
            ModEntityTypesImpl.setupSpawning();
        }

        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void onRegisterAttributes(final EntityAttributeCreationEvent event) {
            ModEntityTypesImpl.registerAttributes(event);
        }

        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void onPostRegisterPotions(final RegistryEvent.Register<Potion> event) {
            ModPotions.registerBrewing();
        }

        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {
            DuckModForgeClientSetup.setupEntityRenderers();
        }
    }
}
