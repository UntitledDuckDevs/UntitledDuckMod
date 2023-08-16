package net.untitledduckmod.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.untitledduckmod.ModEntityTypes;
import net.untitledduckmod.duck.DuckRenderer;
import net.untitledduckmod.goose.GooseRenderer;

public class DuckModFabricClientSetup implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntityTypes.getDuck(), DuckRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.getDuckEgg(), FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.getGoose(), GooseRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.getGooseEgg(), FlyingItemEntityRenderer::new);
    }
}