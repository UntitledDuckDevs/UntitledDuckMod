package net.untitledduckmod.fabric.registration;

import net.untitledduckmod.DuckMod;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.util.registry.Registry;
import net.untitledduckmod.DuckEntity;
import net.untitledduckmod.registration.EntityTypeBuilders;

public class EntityTypes {
    public static void init() {
        EntityType<?> entityType = EntityTypeBuilders.DUCK.get();
        DUCK = (EntityType<net.untitledduckmod.DuckEntity>) Registry.register(Registry.ENTITY_TYPE, DuckMod.MOD_ID + ":" + "duck", entityType);
        FabricDefaultAttributeRegistry.register(EntityTypes.DUCK, net.untitledduckmod.DuckEntity.getDefaultAttributes());
    }
    public static EntityType<net.untitledduckmod.DuckEntity> DUCK;

    public static EntityType<DuckEntity> getDuck() {
        return DUCK;
    }
}
