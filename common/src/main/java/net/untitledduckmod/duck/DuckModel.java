package net.untitledduckmod.duck;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

import static net.untitledduckmod.duck.DuckModelIdentifiers.*;

public class DuckModel extends GeoModel<DuckEntity> {

    @Override
    public Identifier getModelResource(DuckEntity object) {
        return DUCK_MODEL_LOCATION;
    }

    @Override
    public Identifier getTextureResource(DuckEntity animatable) {
        if (animatable.isBaby()) {
            return DUCKLING_TEXTURE;
        } else {
            return animatable.getVariant() == 0 ? NORMAL_TEXTURE : FEMALE_TEXTURE;
        }
    }

    @Override
    public Identifier getAnimationResource(DuckEntity animatable) {
        return DUCK_ANIMATION_FILE_LOCATION;
    }

    @Override
    public void setCustomAnimations(DuckEntity animatable, long instanceId, AnimationState<DuckEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        CoreGeoBone head = this.getAnimationProcessor().getBone("head");
        if (animatable.lookingAround() && head != null) {
            EntityModelData extraData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(extraData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
            head.setRotY(extraData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
        }
    }
}
