package net.untitledduckmod.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.registry.Registries;
import net.minecraft.world.World;
import net.untitledduckmod.common.config.UntitledConfig;
import net.untitledduckmod.common.entity.GooseEntity;
import net.untitledduckmod.common.init.ModStatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IllagerEntity.class)
public class IllagerIntimidationMixin extends HostileEntity {
    protected IllagerIntimidationMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("TAIL"), method = "initGoals")
    private void addGoals(CallbackInfo info) {
        String entityID = Registries.ENTITY_TYPE.getId(this.getType()).toString();
        if (!UntitledConfig.intimidationBlacklist().contains(entityID)) {
            this.goalSelector.add(0, new FleeEntityGoal<>(this, LivingEntity.class, entity -> entity.hasStatusEffect(ModStatusEffects.intimidation), 12.0F, 1.0D, 1.1D, EntityPredicates.EXCEPT_SPECTATOR::test));
        }
        this.goalSelector.add(0, new FleeEntityGoal<>(this, GooseEntity.class, 10.0F, 1.0D, 1.1D));
    }
}
