package net.untitledduckmod.client.forge;

import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.untitledduckmod.client.renderer.entity.DuckRenderer;
import net.untitledduckmod.client.renderer.entity.GooseRenderer;
import net.untitledduckmod.client.screen.MouthScreen;
import net.untitledduckmod.common.init.ModEntityTypes;
import net.untitledduckmod.common.init.ModScreenHandlers;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class DuckModForgeClientSetup {

    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityTypes.getDuck(), DuckRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.getDuckEgg(), FlyingItemEntityRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.getGoose(), GooseRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.getGooseEgg(), FlyingItemEntityRenderer::new);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            HandledScreens.register(ModScreenHandlers.MOUTH.get(), MouthScreen::new);
        });
    }
}