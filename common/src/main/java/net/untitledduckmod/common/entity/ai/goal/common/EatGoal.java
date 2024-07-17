package net.untitledduckmod.common.entity.ai.goal.common;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.item.ItemStack;
import net.untitledduckmod.common.entity.DuckEntity;
import net.untitledduckmod.common.entity.GooseEntity;
import net.untitledduckmod.common.entity.WaterfowlEntity;

import java.util.EnumSet;

public class EatGoal extends Goal {
    private static final int STARTING_DELAY = 10;
    private static final int ANIMATION_LENGTH = 22;
    private static final int ANIMATION_EAT_POINT = ANIMATION_LENGTH - 13;
    private final WaterfowlEntity entity;
    private int animationTime;
    private int delayTime;

    public EatGoal(WaterfowlEntity entity) {
        this.setControls(EnumSet.of(Control.LOOK, Control.MOVE));
        this.entity = entity;
    }

    @Override
    public boolean canStart() {
        // TODO: Should throttle this?
        if (!entity.isHungry()) {
            return false;
        }
        ItemStack stack = entity.getMainHandStack();
        if (stack == null || stack.isEmpty()) {
            return false;
        }
        if (entity instanceof DuckEntity) {
            return DuckEntity.TAMING_INGREDIENT.test(stack) || DuckEntity.BREEDING_INGREDIENT.test(stack);
        }
        return GooseEntity.FOOD.test(stack);
    }

    @Override
    public void start() {
        entity.getNavigation().stop();
        animationTime = ANIMATION_LENGTH;
        delayTime = STARTING_DELAY;
    }

    @Override
    public void stop() {
        entity.setAnimation(WaterfowlEntity.ANIMATION_IDLE);
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
                entity.setAnimation(WaterfowlEntity.ANIMATION_EAT);
            }
            return;
        }
        animationTime--;
        if (animationTime == ANIMATION_EAT_POINT) {
            entity.tryEating();
        }
    }
}

