package net.untitledduckmod.fabric.duck;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.untitledduckmod.DuckEntity;
import net.untitledduckmod.fabric.GeoMobRenderer;

public class DuckRenderer extends GeoMobRenderer<DuckEntity> {
    public DuckRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher, new DuckModel());
        this.shadowRadius = 0.3f;
    }
}
