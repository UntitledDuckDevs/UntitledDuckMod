package net.untitledduckmod.fabric.goose;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3f;
import net.untitledduckmod.goose.GooseEntity;
import net.untitledduckmod.fabric.GeoMobRenderer;
import software.bernie.geckolib3.geo.render.built.GeoBone;

public class GooseRenderer extends GeoMobRenderer<GooseEntity> {
    public GooseRenderer(EntityRendererFactory.Context context) {
        super(context, new GooseModel());
        this.shadowRadius = 0.3f;
    }

    GooseEntity goose;

    @Override
    public void renderEarly(GooseEntity animatable, MatrixStack stackIn, float ticks, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        goose = animatable;
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
    }

    @Override
    public void renderRecursively(GeoBone bone, MatrixStack stack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (bone.getName().equals("beak") && !mainHand.isEmpty()) {
            stack.push();
            stack.translate(0, 1.15, -0.45);
            stack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-90));
            stack.scale(0.7f, 0.7f, 0.7f);

            MinecraftClient.getInstance().getHeldItemRenderer().renderItem(goose, mainHand,
                    ModelTransformation.Mode.GROUND, false, stack, rtb, packedLightIn);
            stack.pop();

            bufferIn = rtb.getBuffer(RenderLayer.getEntityCutout(whTexture));
        }
        super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
