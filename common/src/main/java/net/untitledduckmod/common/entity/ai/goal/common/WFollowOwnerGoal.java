package net.untitledduckmod.common.entity.ai.goal.common;

import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.untitledduckmod.common.entity.WaterfowlEntity;

public class WFollowOwnerGoal extends FollowOwnerGoal {
    private final WaterfowlEntity entity;
    public WFollowOwnerGoal(WaterfowlEntity tameable, double speed, float minDistance, float maxDistance) {
        super(tameable, speed, minDistance, maxDistance);
        this.entity = tameable;
    }

    public boolean canStart() {
        return entity.tamedFollowOwner() && super.canStart();
    }

    public boolean shouldContinue() {
        return this.entity.tamedFollowOwner() && super.shouldContinue();
    }
}
