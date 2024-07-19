package net.untitledduckmod.client.neoforge;

import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.untitledduckmod.client.renderer.entity.DuckRenderer;
import net.untitledduckmod.client.renderer.entity.GooseRenderer;
import net.untitledduckmod.common.init.ModEntityTypes;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class DuckModForgeClientSetup {

    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityTypes.getDuck(), DuckRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.getDuckEgg(), FlyingItemEntityRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.getGoose(), GooseRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.getGooseEgg(), FlyingItemEntityRenderer::new);
    }
}