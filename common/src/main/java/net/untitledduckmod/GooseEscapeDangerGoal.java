package net.untitledduckmod;

import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.mob.PathAwareEntity;

public class GooseEscapeDangerGoal extends EscapeDangerGoal {
    private final PathAwareEntity mob;

    public GooseEscapeDangerGoal(PathAwareEntity mob, double speed) {
        super(mob, speed);
        this.mob = mob;
    }

    @Override
    public boolean canStart() {
        return mob.getHealth() < mob.getMaxHealth()/2 && super.canStart();
    }
}
