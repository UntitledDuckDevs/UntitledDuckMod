package net.untitledduckmod.forge.goose;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeEntity;
import net.minecraftforge.fluids.FluidType;
import net.untitledduckmod.goose.GooseEntity;

public class GooseEntityForge extends GooseEntity implements IForgeEntity {

    public GooseEntityForge(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void jumpInFluid(FluidType type) {
        this.setVelocity(this.getVelocity().add(0.0D, 0.04D, 0.0D));
    }
}
