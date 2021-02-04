package net.untitledduckmod.forge;

import net.untitledduckmod.DuckMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeRegistry;
import net.minecraft.util.Identifier;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.untitledduckmod.DuckEntity;
import net.untitledduckmod.registration.EntityTypeBuilders;

@Mod(DuckMod.MOD_ID)
public class DuckModForge {
    public DuckModForge() {
        DuckMod.init();
    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class ModSetup {
        public static final RegistryObject<EntityType<net.untitledduckmod.DuckEntity>> DUCK = RegistryObject.of(new Identifier(DuckMod.MOD_ID + ":" + "duck"), ForgeRegistries.ENTITIES);
        @SubscribeEvent
        public static void entityRegistry(RegistryEvent.Register<EntityType<?>> event) {
            System.out.println("EntityType Registration");
            EntityType<?> entityType = EntityTypeBuilders.DUCK.get();
            event.getRegistry().register(entityType.setRegistryName(DuckMod.MOD_ID, "duck"));
        }

        @SubscribeEvent
        public static void commonSetup(FMLCommonSetupEvent event) {
            System.out.println("Common Setup");
            DefaultAttributeRegistry.put(DUCK.get(), DuckEntity.getDefaultAttributes().build());
        }
        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {
            System.out.println("Client Setup");
            RenderingRegistry.registerEntityRenderingHandler(DUCK.get(), DuckRenderer::new);
        }
    }
}
