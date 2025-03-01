package net.untitledduckmod.common.entity.ai.goal.common;

import net.untitledduckmod.common.entity.WaterfowlEntity;

public class WFollowOwnerGoal extends net.minecraft.entity.ai.goal.FollowOwnerGoal {
    private final WaterfowlEntity entity;
    public WFollowOwnerGoal(WaterfowlEntity tameable, double speed, float minDistance, float maxDistance) {
        super(tameable, speed, minDistance, maxDistance);
        this.entity = tameable;
    }

    public boolean canStart() {
        return !entity.tamedNotFollowOwner() && super.canStart();
    }

    public boolean shouldContinue() {
        return !this.entity.tamedNotFollowOwner() && super.shouldContinue();
    }

}
