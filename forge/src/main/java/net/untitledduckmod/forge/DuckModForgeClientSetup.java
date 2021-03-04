package net.untitledduckmod.forge;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.untitledduckmod.ModEntityTypes;

public class DuckModForgeClientSetup {
    public static void setupEntityRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.getDuck(), DuckRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.getDuckEgg(),
                erd -> new FlyingItemEntityRenderer<>(erd, MinecraftClient.getInstance().getItemRenderer()));
    }
}
