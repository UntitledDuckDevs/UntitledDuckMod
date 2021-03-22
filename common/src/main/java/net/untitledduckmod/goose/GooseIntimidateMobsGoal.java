package net.untitledduckmod.goose;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.util.math.Vec3d;
import net.untitledduckmod.ModSoundEvents;

import java.util.EnumSet;

public class GooseIntimidateMobsGoal extends Goal {
    private static final int STARTING_DELAY = 10;
    private static final int ANIMATION_LENGTH = 25;
    private static final double INTIMIDATE_DISTANCE = 12;
    private final GooseEntity goose;
    private int animationTime;
    private int delayTime;
    private int cooldown;
    protected Entity targetEntity;
    private Vec3d originalLocation;

    public GooseIntimidateMobsGoal(GooseEntity goose) {
        this.setControls(EnumSet.of(Control.LOOK, Control.MOVE));
        this.goose = goose;
    }

    @Override
    public boolean canStart() {
        // Check if creeper nearby
        if (cooldown-- > 0) {
            return false;
        }
        if (goose.age % 5 == 0) {
            targetEntity = this.goose.world.getClosestEntityIncludingUngeneratedChunks(IllagerEntity.class, new TargetPredicate(), goose, goose.getX(), goose.getY(), goose.getZ(), goose.getBoundingBox().expand(INTIMIDATE_DISTANCE, 3, INTIMIDATE_DISTANCE));
            return targetEntity != null;
        }
        return false;
    }

    @Override
    public void start() {
        goose.getNavigation().stop();
        animationTime = ANIMATION_LENGTH;
        delayTime = STARTING_DELAY;

        originalLocation = goose.getPos();
        goose.getNavigation().startMovingTo(targetEntity, 1.2);
        System.out.println("START");
    }

    @Override
    public void stop() {
        goose.setAnimation(GooseEntity.ANIMATION_IDLE);
        goose.getNavigation().startMovingTo(originalLocation.x, originalLocation.y, originalLocation.z, 1.2);
        cooldown = 20;
        targetEntity = null;
        System.out.println("STOP");
    }

    @Override
    public boolean shouldContinue() {
        return targetEntity.isAlive() && goose.squaredDistanceTo(originalLocation) <= INTIMIDATE_DISTANCE*INTIMIDATE_DISTANCE && animationTime >= 0;
    }

    @Override
    public void tick() {
        goose.getLookControl().lookAt(targetEntity.getX(), targetEntity.getEyeY(), targetEntity.getZ());
        if (delayTime > 0) {
            delayTime--;
            if (delayTime == 0) {
                goose.getNavigation().stop();
                goose.setAnimation(GooseEntity.ANIMATION_INTIMIDATE);
                goose.playSound(ModSoundEvents.getGooseHonkSound(), 1.0f, 1.0f);
            }
            return;
        }
        animationTime--;
    }

}
