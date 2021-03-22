package net.untitledduckmod.fabric.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.untitledduckmod.duck.DuckEntity;
import net.untitledduckmod.fabric.ModEntityTypesImpl;
import net.untitledduckmod.goose.GooseEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public abstract class SwimSpeedMixin extends Entity {
    public SwimSpeedMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @ModifyVariable(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getDepthStrider(Lnet/minecraft/entity/LivingEntity;)I", ordinal = 0), ordinal = 1)
    public float mixin(float speed) {
        if (getType() == ModEntityTypesImpl.DUCK) {
            return speed * DuckEntity.SWIM_SPEED_MULTIPLIER;
        }
        if (getType() == ModEntityTypesImpl.GOOSE) {
            return speed * GooseEntity.SWIM_SPEED_MULTIPLIER;
        }
        return speed;
    }
}
