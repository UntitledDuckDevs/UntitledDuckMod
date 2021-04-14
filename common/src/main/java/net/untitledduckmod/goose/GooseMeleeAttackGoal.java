package net.untitledduckmod.goose;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.untitledduckmod.ModSoundEvents;

public class GooseMeleeAttackGoal extends MeleeAttackGoal {
    private final GooseEntity goose;

    public GooseMeleeAttackGoal(GooseEntity gooseEntity, double speed, boolean pauseWhenIdle) {
        super(gooseEntity, speed, pauseWhenIdle);
        this.goose = gooseEntity;
    }
    private static final int ANIMATION_LEN = GooseEntity.ANIMATION_BITE_LEN;
    private static final int ANIMATION_ATTACK = 5; // Point in animation where to attack, counted from back
    private int animationTimer = 0;

    @Override
    public boolean canStart() {
        return !goose.isBaby() && !goose.isTouchingWater() && super.canStart();
    }

    @Override
    protected void attack(LivingEntity target, double squaredDistance) {
        double d = this.getSquaredMaxAttackDistance(target);
        if (squaredDistance <= d && animationTimer <= 0) {
            goose.setAnimation(GooseEntity.ANIMATION_BITE);
            animationTimer = ANIMATION_LEN;
            goose.playSound(ModSoundEvents.getGooseHonkSound(), 0.8f, 1.2f);
        }
        if (animationTimer > 0) {
            animationTimer--;
            if (animationTimer == ANIMATION_ATTACK) {
                this.mob.tryAttack(target);
            }
            if (animationTimer == 0) {
                goose.setAnimation(GooseEntity.ANIMATION_IDLE);
            }
        }
    }

    @Override
    public void stop() {
        super.stop();
        goose.setAnimation(GooseEntity.ANIMATION_IDLE);
        animationTimer = 0;
    }
}
