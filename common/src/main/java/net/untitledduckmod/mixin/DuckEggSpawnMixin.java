package net.untitledduckmod.mixin;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.untitledduckmod.EntityTypes;
import net.untitledduckmod.items.DuckEggEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class DuckEggSpawnMixin {
    @Shadow private ClientWorld world;

    @Inject(method="onEntitySpawn", at = @At("TAIL"))
    public void spawnDuckEgg(EntitySpawnS2CPacket packet, CallbackInfo info) {
        EntityType<?> entityType = packet.getEntityTypeId();
        if (entityType == EntityTypes.getDuckEgg()) {
            double x = packet.getX();
            double y = packet.getY();
            double z = packet.getZ();
            DuckEggEntity entity = new DuckEggEntity(this.world, x, y, z);
            entity.updateTrackedPosition(x, y, z);
            entity.refreshPositionAfterTeleport(x, y, z);
            entity.pitch = (float)(packet.getPitch() * 360) / 256.0F;
            entity.yaw = (float)(packet.getYaw() * 360) / 256.0F;
            int id = packet.getId();
            entity.setEntityId(id);
            entity.setUuid(packet.getUuid());
            this.world.addEntity(id, entity);
        }
    }
}
