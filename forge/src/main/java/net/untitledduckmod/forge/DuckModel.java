package net.untitledduckmod.forge;

import net.minecraft.util.Identifier;
import net.untitledduckmod.DuckEntity;
import net.untitledduckmod.DuckMod;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class DuckModel extends AnimatedGeoModel<DuckEntity> {
    // TODO: Could use static Identifiers
    @Override
    public Identifier getModelLocation(DuckEntity object) {
        return new Identifier(DuckMod.MOD_ID, "geo/duck.geo.json");
    }

    @Override
    public Identifier getTextureLocation(DuckEntity object) {
        return new Identifier(DuckMod.MOD_ID, "textures/entity/duck.png");
    }

    @Override
    public Identifier getAnimationFileLocation(DuckEntity animatable) {
        return new Identifier(DuckMod.MOD_ID, "animations/duck.animation.json");
    }

    @Override
    public void setLivingAnimations(DuckEntity entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
            head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }
}
