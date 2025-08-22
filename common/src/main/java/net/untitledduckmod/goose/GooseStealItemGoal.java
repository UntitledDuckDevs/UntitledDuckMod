package net.untitledduckmod.goose;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

import java.util.EnumSet;

public class GooseStealItemGoal extends Goal {
    private static final double SPEED = 1.3D;
    private final GooseEntity goose;

    PlayerEntity targetPlayer;
    int nextStealTime;
    private ItemStack playerHandStack;

    public GooseStealItemGoal(GooseEntity goose) {
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
        this.goose = goose;
        nextStealTime = goose.age + goose.getRandom().nextInt(20 * 60) + 20 * 60;
    }

    @Override
    public boolean canStart() {
        // Only wild geese with nothing in their beak(main hand) will attempt to steal items from you
        if (goose.isBaby() || goose.isTamed() || !goose.getMainHandStack().isEmpty()) {
            return false;
        }
        if (goose.age <= nextStealTime) {
            return false;
        }
        // Throttle starts
        if (goose.getRandom().nextInt(10) != 0) {
            return false;
        }
        targetPlayer = goose.world.getClosestPlayer(goose.getX(), goose.getY(), goose.getZ(), 10.0D, true);
        if (targetPlayer == null) {
            nextStealTime = goose.age + goose.getRandom().nextInt(10 * 20) + 10 * 20;
            return false;
        }

        playerHandStack = targetPlayer.getMainHandStack();
        return GooseEntity.FOOD.test(playerHandStack);
    }

    @Override
    public boolean shouldContinue() {
        if (targetPlayer == null) {
            return false;
        }

        playerHandStack = targetPlayer.getMainHandStack();
        return GooseEntity.FOOD.test(playerHandStack);
    }

    @Override
    public void start() {
        goose.getNavigation().startMovingTo(targetPlayer, SPEED);
    }

    @Override
    public void stop() {
        nextStealTime = goose.age + goose.getRandom().nextInt(20 * 60) + 20 * 60;
        targetPlayer = null;
    }

    @Override
    public void tick() {
        if (goose.distanceTo(targetPlayer) <= 2.0f) {
            ItemStack stolenItemStack = playerHandStack.copy();
            stolenItemStack.setCount(1);
            if (!goose.tryEquip(stolenItemStack).isEmpty()) {
                playerHandStack.decrement(1);
            }

            goose.setStackInHand(Hand.MAIN_HAND, stolenItemStack);

            stop();
        } else {
            // Continue going to the player
            goose.getNavigation().startMovingTo(targetPlayer, SPEED);
        }
    }
}
