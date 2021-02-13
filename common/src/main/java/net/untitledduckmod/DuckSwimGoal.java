package net.untitledduckmod;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.tag.FluidTags;

import java.util.EnumSet;

public class DuckSwimGoal extends Goal {
    private final DuckEntity duck;

    public DuckSwimGoal(DuckEntity duck) {
        this.duck = duck;
        this.setControls(EnumSet.of(Goal.Control.JUMP));
        duck.getNavigation().setCanSwim(true);
    }

    @Override
    public boolean canStart() {
        return duck.isTouchingWater() && duck.getFluidHeight(FluidTags.WATER) > (duck.isBaby() ? 0.1D : 0.2D) || duck.isInLava();
    }

    public void tick() {
        if (duck.getRandom().nextFloat() < 0.8F) {
            duck.getJumpControl().setActive();
        }
    }
}
