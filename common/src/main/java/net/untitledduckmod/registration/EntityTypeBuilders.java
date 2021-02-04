package net.untitledduckmod.registration;

import net.untitledduckmod.DuckEntity;
import net.untitledduckmod.DuckMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;

import java.util.function.Supplier;

public class EntityTypeBuilders {
    private static EntityType<?> build(String id, EntityType.Builder<?> builder) {
        String prefixedId = DuckMod.MOD_ID + ":" + id;
        return builder.build(prefixedId);
    }

    // TODO: Should use supplier like Forge RegistryObject?
    public final static Supplier<EntityType<?>> DUCK = () -> build("duck", EntityType.Builder.create(DuckEntity::new, SpawnGroup.MISC).setDimensions(0.6f, 0.6f));
}
