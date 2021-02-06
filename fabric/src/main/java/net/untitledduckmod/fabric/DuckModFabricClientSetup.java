package net.untitledduckmod.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.untitledduckmod.EntityTypes;

// TODO: Maybe this can be moved?
public class DuckModFabricClientSetup implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(EntityTypes.getDuck(), (entityRenderDispatcher, context) -> new DuckRenderer(entityRenderDispatcher));
        EntityRendererRegistry.INSTANCE.register(EntityTypes.getDuckEgg(), (entityRenderDispatcher, context) -> new FlyingItemEntityRenderer<>(entityRenderDispatcher, MinecraftClient.getInstance().getItemRenderer()));
    }
}