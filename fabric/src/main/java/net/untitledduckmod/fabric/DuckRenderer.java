package net.untitledduckmod.fabric;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.untitledduckmod.DuckEntity;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

public class DuckRenderer extends GeoEntityRenderer<DuckEntity> {
    public DuckRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher, new DuckModel());
        this.shadowRadius = 0.3f;
    }
}
