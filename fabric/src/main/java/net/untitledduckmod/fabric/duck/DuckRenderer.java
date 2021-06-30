package net.untitledduckmod.fabric.duck;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.untitledduckmod.duck.DuckEntity;
import net.untitledduckmod.fabric.GeoMobRenderer;

public class DuckRenderer extends GeoMobRenderer<DuckEntity> {
    public DuckRenderer(EntityRendererFactory.Context context){
        super(context, new DuckModel());
        this.shadowRadius = 0.3f;
    }
}
