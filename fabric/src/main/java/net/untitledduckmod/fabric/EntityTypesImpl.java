package net.untitledduckmod.fabric;

import net.minecraft.util.Identifier;
import net.untitledduckmod.DuckMod;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.util.registry.Registry;
import net.untitledduckmod.DuckEntity;
import net.untitledduckmod.items.DuckEggEntity;
import net.untitledduckmod.registration.EntityTypeBuilders;

public class EntityTypesImpl {
    public static EntityType<DuckEntity> DUCK;
    public static EntityType<DuckEggEntity> DUCK_EGG;

    public static void register(Object optionalEvent) {
        DUCK = Registry.register(Registry.ENTITY_TYPE, new Identifier(DuckMod.MOD_ID, "duck"), EntityTypeBuilders.DUCK.get());
        DUCK_EGG = Registry.register(Registry.ENTITY_TYPE, new Identifier(DuckMod.MOD_ID, "duck_egg"), EntityTypeBuilders.DUCK_EGG.get());
    }

    public static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(EntityTypesImpl.DUCK, net.untitledduckmod.DuckEntity.getDefaultAttributes());
    }


    public static EntityType<DuckEntity> getDuck() {
        return DUCK;
    }
    public static EntityType<DuckEggEntity> getDuckEgg() {
        return DUCK_EGG;
    }
}
