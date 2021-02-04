package net.untitledduckmod.forge;

import net.untitledduckmod.DuckEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.util.Identifier;

public class DuckRenderer extends EntityRenderer<DuckEntity> {
    public DuckRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public Identifier getTexture(DuckEntity entity) {
        return null;
    }
}
