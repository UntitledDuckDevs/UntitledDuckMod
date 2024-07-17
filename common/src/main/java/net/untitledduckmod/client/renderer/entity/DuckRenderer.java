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
import net.untitledduckmod.client.model.DuckModel;
import net.untitledduckmod.common.entity.DuckEntity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class DuckRenderer extends GeoEntityRenderer<DuckEntity> {
    private final HeldItemRenderer heldItemRenderer;
    public DuckRenderer(EntityRendererFactory.Context context) {
        super(context, new DuckModel());
        this.shadowRadius = 0.3f;
        this.heldItemRenderer = context.getHeldItemRenderer();
    }

    @Override
    public void preRender(MatrixStack poseStack, DuckEntity animatable, BakedGeoModel model, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if (animatable.isBaby()) {
            this.withScale(0.7f);
        } else {
            this.withScale(1.0f);
        }
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void renderRecursively(MatrixStack poseStack, DuckEntity animatable, GeoBone bone, RenderLayer renderType, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        ItemStack mainHand = animatable.getMainHandStack();
        if (bone.getName().equals("beak") && !mainHand.isEmpty()) {
            poseStack.push();
            poseStack.translate(0.0, 0.50, -0.40);

            poseStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90f));
            poseStack.scale(0.7f, 0.7f, 0.7f);

            heldItemRenderer.renderItem(animatable, mainHand, ModelTransformationMode.GROUND, false, poseStack, bufferSource, packedLight);
            poseStack.pop();

            buffer = bufferSource.getBuffer(RenderLayer.getEntityCutout(this.getTexture(animatable)));
        }
        super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
