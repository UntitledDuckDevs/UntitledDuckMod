package net.untitledduckmod.client.model;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.untitledduckmod.common.entity.DuckEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.renderer.GeoRenderer;

public class DuckModel extends GeoModel<DuckEntity> {

    @Override
    public Identifier getModelResource(DuckEntity animatable, @Nullable GeoRenderer<DuckEntity> renderer) {
        return ModelIdentifiers.DUCK_MODEL_LOCATION;
    }

    @Override
    public Identifier getTextureResource(DuckEntity animatable, @Nullable GeoRenderer<DuckEntity> renderer) {
        if (animatable.isBaby()) {
            return ModelIdentifiers.DUCKLING_TEXTURE;
        } else {
            return animatable.getVariant() == 0 ? ModelIdentifiers.NORMAL_TEXTURE : ModelIdentifiers.FEMALE_TEXTURE;
        }
    }

    @Override
    public Identifier getAnimationResource(DuckEntity animatable) {
        return ModelIdentifiers.DUCK_ANIMATION_FILE_LOCATION;
    }

    @Override
    public void setCustomAnimations(DuckEntity animatable, long instanceId, AnimationState<DuckEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        GeoBone head = this.getAnimationProcessor().getBone("head");
        if (animatable.lookingAround() && head != null) {
            EntityModelData extraData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(extraData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
            head.setRotY(extraData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
        }
    }
}
