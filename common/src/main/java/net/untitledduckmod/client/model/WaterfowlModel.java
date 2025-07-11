package net.untitledduckmod.client.model;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.untitledduckmod.common.entity.WaterfowlEntity;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class WaterfowlModel<T extends WaterfowlEntity>  extends DefaultedEntityGeoModel<T> {
    public WaterfowlModel(Identifier assetSubpath) {
        super(assetSubpath);
    }

    @Override
    public void setCustomAnimations(T animatable, long instanceId, AnimationState<T> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        GeoBone head = this.getAnimationProcessor().getBone("head");
        if (animatable.lookingAround() && head != null) {
            EntityModelData extraData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(extraData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
            head.setRotY(extraData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
        }
    }
}
