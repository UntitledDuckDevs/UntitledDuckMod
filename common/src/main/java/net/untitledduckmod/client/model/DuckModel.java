package net.untitledduckmod.client.model;

import net.minecraft.util.Identifier;
import net.untitledduckmod.DuckMod;
import net.untitledduckmod.common.entity.DuckEntity;

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
            return animatable.getVariant() == 0 ? ModelIdentifiers.NORMAL_TEXTURE : ModelIdentifiers.FEMALE_TEXTURE;
        }
    }

    @Override
    public Identifier getAnimationResource(DuckEntity animatable) {
        return ModelIdentifiers.DUCK_ANIMATION_FILE_LOCATION;
    }
}
