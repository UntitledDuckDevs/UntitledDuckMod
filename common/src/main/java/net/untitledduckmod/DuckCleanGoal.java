package net.untitledduckmod;

import net.minecraft.entity.ai.goal.Goal;

import java.util.EnumSet;

public class DuckCleanGoal extends Goal {
    private final DuckEntity duck;
    private static final int ANIMATION_LENGTH = 32;
    private int cleanTime;
    private int nextCleanTime;

    public DuckCleanGoal(DuckEntity duck) {
        this.duck = duck;
        this.setControls(EnumSet.of(Control.LOOK, Control.MOVE));
        nextCleanTime = duck.age + (10 * 20 + duck.getRandom().nextInt(10) * 20);
    }

    @Override
    public boolean canStart() {
        // Don't clean if not near player
        if (nextCleanTime > duck.age || duck.getDespawnCounter() >= 100 || duck.getAnimation() != DuckEntity.ANIMATION_IDLE) {
            return false;
        }
        return duck.getRandom().nextInt(40) == 0;
    }

    @Override
    public void start() {
        //System.out.printf("[%d:%d] Start cleaning\n", nextCleanTime,duck.age);
        cleanTime = ANIMATION_LENGTH;
        duck.setAnimation(DuckEntity.ANIMATION_CLEAN);
        nextCleanTime = duck.age + (10 * 20 + duck.getRandom().nextInt(10) * 20);
    }

    @Override
    public void stop() {
        duck.setAnimation(DuckEntity.ANIMATION_IDLE);
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
