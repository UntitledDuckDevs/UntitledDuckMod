package net.untitledduckmod.client.renderer.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.untitledduckmod.client.model.DuckModel;
import net.untitledduckmod.common.entity.DuckEntity;

public class DuckRenderer extends WaterfowlRenderer<DuckEntity> {
    public DuckRenderer(EntityRendererFactory.Context context) {
        super(context, new DuckModel());
    }
}
