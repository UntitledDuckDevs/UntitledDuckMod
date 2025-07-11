package net.untitledduckmod.client.renderer.entity;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;
import net.untitledduckmod.common.entity.DuckEntity;
import net.untitledduckmod.common.entity.GooseEntity;
import net.untitledduckmod.common.entity.WaterfowlEntity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class WaterfowlRenderer<T extends WaterfowlEntity> extends GeoEntityRenderer<T> {

    private static final float ADULT_SHADOW_RADIUS = 0.3f;

    private final HeldItemRenderer heldItemRenderer;
    protected ItemStack mainHandItem;

    public WaterfowlRenderer(EntityRendererFactory.Context renderManager, GeoModel<T> model) {
        super(renderManager, model);
        this.shadowRadius = ADULT_SHADOW_RADIUS;
        this.heldItemRenderer = renderManager.getHeldItemRenderer();
    }

    public boolean babyRandomSize() {
        return true;
    }

    public float getBabyScale(T animatable) {
        float defaultBabyScale = WaterfowlEntity.BABY_MAX_SCALE;
        float minScale = WaterfowlEntity.BABY_MIN_SCALE;
        float scale = animatable.getBabyScale();
        return scale >= minScale && scale <= defaultBabyScale
                ? animatable.getBabyScale()
                : defaultBabyScale;
    }

    @Override
    public void preRender(MatrixStack poseStack, T animatable, BakedGeoModel model, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.mainHandItem = animatable.getMainHandStack();
        float babyScale = getBabyScale(animatable);
        float modelScale;
        if (animatable.isBaby()) {
            modelScale = babyRandomSize()
                    ? babyScale
                    : 0.7f;
        } else {
            modelScale = babyRandomSize()
                    ? 0.8f + babyScale * 0.5f
                    : 1.0f;
        }
        this.withScale(modelScale);
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void renderRecursively(MatrixStack poseStack, T animatable, GeoBone bone, RenderLayer renderType, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        ItemStack mainHand = animatable.getMainHandStack();
        if (bone.getName().equals("beak") && !mainHand.isEmpty()) {
            poseStack.push();
            if (animatable instanceof DuckEntity) {
                poseStack.translate(0.0, 0.50, -0.40);
            } else if (animatable instanceof GooseEntity) {
                poseStack.translate(0.0, 1.15, -0.45);
            }

            poseStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90f));
            poseStack.scale(0.7f, 0.7f, 0.7f);

            heldItemRenderer.renderItem(animatable, mainHand, ModelTransformationMode.GROUND, false, poseStack, bufferSource, packedLight);
            poseStack.pop();

            buffer = bufferSource.getBuffer(RenderLayer.getEntityCutout(this.getTexture(animatable)));
        }
        super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
