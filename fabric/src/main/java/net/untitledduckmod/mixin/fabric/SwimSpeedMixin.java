package net.untitledduckmod.mixin.fabric;

import net.untitledduckmod.common.entity.DuckEntity;
import net.untitledduckmod.common.entity.GooseEntity;
import net.untitledduckmod.common.init.ModEntityTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(LivingEntity.class)
public abstract class SwimSpeedMixin extends Entity {
    public SwimSpeedMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @ModifyArg(
            method = "travel",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;updateVelocity(FLnet/minecraft/util/math/Vec3d;)V", ordinal = 0),
            index = 0
    )
    public float mixin(float original) {
        if (getType() == ModEntityTypes.getDuck()) {
            return original * DuckEntity.SWIM_SPEED_MULTIPLIER;
        }
        if (getType() == ModEntityTypes.getGoose()) {
            return original * GooseEntity.SWIM_SPEED_MULTIPLIER;
        }
        return original;
    }
}
