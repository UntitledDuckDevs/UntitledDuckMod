package net.untitledduckmod.common.entity.ai.goal.common;

import net.untitledduckmod.common.entity.WaterfowlEntity;

public class FollowParentGoal extends net.minecraft.entity.ai.goal.FollowParentGoal {
    private final WaterfowlEntity entity;

    public FollowParentGoal(WaterfowlEntity entity, double speed) {
        super(entity, speed);
        this.entity = entity;
    }

    private boolean cannotFollow() {
        return entity.isSitting() || entity.hasVehicle() || entity.isLeashed();
    }

    public boolean canStart() {
        return !cannotFollow() && super.canStart();
    }

    public boolean shouldContinue() {
        return !this.cannotFollow() && super.shouldContinue();
    }
}
