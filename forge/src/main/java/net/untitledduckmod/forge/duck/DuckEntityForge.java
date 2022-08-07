package net.untitledduckmod.forge.duck;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeEntity;
import net.minecraftforge.fluids.FluidType;
import net.untitledduckmod.duck.DuckEntity;

public class DuckEntityForge extends DuckEntity implements IForgeEntity {
    public DuckEntityForge(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void jumpInFluid(FluidType type) {
        this.setVelocity(this.getVelocity().add(0.0D, 0.04D, 0.0D));
    }
}
