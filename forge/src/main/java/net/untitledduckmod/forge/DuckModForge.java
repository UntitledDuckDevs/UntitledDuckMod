package net.untitledduckmod.forge;

import net.untitledduckmod.DuckMod;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.untitledduckmod.EntityTypes;

@Mod(DuckMod.MOD_ID)
public class DuckModForge {
    public DuckModForge() {
        DuckMod.init();
    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class ModSetup {
        @SubscribeEvent
        public static void entityRegistry(RegistryEvent.Register<EntityType<?>> event) {
            EntityTypes.register(event);
        }
        @SubscribeEvent
        public static void commonSetup(FMLCommonSetupEvent event) {
            EntityTypes.registerAttributes();
        }
        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(EntityTypes.getDuck(), DuckRenderer::new);
        }
    }
}
