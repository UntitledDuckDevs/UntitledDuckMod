package net.untitledduckmod.common.entity.ai.goal.common;

import net.untitledduckmod.common.entity.WaterfowlEntity;

public class WFollowParentGoal extends net.minecraft.entity.ai.goal.FollowParentGoal {
    private final WaterfowlEntity entity;

    public WFollowParentGoal(WaterfowlEntity entity, double speed) {
        super(entity, speed);
        this.entity = entity;
    }

    public final boolean canFollow() {
        return !entity.isSitting() && !entity.hasVehicle() && !entity.mightBeLeashed();
    }

    public boolean canStart() {
        return canFollow() && super.canStart();
    }

    public boolean shouldContinue() {
        return this.canFollow() && super.shouldContinue();
    }
}
