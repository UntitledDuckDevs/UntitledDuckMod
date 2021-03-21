package net.untitledduckmod.goose;

import net.minecraft.entity.ai.goal.RevengeGoal;

public class GooseRevengeGoal extends RevengeGoal {
    private final GooseEntity goose;

    public GooseRevengeGoal(GooseEntity goose) {
        super(goose, new Class[0]);
        this.goose = goose;
    }


    @Override
    public boolean canStart() {
        if (goose.isBaby()) {
            return false;
        }
        return super.canStart();
    }

    @Override
    public boolean shouldContinue() {
        return goose.getTarget() != null && super.shouldContinue();
    }
}
