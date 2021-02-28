package net.untitledduckmod;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.sound.SoundEvents;

import java.util.EnumSet;

public class DuckDiveGoal extends Goal {
    private static final int ANIMATION_LENGTH = 32;
    private final DuckEntity duck;
    private int diveTime;
    private int nextDiveTime;

    public DuckDiveGoal(DuckEntity duck) {
        this.duck = duck;
        this.setControls(EnumSet.of(Control.LOOK, Control.MOVE));
        nextDiveTime = duck.age + (8 * 20 + duck.getRandom().nextInt(10) * 20);
    }

    @Override
    public boolean canStart() {
        // Don't dive if not near player
        if (nextDiveTime > duck.age || duck.getDespawnCounter() >= 100 || !duck.isTouchingWater() || duck.getAnimation() != DuckEntity.ANIMATION_IDLE) {
            return false;
        }
        return duck.getRandom().nextInt(40) == 0;
    }

    @Override
    public void start() {
        //System.out.printf("[%d:%d] Start diving\n", nextDiveTime, duck.age);
        diveTime = ANIMATION_LENGTH;
        duck.setAnimation(DuckEntity.ANIMATION_DIVE);
        nextDiveTime = duck.age + (8 * 20 + duck.getRandom().nextInt(10) * 20);
    }

    @Override
    public void stop() {
        duck.setAnimation(DuckEntity.ANIMATION_IDLE);
    }

    @Override
    public boolean shouldContinue() {
        return diveTime >= 0;
    }

    @Override
    public void tick() {
        diveTime--;
        // Play splash sound 10 ticks = 0.5 seconds into the animation
        if (diveTime == 32 - 10) {
            duck.playSound(SoundEvents.ENTITY_GENERIC_SPLASH, 1.0f, 1.0f);
        }
    }
}
