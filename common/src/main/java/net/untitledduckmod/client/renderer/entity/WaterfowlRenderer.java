package net.untitledduckmod.client.renderer.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.untitledduckmod.client.model.WaterfowlModel;
import net.untitledduckmod.common.entity.WaterfowlEntity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.layer.ItemInHandGeoLayer;

public class WaterfowlRenderer<T extends WaterfowlEntity, R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<T, R> {
    public WaterfowlRenderer(WaterfowlModel<T> model, EntityRendererFactory.Context context) {
        super(context, model);
        this.shadowRadius = 0.3f;
        // add the render layer so that ducks/geese hold items in their beak
        // this replaces the need to manually render the item in renderRecursively
        addRenderLayer(new ItemInHandGeoLayer<>(this, "beak", "beak"));
    }

    @Override
    public void addRenderData(T animatable, Void relatedObject, R renderState) {
        // set the variant in the render state
        renderState.addGeckolibData(WaterfowlEntity.VARIANT_TICKET, animatable.getVariant());
    }

    @Override
    public void scaleModelForRender(R renderState, float widthScale, float heightScale, MatrixStack poseStack, BakedGeoModel model, boolean isReRender) {
        float modelScale = renderState.baby ? 0.7f : 1.0f;
        // set the entity scale for rendering (this replaces the need to change the scale in preRender)
        super.scaleModelForRender(renderState, modelScale, modelScale, poseStack, model, isReRender);
    }
}
