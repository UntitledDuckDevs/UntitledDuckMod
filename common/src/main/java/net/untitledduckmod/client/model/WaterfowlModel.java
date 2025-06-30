package net.untitledduckmod.client.model;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.untitledduckmod.common.entity.WaterfowlEntity;
import software.bernie.geckolib.animatable.processing.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public abstract class WaterfowlModel<T extends WaterfowlEntity>  extends DefaultedEntityGeoModel<T> {
    public WaterfowlModel(Identifier assetSubpath) { super(assetSubpath); }

    @Override
    public void addAdditionalStateData(WaterfowlEntity animatable, GeoRenderState renderState) {
        renderState.addGeckolibData(WaterfowlEntity.LOOKING_AROUND, animatable.lookingAround());
    }

    @Override
    public void setCustomAnimations(AnimationState<T> animationState) {
        super.setCustomAnimations(animationState);
        GeoBone head = getAnimationProcessor().getBone("head");
        boolean lookingAround = animationState.getData(WaterfowlEntity.LOOKING_AROUND);

        if (lookingAround && head != null) {
            float pitch = animationState.getData(DataTickets.ENTITY_PITCH);
            float yaw = animationState.getData(DataTickets.ENTITY_YAW);

            head.setRotX(-pitch * MathHelper.RADIANS_PER_DEGREE);
            head.setRotY(-yaw * MathHelper.RADIANS_PER_DEGREE);
        }
    }
}
