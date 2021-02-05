package net.untitledduckmod.fabric;

import net.minecraft.util.Identifier;
import net.untitledduckmod.DuckEntity;
import net.untitledduckmod.DuckMod;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class DuckModel extends AnimatedGeoModel<DuckEntity> {

    // TODO: Could share these identifiers between fabric/forge
    public static Identifier NORMAL_TEXTURE = new Identifier(DuckMod.MOD_ID, "textures/entity/duck.png");
    public static Identifier FEMALE_TEXTURE = new Identifier(DuckMod.MOD_ID, "textures/entity/duck_female.png");
    public static Identifier DUCKLING_TEXTURE = new Identifier(DuckMod.MOD_ID, "textures/entity/duckling.png");
    private Identifier currentTexture = NORMAL_TEXTURE;

    @Override
    public Identifier getModelLocation(DuckEntity object) {
        return new Identifier(DuckMod.MOD_ID, "geo/duck.geo.json");
    }

    @Override
    public Identifier getTextureLocation(DuckEntity object) {
        return currentTexture;
    }

    @Override
    public Identifier getAnimationFileLocation(DuckEntity animatable) {
        return new Identifier(DuckMod.MOD_ID, "animations/duck.animation.json");
    }

    @Override
    public void setLivingAnimations(DuckEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);

        if (entity.isBaby()) {
            IBone root = this.getAnimationProcessor().getBone("root");
            root.setScaleX(0.7f);
            root.setScaleY(0.7f);
            root.setScaleZ(0.7f);
        }
        currentTexture = entity.isBaby() ? DUCKLING_TEXTURE : NORMAL_TEXTURE;

        IBone head = this.getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
            head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }
}
