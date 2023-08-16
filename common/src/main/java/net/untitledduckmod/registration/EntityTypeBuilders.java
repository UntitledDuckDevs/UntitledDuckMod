package net.untitledduckmod.registration;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.untitledduckmod.DuckMod;
import net.untitledduckmod.duck.DuckEntity;
import net.untitledduckmod.goose.GooseEntity;
import net.untitledduckmod.items.DuckEggEntity;

import java.util.function.Supplier;

public class EntityTypeBuilders {
    public final static Supplier<EntityType<DuckEntity>> DUCK = () -> build("duck", EntityType.Builder.create(DuckEntity::new, SpawnGroup.CREATURE).setDimensions(0.6f, 0.6f).maxTrackingRange(10));
    public final static Supplier<EntityType<DuckEggEntity>> DUCK_EGG = () -> build("duck_egg", EntityType.Builder.<DuckEggEntity>create(DuckEggEntity::new, SpawnGroup.MISC).setDimensions(0.25F, 0.25F).maxTrackingRange(4).trackingTickInterval(10));
    public final static Supplier<EntityType<GooseEntity>> GOOSE = () -> build("goose", EntityType.Builder.create(GooseEntity::new, SpawnGroup.CREATURE).setDimensions(0.7f, 1.2f).maxTrackingRange(10));
    public final static Supplier<EntityType<DuckEggEntity>> GOOSE_EGG = () -> build("goose_egg", EntityType.Builder.<DuckEggEntity>create(DuckEggEntity::new, SpawnGroup.MISC).setDimensions(0.25F, 0.25F).maxTrackingRange(4).trackingTickInterval(10));

    public static <T extends Entity> EntityType<T> build(String id, EntityType.Builder<T> builder) {
        return builder.build(DuckMod.MOD_ID + ":" + id);
    }
}
