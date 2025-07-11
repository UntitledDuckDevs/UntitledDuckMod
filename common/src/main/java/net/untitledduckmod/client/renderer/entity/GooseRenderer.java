package net.untitledduckmod.client.renderer.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.untitledduckmod.client.model.GooseModel;
import net.untitledduckmod.common.config.UntitledConfig;
import net.untitledduckmod.common.entity.GooseEntity;

public class GooseRenderer extends WaterfowlRenderer<GooseEntity> {

    public GooseRenderer(EntityRendererFactory.Context context) {
        super(context, new GooseModel());
    }

    @Override
    public boolean babyRandomSize() {
        return UntitledConfig.gooseBabyRandomSize();
    }
}
