package net.untitledduckmod.common.init;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.entry.RegistryEntry;
import net.untitledduckmod.common.effect.IntimidationStatusEffect;
import net.untitledduckmod.common.platform.RegistryHelper;

public class ModStatusEffects {
    public static final RegistryEntry<StatusEffect> intimidation;

    static {
        intimidation = RegistryHelper.registerStatusEffect("intimidation", IntimidationStatusEffect::new);
    }

    // Call during mod initialization to ensure registration
    public static void init() {
    }
}

