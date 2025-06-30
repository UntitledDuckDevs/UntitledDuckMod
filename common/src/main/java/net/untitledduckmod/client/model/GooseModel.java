package net.untitledduckmod.client.model;

import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.util.Identifier;
import net.untitledduckmod.DuckMod;
import net.untitledduckmod.common.entity.GooseEntity;
import net.untitledduckmod.common.entity.WaterfowlEntity;
import software.bernie.geckolib.renderer.base.GeoRenderState;

import java.util.Objects;

public class GooseModel extends WaterfowlModel<GooseEntity> {

    public GooseModel() {
        super(Identifier.of(DuckMod.MOD_ID, "goose"));
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        if (renderState instanceof LivingEntityRenderState livingEntityRenderState) {

            if (livingEntityRenderState.baby) {
                return ModelIdentifiers.GOSLING_TEXTURE;
            } else if (livingEntityRenderState.customName != null) {
                String name = Objects.requireNonNull(livingEntityRenderState.customName).getString().toLowerCase();
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

        var variant = renderState.hasGeckolibData(WaterfowlEntity.VARIANT_TICKET) ? renderState.getGeckolibData(WaterfowlEntity.VARIANT_TICKET) : 0;
        //noinspection DataFlowIssue
        return variant == 0 ? ModelIdentifiers.GOOSE_TEXTURE : ModelIdentifiers.CANADIAN_GOOSE_TEXTURE;
    }
}
