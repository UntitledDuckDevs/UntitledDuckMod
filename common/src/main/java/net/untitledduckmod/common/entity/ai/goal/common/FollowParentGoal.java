package net.untitledduckmod.common.entity.ai.goal.common;

import net.untitledduckmod.common.entity.WaterfowlEntity;

public class FollowParentGoal extends net.minecraft.entity.ai.goal.FollowParentGoal {
    private final WaterfowlEntity entity;

    public FollowParentGoal(WaterfowlEntity entity, double speed) {
        super(entity, speed);
        this.entity = entity;
    }

    public final boolean cannotFollowOwner() {
        return entity.isSitting() || entity.hasVehicle() || entity.mightBeLeashed();
    }

    public boolean canStart() {
        return !cannotFollowOwner() && super.canStart();
    }

    public boolean shouldContinue() {
        return !this.cannotFollowOwner() && super.shouldContinue();
    }
}
