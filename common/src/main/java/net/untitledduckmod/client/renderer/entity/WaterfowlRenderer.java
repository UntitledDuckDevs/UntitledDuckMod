package net.untitledduckmod.client.renderer.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;
import net.untitledduckmod.client.model.WaterfowlModel;
import net.untitledduckmod.common.entity.WaterfowlEntity;
import net.untitledduckmod.common.init.ModEntityTypes;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.util.ClientUtil;

import java.util.EnumMap;

public class WaterfowlRenderer<T extends WaterfowlEntity, R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<T, R> {
    private static final float ADULT_SHADOW_RADIUS = 0.3f;

    public WaterfowlRenderer(WaterfowlModel<T> model, EntityRendererFactory.Context context) {
        super(context, model);
        this.shadowRadius = ADULT_SHADOW_RADIUS;
    }

    @Override
    protected float getShadowRadius(R state) {
        return super.getShadowRadius(state) * state.ageScale;
    }

    @Override
    public void addRenderData(T animatable, Void relatedObject, R renderState) {
        EnumMap<EquipmentSlot, ItemStack> equipment = renderState.getOrDefaultGeckolibData(DataTickets.EQUIPMENT_BY_SLOT, new EnumMap<>(EquipmentSlot.class));
        equipment.put(EquipmentSlot.MAINHAND, animatable.getMainHandStack());
        renderState.addGeckolibData(DataTickets.EQUIPMENT_BY_SLOT, equipment);

        // set the variant in the render state
        renderState.addGeckolibData(WaterfowlEntity.VARIANT_TICKET, animatable.getVariant());
    }

    @Override
    public void scaleModelForRender(R renderState, float widthScale, float heightScale, MatrixStack poseStack, BakedGeoModel model, boolean isReRender) {
        float modelScale = renderState.ageScale;

        this.withScale(modelScale);
        // set the entity scale for rendering (this replaces the need to change the scale in preRender)
        super.scaleModelForRender(renderState, widthScale, heightScale, poseStack, model, isReRender);
    }

    @Override
    public void renderRecursively(R renderState, MatrixStack poseStack, GeoBone bone, RenderLayer renderType, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, int packedLight, int packedOverlay, int renderColor) {
        EnumMap<EquipmentSlot, ItemStack> equipment = renderState.getOrDefaultGeckolibData(DataTickets.EQUIPMENT_BY_SLOT, new EnumMap<>(EquipmentSlot.class));
        ItemStack mainHand = equipment.getOrDefault(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        if (bone.getName().equals("beak") && !mainHand.isEmpty()) {
            poseStack.push();
            if (renderState.entityType == ModEntityTypes.getDuck()) {
                poseStack.translate(0.0, 0.50, -0.40);
            } else if (renderState.entityType == ModEntityTypes.getGoose()) {
                poseStack.translate(0.0, 1.15, -0.45);
            }

            poseStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90f));
            poseStack.scale(0.7f, 0.7f, 0.7f);

            MinecraftClient.getInstance().getItemRenderer().renderItem(mainHand, ItemDisplayContext.GROUND, packedLight, packedOverlay, poseStack, bufferSource, ClientUtil.getLevel(), renderState.getGeckolibData(DataTickets.ANIMATABLE_INSTANCE_ID).intValue());
            poseStack.pop();
        }
        super.renderRecursively(renderState, poseStack, bone, renderType, bufferSource, buffer, isReRender, packedLight, packedOverlay, renderColor);
    }
}
