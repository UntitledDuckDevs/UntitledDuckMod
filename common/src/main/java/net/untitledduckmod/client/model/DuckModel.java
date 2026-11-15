package net.untitledduckmod.client.model;

import net.minecraft.util.Identifier;
import net.untitledduckmod.DuckMod;
import net.untitledduckmod.common.entity.DuckEntity;

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
        }
        var variant = animatable.getVariant();

        return switch (variant) {
            case 1 -> ModelIdentifiers.FEMALE_TEXTURE;
            case 2 -> ModelIdentifiers.CAMPBELL_TEXTURE;
            case 3 -> ModelIdentifiers.PEKIN_TEXTURE;
            default -> ModelIdentifiers.NORMAL_TEXTURE;
        };
    }

    @Override
    public Identifier getAnimationResource(DuckEntity animatable) {
        return ModelIdentifiers.DUCK_ANIMATION_FILE_LOCATION;
    }

}
