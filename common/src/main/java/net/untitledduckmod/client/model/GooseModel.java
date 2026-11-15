package net.untitledduckmod.client.model;

import net.minecraft.util.Identifier;
import net.untitledduckmod.DuckMod;
import net.untitledduckmod.common.entity.GooseEntity;

import java.util.Objects;


public class GooseModel extends WaterfowlModel<GooseEntity> {
    public GooseModel() {
        super(DuckMod.id("goose"));
    }

    @Override
    public Identifier getModelResource(GooseEntity object) {
        return ModelIdentifiers.GOOSE_MODEL_LOCATION;
    }

    @Override
    public Identifier getTextureResource(GooseEntity animatable) {
        if (animatable.isBaby()) {
            return ModelIdentifiers.GOSLING_TEXTURE;
        } else {
            if (animatable.hasCustomName()) {
                String name = Objects.requireNonNull(animatable.getCustomName()).getString().toLowerCase();
                switch (name) {
                    case "ping" -> {
                        return ModelIdentifiers.PING_GOOSE_TEXTURE;
                    }
                    case "sus" -> {
                        return ModelIdentifiers.SUS_GOOSE_TEXTURE;
                    }
                    case "untitled" -> {
                        return ModelIdentifiers.UNTITLED_GOOSE_TEXTURE;
                    }
                }
            }
        }
        var variant = animatable.getVariant();

        return switch (variant) {
            case 1 -> ModelIdentifiers.CANADIAN_GOOSE_TEXTURE;
            case 2 -> ModelIdentifiers.GREYLAG_GOOSE_TEXTURE;
            default -> ModelIdentifiers.GOOSE_TEXTURE;
        };
    }

    @Override
    public Identifier getAnimationResource(GooseEntity animatable) {
        return ModelIdentifiers.GOOSE_ANIMATION_FILE_LOCATION;
    }
}
