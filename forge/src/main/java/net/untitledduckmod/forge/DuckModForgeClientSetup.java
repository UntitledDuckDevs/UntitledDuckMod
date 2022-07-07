package net.untitledduckmod.forge;

import net.minecraft.client.render.entity.EntityRenderers;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.untitledduckmod.ModEntityTypes;
import net.untitledduckmod.forge.duck.DuckRenderer;
import net.untitledduckmod.forge.goose.GooseRenderer;

public class DuckModForgeClientSetup {
    public static void setupEntityRenderers() {
        EntityRenderers.register(ModEntityTypes.getDuck(), DuckRenderer::new);
        EntityRenderers.register(ModEntityTypes.getDuckEgg(), FlyingItemEntityRenderer::new);
        EntityRenderers.register(ModEntityTypes.getGoose(), GooseRenderer::new);
        EntityRenderers.register(ModEntityTypes.getGooseEgg(), FlyingItemEntityRenderer::new);
    }
}
