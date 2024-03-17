package net.untitledduckmod.common.entity.forge;

import net.untitledduckmod.common.entity.GooseEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeEntity;
import net.minecraftforge.fluids.FluidType;
import net.untitledduckmod.common.entity.WaterfowlEntity;

public class GooseEntityForge extends GooseEntity implements IForgeEntity {

    public GooseEntityForge(EntityType<? extends WaterfowlEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void jumpInFluid(FluidType type) {
        this.setVelocity(this.getVelocity().add(0.0D, 0.04D, 0.0D));
    }
}
