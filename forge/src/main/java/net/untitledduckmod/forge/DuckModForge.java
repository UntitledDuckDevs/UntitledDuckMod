package net.untitledduckmod.forge;

import net.minecraft.item.ItemGroups;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.RegisterEvent;
import net.untitledduckmod.*;

import static net.untitledduckmod.forge.ModItemsImpl.*;

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
            ModEntityTypesImpl.setupSpawning();
            ModPotions.registerBrewing();
            DuckMod.postEntityInit();
        }

        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void onRegisterAttributes(final EntityAttributeCreationEvent event) {
            ModEntityTypesImpl.registerAttributes(event);
        }

        @SubscribeEvent
        public static void buildContentsOfCreativeModeTab(CreativeModeTabEvent.BuildContents event) {
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
}
