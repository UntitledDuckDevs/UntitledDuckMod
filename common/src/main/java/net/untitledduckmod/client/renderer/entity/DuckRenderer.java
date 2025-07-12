package net.untitledduckmod.client.renderer.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.untitledduckmod.client.model.DuckModel;
import net.untitledduckmod.common.entity.DuckEntity;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class DuckRenderer<R extends LivingEntityRenderState & GeoRenderState> extends WaterfowlRenderer<DuckEntity, R> {
    public DuckRenderer(EntityRendererFactory.Context context) { super(new DuckModel(), context); }
}
