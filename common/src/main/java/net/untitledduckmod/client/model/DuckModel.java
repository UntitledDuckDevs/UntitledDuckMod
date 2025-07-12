package net.untitledduckmod.client.model;

import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.util.Identifier;
import net.untitledduckmod.DuckMod;
import net.untitledduckmod.common.entity.DuckEntity;
import net.untitledduckmod.common.entity.WaterfowlEntity;
import software.bernie.geckolib.renderer.base.GeoRenderState;

import java.util.Objects;

public class DuckModel extends WaterfowlModel<DuckEntity> {

    public DuckModel() {
        super(Identifier.of(DuckMod.MOD_ID, "duck"));
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        if (renderState instanceof LivingEntityRenderState livingEntityRenderState && livingEntityRenderState.baby) {
            return ModelIdentifiers.DUCKLING_TEXTURE;
        } else {
            if (renderState instanceof LivingEntityRenderState livingEntityRenderState) {

                if (livingEntityRenderState.baby) {
                    return ModelIdentifiers.DUCKLING_TEXTURE;
                } else if (livingEntityRenderState.customName != null) {
                    String name = Objects.requireNonNull(livingEntityRenderState.customName).getString().toLowerCase();
                    switch (name) {
                        case "pekin" -> {
                            return ModelIdentifiers.PEKIN_TEXTURE;
                        }
                    }
                }
            }
            var variant = renderState.hasGeckolibData(WaterfowlEntity.VARIANT_TICKET) ? renderState.getGeckolibData(WaterfowlEntity.VARIANT_TICKET) : 0;
            //noinspection DataFlowIssue
            return switch (variant) {
                case 1 -> ModelIdentifiers.FEMALE_TEXTURE;
                case 2 -> ModelIdentifiers.CAMPBELL_TEXTURE;
                default -> ModelIdentifiers.NORMAL_TEXTURE;
            };
        }
    }
}
