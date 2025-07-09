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
    private static final float ADULT_SHADOW_RADIUS = 0.3f;
    private static final float BABY_MIN_SHADOW_RADIUS = 0.08f;
    private static final float BABY_MAX_SHADOW_RADIUS = 0.21f;

    public WaterfowlRenderer(WaterfowlModel<T> model, EntityRendererFactory.Context context) {
        super(context, model);
        this.shadowRadius = ADULT_SHADOW_RADIUS;
        // add the render layer so that ducks/geese hold items in their beak
        // this replaces the need to manually render the item in renderRecursively
        addRenderLayer(new ItemInHandGeoLayer<>(this, "beak", "beak"));
    }

    public boolean babyRandomSize() {
        return true;
    }

    @Override
    protected float getShadowRadius(R state) {
        if (babyRandomSize() && state.baby) {
            float babyScale = getBabyScale(state);

            shadowRadius = calculateBabyShadowRadius(babyScale);
        }
        return super.getShadowRadius(state);
    }

    @Override
    public void addRenderData(T animatable, Void relatedObject, R renderState) {
        // set the variant in the render state
        renderState.addGeckolibData(WaterfowlEntity.VARIANT_TICKET, animatable.getVariant());
        renderState.addGeckolibData(WaterfowlEntity.BABY_SCALE_TICKET, animatable.getBabyScale());
    }

    @Override
    public void scaleModelForRender(R renderState, float widthScale, float heightScale, MatrixStack poseStack, BakedGeoModel model, boolean isReRender) {
        // set the entity scale for rendering (this replaces the need to change the scale in preRender)
        float modelScale;
        if (renderState.baby) {
            modelScale = babyRandomSize()
                    ? getBabyScale(renderState)
                    : 0.7f;
        } else {
            modelScale = babyRandomSize()
                    ? 0.8f + getBabyScale(renderState) * 0.5f
                    : 1.0f;
        }

        super.scaleModelForRender(renderState, modelScale, modelScale, poseStack, model, isReRender);
    }

    public float getBabyScale(R state) {
        //noinspection DataFlowIssue
        float defaultBabyScale = WaterfowlEntity.BABY_MAX_SCALE;
        return state.hasGeckolibData(WaterfowlEntity.BABY_SCALE_TICKET)
                ? state.getGeckolibData(WaterfowlEntity.BABY_SCALE_TICKET)
                : defaultBabyScale;
    }

    public float calculateBabyShadowRadius(float babyScale) {
        return BABY_MIN_SHADOW_RADIUS +
                (babyScale - WaterfowlEntity.BABY_MIN_SCALE) /
                        (WaterfowlEntity.BABY_MAX_SCALE - WaterfowlEntity.BABY_MIN_SCALE) *
                        (BABY_MAX_SHADOW_RADIUS - BABY_MIN_SHADOW_RADIUS);
    }
}
