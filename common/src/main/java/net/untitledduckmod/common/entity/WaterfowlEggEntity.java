package net.untitledduckmod.common.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.untitledduckmod.common.init.ModEntityTypes;
import net.untitledduckmod.common.init.ModItems;

public class WaterfowlEggEntity extends ThrownItemEntity {
    private final EntityType<? extends WaterfowlEntity> mobEntityType;

    public WaterfowlEggEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
        this.mobEntityType = ModEntityTypes.getDuck();
    }

    public WaterfowlEggEntity(EntityType<? extends ThrownItemEntity> entityType, World world, double x, double y, double z) {
        // Used for client side rendering, so mobEntityType doesn't matter
        super(entityType, x, y, z, world);
        this.mobEntityType = ModEntityTypes.getDuck();
    }

    public WaterfowlEggEntity(EntityType<? extends ThrownItemEntity> entityType, World world, LivingEntity owner, EntityType<? extends WaterfowlEntity> mobEntityType) {
        // This is the only constructor used on server side that matters
        super(entityType, owner, world);
        this.mobEntityType = mobEntityType;
    }

    public WaterfowlEggEntity(EntityType<? extends ThrownItemEntity> entityType, World world, double x, double y, double z, EntityType<? extends WaterfowlEntity> mobEntityType) {
        // Used for dispensing the item
        super(entityType, x, y, z, world);
        this.mobEntityType = mobEntityType;
    }

    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
        if (status == 3) {
            for (int i = 0; i < 8; ++i) {
                this.getWorld().addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, this.getStack()), this.getX(), this.getY(), this.getZ(), ((double) this.random.nextFloat() - 0.5D) * 0.08D, ((double) this.random.nextFloat() - 0.5D) * 0.08D, ((double) this.random.nextFloat() - 0.5D) * 0.08D);
            }
        }
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        entityHitResult.getEntity().damage(this.getDamageSources().thrown(this, this.getOwner()), 0.0F);
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        World world = this.getWorld();
        if (!world.isClient) {
            if (this.random.nextInt(8) == 0) {
                int i = 1;
                if (this.random.nextInt(32) == 0) {
                    i = 4;
                }

                for (int j = 0; j < i; ++j) {
                    WaterfowlEntity waterfowl = mobEntityType.create(this.getWorld());
                    if (waterfowl != null) {
                        waterfowl.setBreedingAge(-24000);
                        waterfowl.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), 0.0F);
                        waterfowl.setVariant((byte) this.getWorld().getRandom().nextInt(2)); // Randomly choose between the two variants
                        world.spawnEntity(waterfowl);
                    }
                }
            }

            world.sendEntityStatus(this, EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES);
            this.discard();
        }
    }

    protected Item getDefaultItem() {
        if (mobEntityType == ModEntityTypes.getDuck()) {
            return ModItems.DUCK_EGG.get();
        } else {
            return ModItems.GOOSE_EGG.get();
        }
    }
}
