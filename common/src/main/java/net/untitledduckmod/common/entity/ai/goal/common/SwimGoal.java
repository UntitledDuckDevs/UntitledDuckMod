package net.untitledduckmod.common.entity.ai.goal.common;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.registry.tag.FluidTags;
import net.untitledduckmod.common.entity.WaterfowlEntity;

import java.util.EnumSet;

public class SwimGoal extends Goal {
    private final WaterfowlEntity entity;

    public SwimGoal(WaterfowlEntity entity) {
        this.entity = entity;
        this.setControls(EnumSet.of(Goal.Control.JUMP));
        entity.getNavigation().setCanSwim(true);
    }

    @Override
    public boolean canStart() {
        return entity.isTouchingWater() && entity.getFluidHeight(FluidTags.WATER) > (entity.isBaby() ? 0.1D : 0.2D) || entity.isInLava();
    }

    @Override
    public void tick() {
        if (entity.getRandom().nextFloat() < 0.8F) {
            entity.getJumpControl().setActive();
        }
    }
}
