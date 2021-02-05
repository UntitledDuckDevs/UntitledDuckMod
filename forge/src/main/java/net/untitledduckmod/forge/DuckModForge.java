package net.untitledduckmod.forge;

import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.EventPriority;
import net.untitledduckmod.DuckMod;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.untitledduckmod.EntityTypes;
import net.untitledduckmod.ModItems;

@Mod(DuckMod.MOD_ID)
public class DuckModForge {
    public DuckModForge() {
        DuckMod.init();
    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class ModSetup {
        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event) {
            ModItems.register(event);
        }
        @SubscribeEvent
        public static void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
            EntityTypes.register(event);
        }

        @SubscribeEvent
        public static void commonSetup(FMLCommonSetupEvent event) {
            EntityTypes.registerAttributes();
            ModItems.setup(event); // This setups the spawn eggs for forge
        }
        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(EntityTypes.getDuck(), DuckRenderer::new);
        }
    }
}
