package net.untitledduckmod.goose;

import net.minecraft.entity.ai.goal.Goal;

import java.util.EnumSet;

public class GooseCleanGoal extends Goal {
    private static final int ANIMATION_LENGTH = 32;
    private final GooseEntity goose;
    private int cleanTime;
    private int nextCleanTime;

    public GooseCleanGoal(GooseEntity goose) {
        this.goose = goose;
        this.setControls(EnumSet.of(Control.LOOK, Control.MOVE));
        nextCleanTime = goose.age + (10 * 20 + goose.getRandom().nextInt(10) * 20);
    }

    @Override
    public boolean canStart() {
        // Don't clean if not near player
        if (nextCleanTime > goose.age || goose.getDespawnCounter() >= 100 || goose.getAnimation() != GooseEntity.ANIMATION_IDLE) {
            return false;
        }
        return goose.getRandom().nextInt(40) == 0;
    }

    @Override
    public void start() {
        cleanTime = ANIMATION_LENGTH;
        goose.setAnimation(GooseEntity.ANIMATION_CLEAN);
        nextCleanTime = goose.age + (10 * 20 + goose.getRandom().nextInt(10) * 20);
    }

    @Override
    public void stop() {
        goose.setAnimation(GooseEntity.ANIMATION_IDLE);
    }

    @Override
    public boolean shouldContinue() {
        return cleanTime >= 0;
    }

    @Override
    public void tick() {
        cleanTime--;
    }
}
