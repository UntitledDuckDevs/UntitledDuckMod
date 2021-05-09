package net.untitledduckmod.forge;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.untitledduckmod.ModEntityTypes;
import net.untitledduckmod.forge.duck.DuckRenderer;
import net.untitledduckmod.forge.goose.GooseRenderer;

public class DuckModForgeClientSetup {
    public static void setupEntityRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.getDuck(), DuckRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.getDuckEgg(),
                erd -> new FlyingItemEntityRenderer<>(erd, MinecraftClient.getInstance().getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.getGoose(), GooseRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.getGooseEgg(),
                erd -> new FlyingItemEntityRenderer<>(erd, MinecraftClient.getInstance().getItemRenderer()));
    }
}
