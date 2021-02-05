package net.untitledduckmod.forge;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeRegistry;
import net.minecraft.util.Identifier;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.untitledduckmod.DuckEntity;
import net.untitledduckmod.DuckMod;
import net.untitledduckmod.registration.EntityTypeBuilders;

public class EntityTypesImpl {
    public static final RegistryObject<EntityType<DuckEntity>> DUCK = RegistryObject.of(new Identifier(DuckMod.MOD_ID + ":" + "duck"), ForgeRegistries.ENTITIES);

    public static void register(Object optionalEvent) {
        assert optionalEvent != null;
        RegistryEvent.Register<EntityType<?>> event = (RegistryEvent.Register<EntityType<?>>) optionalEvent;
        EntityType<?> entityType = EntityTypeBuilders.DUCK.get();
        event.getRegistry().register(entityType.setRegistryName(DuckMod.MOD_ID, "duck"));
    }

    public static void registerAttributes() {
        DefaultAttributeRegistry.put(DUCK.get(), DuckEntity.getDefaultAttributes().build());
    }

    public static EntityType<DuckEntity> getDuck() {
        return DUCK.get();
    }
}
