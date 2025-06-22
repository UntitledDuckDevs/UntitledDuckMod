package net.untitledduckmod.client.model;

import net.minecraft.util.Identifier;
import net.untitledduckmod.common.entity.WaterfowlEntity;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public abstract class WaterfowlModel<T extends WaterfowlEntity>  extends DefaultedEntityGeoModel<T> {
    public WaterfowlModel(Identifier assetSubpath, boolean turnsHead) { super(assetSubpath, turnsHead); }
}
