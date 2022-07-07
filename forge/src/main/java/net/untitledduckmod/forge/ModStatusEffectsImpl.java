package net.untitledduckmod.forge;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.untitledduckmod.DuckMod;
import net.untitledduckmod.goose.IntimidationStatusEffect;

public class ModStatusEffectsImpl {
    private static final DeferredRegister<StatusEffect> STATUS_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, DuckMod.MOD_ID);
    public static final RegistryObject<StatusEffect> INTIMIDATION = STATUS_EFFECTS.register("intimidation", IntimidationStatusEffect::new);

    public static StatusEffect getIntimidationEffect() {
        return INTIMIDATION.get();
    }

    public static void register() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        STATUS_EFFECTS.register(bus);
    }
}
