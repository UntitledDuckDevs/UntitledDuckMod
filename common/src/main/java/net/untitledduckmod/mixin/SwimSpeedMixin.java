package net.untitledduckmod.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.untitledduckmod.ModEntityTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public abstract class SwimSpeedMixin extends Entity {
    public SwimSpeedMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @ModifyVariable(method = "travel", at=@At(value="INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getDepthStrider(Lnet/minecraft/entity/LivingEntity;)I", ordinal = 0), ordinal = 1)
    public float mixin(float speed) {
        if (getType() == ModEntityTypes.getDuck()) {
            return speed * 3.0f;
        }
        return speed;
    }
}
