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
        EntityRendererRegistry.INSTANCE.register(ModEntityTypes.getDuck(), (context) -> new DuckRenderer(context));
        EntityRendererRegistry.INSTANCE.register(ModEntityTypes.getDuckEgg(), (context) -> new FlyingItemEntityRenderer<>(context));
        EntityRendererRegistry.INSTANCE.register(ModEntityTypes.getGoose(), (context) -> new GooseRenderer(context));
        EntityRendererRegistry.INSTANCE.register(ModEntityTypes.getGooseEgg(), (context) -> new FlyingItemEntityRenderer<>(context));
    }
}