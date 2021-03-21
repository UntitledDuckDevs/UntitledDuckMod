package net.untitledduckmod.items;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.untitledduckmod.ModEntityTypes;
import net.untitledduckmod.ModItems;

public class DuckEggEntity extends ThrownItemEntity {
    private final EntityType<? extends PassiveEntity> mobEntityType;

    public DuckEggEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
        this.mobEntityType = ModEntityTypes.getDuck();
    }

    public DuckEggEntity(EntityType<? extends ThrownItemEntity> entityType, World world, LivingEntity owner, EntityType<? extends PassiveEntity> mobEntityType) {
        // This is the only constructor used on server side that matters
        super(entityType, owner, world);
        this.mobEntityType = mobEntityType;
    }

    public DuckEggEntity(EntityType<? extends ThrownItemEntity> entityType, World world, double x, double y, double z) {
        super(entityType, x, y, z, world);
        this.mobEntityType = ModEntityTypes.getDuck();
    }

    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
        if (status == 3) {
            for (int i = 0; i < 8; ++i) {
                this.world.addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, this.getStack()), this.getX(), this.getY(), this.getZ(), ((double) this.random.nextFloat() - 0.5D) * 0.08D, ((double) this.random.nextFloat() - 0.5D) * 0.08D, ((double) this.random.nextFloat() - 0.5D) * 0.08D);
            }
        }
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        entityHitResult.getEntity().damage(DamageSource.thrownProjectile(this, this.getOwner()), 0.0F);
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.world.isClient) {
            if (this.random.nextInt(8) == 0) {
                int i = 1;
                if (this.random.nextInt(32) == 0) {
                    i = 4;
                }

                for (int j = 0; j < i; ++j) {
                    PassiveEntity duckEntity = mobEntityType.create(this.world);
                    duckEntity.setBreedingAge(-24000);
                    duckEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.yaw, 0.0F);
                    this.world.spawnEntity(duckEntity);
                }
            }

            this.world.sendEntityStatus(this, (byte) 3);
            this.remove();
        }
    }

    protected Item getDefaultItem() {
        return ModItems.getDuckEgg();
    }
}
