package net.untitledduckmod.fabric;

import net.untitledduckmod.fabric.registration.EntityTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;

public class DuckModClientSetup implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(EntityTypes.DUCK, (entityRenderDispatcher, context) -> new DuckRenderer(entityRenderDispatcher));
    }
}
