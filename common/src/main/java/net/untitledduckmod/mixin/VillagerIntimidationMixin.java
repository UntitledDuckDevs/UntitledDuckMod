package net.untitledduckmod.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.untitledduckmod.ModStatusEffects;
import net.untitledduckmod.goose.GooseEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VillagerEntity.class)
public class VillagerIntimidationMixin extends PassiveEntity {
    protected VillagerIntimidationMixin(EntityType<? extends PassiveEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("TAIL"), method = "initGoals")
    private void addGoals(CallbackInfo info) {
        this.goalSelector.add(0, new FleeEntityGoal<>(this, LivingEntity.class, entity -> entity.hasStatusEffect(ModStatusEffects.getIntimidationEffect()), 12.0F, 1.0D, 1.1D, EntityPredicates.EXCEPT_SPECTATOR::test));
        this.goalSelector.add(0, new FleeEntityGoal<>(this, GooseEntity.class, 10.0F, 1.0D, 1.1D));
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }
}
