package net.untitledduckmod.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.untitledduckmod.DuckEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Add the duck to CHICKEN_AND_RABBIT_FILTER predicate and its duplicate in initGoals()
@SuppressWarnings("UnresolvedMixinReference")
@Mixin(FoxEntity.class)
public class FoxAttackMixin {
    // FoxEntity.CHICKEN_AND_RABBIT_FILTER predicate
    @Inject(method = "method_18267", at = @At("TAIL"), cancellable = true)
    private static void isTarget(Entity entity, CallbackInfoReturnable<Boolean> info) {
        if (!info.getReturnValueZ()) {
            info.setReturnValue(entity instanceof DuckEntity);
        }
    }

    // CHICKEN_AND_RABBIT_FILTER duplicate in initGoals()
    @Inject(method = "method_18262", at = @At("TAIL"), cancellable = true)
    private static void isTarget2(LivingEntity entity, CallbackInfoReturnable<Boolean> info) {
        if (!info.getReturnValueZ()) {
            info.setReturnValue(entity instanceof DuckEntity);
        }
    }
}
