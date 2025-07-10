package net.untitledduckmod.client.model;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.untitledduckmod.DuckMod;
import net.untitledduckmod.common.entity.DuckEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

import java.util.Objects;

public class DuckModel extends WaterfowlModel<DuckEntity> {

    public DuckModel() {
        super(DuckMod.id("duck"));
    }

    @Override
    public Identifier getModelResource(DuckEntity object) {
        return ModelIdentifiers.DUCK_MODEL_LOCATION;
    }

    @Override
    public Identifier getTextureResource(DuckEntity animatable) {
        if (animatable.isBaby()) {
            return ModelIdentifiers.DUCKLING_TEXTURE;
        } else {
            if (animatable.hasCustomName()) {
                String name = Objects.requireNonNull(animatable.getCustomName()).getString().toLowerCase();
                switch (name) {
                    case "pekin" -> {
                        return ModelIdentifiers.PEKIN_TEXTURE;
                    }
                }
            }
        }
        var variant = animatable.getVariant();

        return switch (variant) {
            case 1 -> ModelIdentifiers.FEMALE_TEXTURE;
            case 2 -> ModelIdentifiers.CAMPBELL_TEXTURE;
            default -> ModelIdentifiers.NORMAL_TEXTURE;
        };
    }

    @Override
    public Identifier getAnimationResource(DuckEntity animatable) {
        return ModelIdentifiers.DUCK_ANIMATION_FILE_LOCATION;
    }

}
