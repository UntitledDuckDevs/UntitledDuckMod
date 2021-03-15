package net.untitledduckmod;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.item.ItemStack;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class GoosePickupItemGoal extends Goal {
    public static final double SPEED = 1.3D;
    private final GooseEntity goose;

    public GoosePickupItemGoal(GooseEntity goose) {
        this.goose = goose;
        this.setControls(EnumSet.of(Goal.Control.MOVE));
    }

    private static final Predicate<ItemEntity> PICKABLE_DROP_FILTER = (itemEntity) -> !itemEntity.cannotPickup() && itemEntity.isAlive();


    public boolean canStart() {
        if (!goose.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty()) {
            return false;
        } else if (goose.getTarget() == null && goose.getAttacker() == null) {
            if (!goose.wantsToPickupItem()) {
                return false;
            } else if (goose.getRandom().nextInt(10) != 0) {
                return false;
            } else {
                List<ItemEntity> list = goose.world.getEntitiesByClass(ItemEntity.class, goose.getBoundingBox().expand(8.0D, 8.0D, 8.0D), PICKABLE_DROP_FILTER);
                return !list.isEmpty() && goose.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty();
            }
        } else {
            return false;
        }
    }

    public void tick() {
        ItemStack itemStack = goose.getEquippedStack(EquipmentSlot.MAINHAND);
        if (itemStack.isEmpty()) {
            start();
        }
    }

    public void start() {
        List<ItemEntity> list = goose.world.getEntitiesByClass(ItemEntity.class, goose.getBoundingBox().expand(8.0D, 8.0D, 8.0D), PICKABLE_DROP_FILTER);
        if (!list.isEmpty()) {
            goose.getNavigation().startMovingTo(list.get(0), SPEED);
        }
    }
}
