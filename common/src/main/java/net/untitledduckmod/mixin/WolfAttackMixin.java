package net.untitledduckmod.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.untitledduckmod.DuckEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(WolfEntity.class)
public class WolfAttackMixin {
    // method_18444 == WolfEntity.FOLLOW_TAMED_PREDICATE which determines which mob the wolf will attack
    @Inject(method = "method_18444(Lnet/minecraft/entity/LivingEntity;)Z", at = @At("TAIL"), cancellable = true)
    private static void isTarget(LivingEntity entity, CallbackInfoReturnable<Boolean> info) {
        if (!info.getReturnValueZ()) {
            info.setReturnValue(entity instanceof DuckEntity);
        }
    }
}
