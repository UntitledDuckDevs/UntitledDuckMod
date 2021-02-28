package net.untitledduckmod.forge;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.util.Identifier;
import net.untitledduckmod.DuckEntity;

public class DuckRenderer extends GeoMobRenderer<DuckEntity> {
    public DuckRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher, new DuckModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    public Identifier getTexture(DuckEntity entity) {
        return getTextureLocation(entity);
    }
}
