package net.untitledduckmod.items;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.untitledduckmod.DuckEntity;
import net.untitledduckmod.ModEntityTypes;
import net.untitledduckmod.ModItems;

public class DuckEggEntity extends ThrownItemEntity {
    public DuckEggEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public DuckEggEntity(World world, LivingEntity owner) {
        super(ModEntityTypes.getDuckEgg(), owner, world);
    }

    public DuckEggEntity(World world, double x, double y, double z) {
        super(ModEntityTypes.getDuckEgg(), x, y, z, world);
    }

    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
        if (status == 3) {
            double d = 0.08D;
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
                    DuckEntity duckEntity = ModEntityTypes.getDuck().create(this.world);
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
