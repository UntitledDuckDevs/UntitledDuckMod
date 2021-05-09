package net.untitledduckmod.forge.duck;

import net.minecraft.util.Identifier;
import net.untitledduckmod.duck.DuckEntity;
import net.untitledduckmod.duck.DuckModelIdentifiers;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import static net.untitledduckmod.duck.DuckModelIdentifiers.*;

public class DuckModel extends AnimatedGeoModel<DuckEntity> {
    private Identifier currentTexture = NORMAL_TEXTURE;

    @Override
    public Identifier getModelLocation(DuckEntity object) {
        return DuckModelIdentifiers.DUCK_MODEL_LOCATION;
    }

    @Override
    public Identifier getTextureLocation(DuckEntity object) {
        return currentTexture;
    }

    @Override
    public Identifier getAnimationFileLocation(DuckEntity animatable) {
        return DuckModelIdentifiers.DUCK_ANIMATION_FILE_LOCATION;
    }

    @Override
    public void setLivingAnimations(DuckEntity entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);

        if (entity.isBaby()) {
            IBone root = this.getAnimationProcessor().getBone("root");
            root.setScaleX(0.7f);
            root.setScaleY(0.7f);
            root.setScaleZ(0.7f);
            currentTexture = DUCKLING_TEXTURE;
        } else {
            currentTexture = entity.getVariant() == 0 ? NORMAL_TEXTURE : FEMALE_TEXTURE;
        }

        IBone head = this.getAnimationProcessor().getBone("head");
        if (entity.lookingAround() && head != null) {
            EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
            head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }
}
