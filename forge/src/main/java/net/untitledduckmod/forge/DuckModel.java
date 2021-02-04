package net.untitledduckmod.forge;

import net.minecraft.util.Identifier;
import net.untitledduckmod.DuckEntity;
import net.untitledduckmod.DuckMod;
import software.bernie.geckolib3.model.AnimatedGeoModel;

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
}
