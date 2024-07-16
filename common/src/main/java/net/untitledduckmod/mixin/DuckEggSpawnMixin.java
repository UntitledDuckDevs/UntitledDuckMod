package net.untitledduckmod.mixin;

import net.untitledduckmod.common.entity.WaterfowlEggEntity;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.untitledduckmod.common.init.ModEntityTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class DuckEggSpawnMixin {
    @Shadow
    private ClientWorld world;

    @Inject(method = "onEntitySpawn", at = @At("TAIL"))
    public void spawnDuckEgg(EntitySpawnS2CPacket packet, CallbackInfo info) {
        EntityType<? extends ThrownItemEntity> entityType = untitledduckmod$convert(packet.getEntityType());
        if (entityType != null) { // is duck/goose egg entity type
            double x = packet.getX();
            double y = packet.getY();
            double z = packet.getZ();
            WaterfowlEggEntity entity = new WaterfowlEggEntity(entityType, this.world, x, y, z);
            entity.updateTrackedPosition(x, y, z);
            entity.refreshPositionAfterTeleport(x, y, z);
            entity.setPitch((packet.getPitch() * 360) / 256.0F);
            entity.setYaw((packet.getYaw() * 360) / 256.0F);
            int id = packet.getId();
            entity.setId(id);
            entity.setUuid(packet.getUuid());
            this.world.addEntity(id, entity);
        }
    }

    @Unique
    private static EntityType<? extends ThrownItemEntity> untitledduckmod$convert(EntityType<?> entityType) {
        if (entityType == ModEntityTypes.getDuckEgg()) {
            return ModEntityTypes.getDuckEgg();
        } else if (entityType == ModEntityTypes.getGooseEgg()) {
            return ModEntityTypes.getGooseEgg();
        }
        return null;
    }
}
