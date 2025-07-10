package net.untitledduckmod.client.renderer.entity;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;
import net.untitledduckmod.common.entity.DuckEntity;
import net.untitledduckmod.common.entity.GooseEntity;
import net.untitledduckmod.common.entity.WaterfowlEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;

public class WaterfowlRenderer<T extends WaterfowlEntity> extends GeoEntityRenderer<T> {

    private static final float ADULT_SHADOW_RADIUS = 0.3f;
    private static final float BABY_MIN_SHADOW_RADIUS = 0.08f;
    private static final float BABY_MAX_SHADOW_RADIUS = 0.21f;

    protected ItemStack mainHandItem;

    public WaterfowlRenderer(EntityRendererFactory.Context renderManager, GeoModel<T> model) {
        super(renderManager, model);
        this.shadowRadius = ADULT_SHADOW_RADIUS;

        addRenderLayer(new BlockAndItemGeoLayer<>(this) {
            @Nullable
            @Override
            protected ItemStack getStackForBone(GeoBone bone, T animatable) {
                ItemStack var10000;
                if (bone.getName().equals("beak") && !mainHandItem.isEmpty()) {
                    var10000 = mainHandItem;
                } else {
                    var10000 = null;
                }

                return var10000;
            }

            @Override
            protected ModelTransformationMode getTransformTypeForStack(GeoBone bone, ItemStack stack, T animatable) {
                ModelTransformationMode var10000;
                if (bone.getName().equals("bipedHandRight")) {
                    var10000 = ModelTransformationMode.THIRD_PERSON_RIGHT_HAND;
                } else {
                    var10000 = ModelTransformationMode.NONE;
                }

                return var10000;
            }

            @Override
            protected void renderStackForBone(MatrixStack poseStack, GeoBone bone, ItemStack stack, T animatable, VertexConsumerProvider bufferSource, float partialTick, int packedLight, int packedOverlay) {
                if (stack == mainHandItem) {
                    poseStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90.0F));
                    poseStack.scale(0.7f, 0.7f, 0.7f);
                    if (animatable instanceof DuckEntity) {
                        poseStack.translate(0.0, 0.50, -0.40);
                    } else if (animatable instanceof GooseEntity) {
                        poseStack.translate(0.0, 1.15, -0.45);
                    }
                }

                super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight, packedOverlay);
            }

        });
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

    public float calculateBabyShadowRadius(float babyScale) {
        return BABY_MIN_SHADOW_RADIUS +
                (babyScale - WaterfowlEntity.BABY_MIN_SCALE) /
                        (WaterfowlEntity.BABY_MAX_SCALE - WaterfowlEntity.BABY_MIN_SCALE) *
                        (BABY_MAX_SHADOW_RADIUS - BABY_MIN_SHADOW_RADIUS);
    }

    @Override
    public void preRender(MatrixStack poseStack, T animatable, BakedGeoModel model, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.mainHandItem = animatable.getMainHandStack();
        float babyScale = getBabyScale(animatable);
        if (babyRandomSize() && animatable.isBaby()) {

            shadowRadius = calculateBabyShadowRadius(babyScale);
        }
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
}
