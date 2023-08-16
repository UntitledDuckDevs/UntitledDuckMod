package net.untitledduckmod.goose;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

import java.util.Objects;

import static net.untitledduckmod.duck.DuckModelIdentifiers.*;

public class GooseModel extends GeoModel<GooseEntity> {
    @Override
    public Identifier getModelResource(GooseEntity object) {
        return GOOSE_MODEL_LOCATION;
    }

    @Override
    public Identifier getTextureResource(GooseEntity entity) {
        if (entity.isBaby()) {
            return GOSLING_TEXTURE;
        } else {
            if (entity.hasCustomName()) {
                String name = Objects.requireNonNull(entity.getCustomName()).getString().toLowerCase();
                switch (name) {
                    case "ping" -> {
                        return PING_GOOSE_TEXTURE;
                    }
                    case "sus" -> {
                        return SUS_GOOSE_TEXTURE;
                    }
                    case "untitled" -> {
                        return UNTITLED_GOOSE_TEXTURE;
                    }
                }
            }
        }

        return entity.getVariant() == 0 ? GOOSE_TEXTURE : CANADIAN_GOOSE_TEXTURE;
    }

    @Override
    public Identifier getAnimationResource(GooseEntity animatable) {
        return GOOSE_ANIMATION_FILE_LOCATION;
    }

    @Override
    public void setCustomAnimations(GooseEntity animatable, long instanceId, AnimationState<GooseEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        CoreGeoBone head = this.getAnimationProcessor().getBone("head");
        if (animatable.lookingAround() && head != null) {
            EntityModelData extraData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(extraData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
            head.setRotY(extraData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
        }
    }
}
