package net.untitledduckmod.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;

public class DuckModClientSetup implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(EntityTypesImpl.DUCK, (entityRenderDispatcher, context) -> new DuckRenderer(entityRenderDispatcher));
    }
}