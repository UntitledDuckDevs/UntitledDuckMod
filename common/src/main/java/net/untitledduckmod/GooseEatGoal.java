package net.untitledduckmod;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.item.ItemStack;

import java.util.EnumSet;

public class GooseEatGoal extends Goal {
    private static final int STARTING_DELAY = 10;
    private static final int ANIMATION_LENGTH = 22;
    private static final int ANIMATION_EAT_POINT = ANIMATION_LENGTH - 13;
    private final GooseEntity goose;
    private int animationTime;
    private int delayTime;

    public GooseEatGoal(GooseEntity goose) {
        this.setControls(EnumSet.of(Control.LOOK, Control.MOVE));
        this.goose = goose;
    }

    @Override
    public boolean canStart() {
        // TODO: Should throttle this?
        if (!goose.isHungry()) {
            return false;
        }
        ItemStack stack = goose.getMainHandStack();
        if (stack == null || stack.isEmpty()) {
            return false;
        }
        return GooseEntity.FOOD.test(stack);
    }

    @Override
    public void start() {
        goose.getNavigation().stop();
        animationTime = ANIMATION_LENGTH;
        delayTime = STARTING_DELAY;
    }

    @Override
    public void stop() {
        goose.setAnimation(GooseEntity.ANIMATION_IDLE);
    }

    @Override
    public boolean shouldContinue() {
        return animationTime >= 0;
    }

    @Override
    public void tick() {
        if (delayTime > 0) {
            delayTime--;
            if (delayTime == 0) {
                goose.setAnimation(GooseEntity.ANIMATION_EAT);
            }
            return;
        }
        animationTime--;
        if (animationTime == ANIMATION_EAT_POINT) {
            goose.tryEating();
        }
    }
}
