package net.untitledduckmod.forge;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.util.Identifier;
import net.untitledduckmod.DuckEntity;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class DuckRenderer extends GeoEntityRenderer<DuckEntity> {
    public DuckRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher, new DuckModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    public Identifier getTexture(DuckEntity entity) {
        return getTextureLocation(entity);
    }
}
