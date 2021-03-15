package net.untitledduckmod.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.untitledduckmod.ModEntityTypes;
import net.untitledduckmod.fabric.duck.DuckRenderer;
import net.untitledduckmod.fabric.goose.GooseRenderer;

public class DuckModFabricClientSetup implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(ModEntityTypes.getDuck(), (entityRenderDispatcher, context) -> new DuckRenderer(entityRenderDispatcher));
        EntityRendererRegistry.INSTANCE.register(ModEntityTypes.getDuckEgg(), (entityRenderDispatcher, context) -> new FlyingItemEntityRenderer<>(entityRenderDispatcher, MinecraftClient.getInstance().getItemRenderer()));
        EntityRendererRegistry.INSTANCE.register(ModEntityTypes.getGoose(), (entityRenderDispatcher, context) -> new GooseRenderer(entityRenderDispatcher));
        EntityRendererRegistry.INSTANCE.register(ModEntityTypes.getGooseEgg(), (entityRenderDispatcher, context) -> new FlyingItemEntityRenderer<>(entityRenderDispatcher, MinecraftClient.getInstance().getItemRenderer()));
    }
}