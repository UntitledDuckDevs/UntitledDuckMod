package net.untitledduckmod.client.model;

import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.util.Identifier;
import net.untitledduckmod.DuckMod;
import net.untitledduckmod.common.entity.DuckEntity;
import net.untitledduckmod.common.entity.WaterfowlEntity;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class DuckModel extends WaterfowlModel<DuckEntity> {

    public DuckModel() { super(Identifier.of(DuckMod.MOD_ID, "duck"), true); }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        if (renderState instanceof LivingEntityRenderState livingEntityRenderState && livingEntityRenderState.baby) {
            return ModelIdentifiers.DUCKLING_TEXTURE;
        } else {
            var variant = renderState.hasGeckolibData(WaterfowlEntity.VARIANT_TICKET) ? renderState.getGeckolibData(WaterfowlEntity.VARIANT_TICKET) : 0;
            //noinspection DataFlowIssue
            return variant == 0 ? ModelIdentifiers.NORMAL_TEXTURE : ModelIdentifiers.FEMALE_TEXTURE;
        }
    }
}
