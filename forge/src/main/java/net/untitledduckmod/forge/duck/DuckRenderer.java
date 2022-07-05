package net.untitledduckmod.forge.duck;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.untitledduckmod.duck.DuckEntity;
import net.untitledduckmod.forge.GeoMobRenderer;

public class DuckRenderer extends GeoMobRenderer<DuckEntity> {
    public DuckRenderer(EntityRendererFactory.Context context) {
        super(context, new DuckModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    public Identifier getTexture(DuckEntity entity) {
        return getTextureLocation(entity);
    }
}
