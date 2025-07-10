package net.untitledduckmod.client.model;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.untitledduckmod.common.entity.DuckEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class DuckModel extends WaterfowlModel<DuckEntity> {

    public DuckModel(Identifier assetSubpath) {
        super(assetSubpath);
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
