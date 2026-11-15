package net.untitledduckmod.client.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.untitledduckmod.client.renderer.entity.DuckRenderer;
import net.untitledduckmod.client.renderer.entity.GooseRenderer;
import net.untitledduckmod.client.screen.MouthScreen;
import net.untitledduckmod.common.init.ModEntityTypes;
import net.untitledduckmod.common.init.ModScreenHandlers;

public class DuckModFabricClientSetup implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntityTypes.getDuck(), DuckRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.getDuckEgg(), FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.getGoose(), GooseRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.getGooseEgg(), FlyingItemEntityRenderer::new);
        HandledScreens.register(ModScreenHandlers.MOUTH.get(), MouthScreen::new);
    }
}