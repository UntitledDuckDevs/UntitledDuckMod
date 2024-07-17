package net.untitledduckmod.common.entity.ai.goal.goose;

import net.minecraft.entity.ai.goal.RevengeGoal;
import net.untitledduckmod.common.entity.GooseEntity;

public class GooseRevengeGoal extends RevengeGoal {
    private final GooseEntity goose;

    public GooseRevengeGoal(GooseEntity goose) {
        super(goose);
        this.goose = goose;
    }

    @Override
    public boolean shouldContinue() {
        return goose.getTarget() != null && super.shouldContinue();
    }
}

