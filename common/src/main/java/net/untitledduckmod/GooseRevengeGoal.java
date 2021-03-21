package net.untitledduckmod;

import net.minecraft.entity.ai.goal.RevengeGoal;

public class GooseRevengeGoal extends RevengeGoal {
    private final GooseEntity goose;

    public GooseRevengeGoal(GooseEntity goose) {
        super(goose, new Class[0]);
        this.goose = goose;
    }

    @Override
    public boolean shouldContinue() {
        return goose.getTarget() != null && super.shouldContinue();
    }
}
