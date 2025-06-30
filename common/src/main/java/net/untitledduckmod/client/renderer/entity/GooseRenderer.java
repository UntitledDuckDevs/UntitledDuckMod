package net.untitledduckmod.client.renderer.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.untitledduckmod.client.model.GooseModel;
import net.untitledduckmod.common.entity.GooseEntity;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class GooseRenderer<R extends LivingEntityRenderState & GeoRenderState> extends WaterfowlRenderer<GooseEntity, R> {
    public GooseRenderer(EntityRendererFactory.Context context) { super(new GooseModel(), context); }
}
