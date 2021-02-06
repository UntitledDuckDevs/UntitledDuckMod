package net.untitledduckmod.registration;

import net.untitledduckmod.DuckEntity;
import net.untitledduckmod.DuckMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.untitledduckmod.items.DuckEggEntity;

import java.util.function.Supplier;

public class EntityTypeBuilders {
    private static EntityType<?> build(String id, EntityType.Builder<?> builder) {
        String prefixedId = DuckMod.MOD_ID + ":" + id;
        return builder.build(prefixedId);
    }

    public final static Supplier<EntityType<DuckEntity>> DUCK = () -> (EntityType<DuckEntity>) build("duck", EntityType.Builder.create(DuckEntity::new, SpawnGroup.MISC).setDimensions(0.6f, 0.6f));
    public final static Supplier<EntityType<DuckEggEntity>> DUCK_EGG = () -> (EntityType<DuckEggEntity>) build("duck_egg", EntityType.Builder.<DuckEggEntity>create(DuckEggEntity::new, SpawnGroup.MISC).setDimensions(0.25F, 0.25F).maxTrackingRange(4).trackingTickInterval(10));
}
