package net.untitledduckmod.goose;

import net.minecraft.entity.ai.goal.RevengeGoal;

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
