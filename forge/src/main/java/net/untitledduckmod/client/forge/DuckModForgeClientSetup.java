package net.untitledduckmod.client.forge;

import net.untitledduckmod.client.renderer.entity.DuckRenderer;
import net.untitledduckmod.client.renderer.entity.GooseRenderer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.untitledduckmod.common.init.ModEntityTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class DuckModForgeClientSetup {

    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityTypes.getDuck(), DuckRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.getDuckEgg(), FlyingItemEntityRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.getGoose(), GooseRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.getGooseEgg(), FlyingItemEntityRenderer::new);
    }
}