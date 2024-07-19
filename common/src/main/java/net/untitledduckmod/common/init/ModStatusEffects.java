package net.untitledduckmod.common.init;

import net.minecraft.entity.effect.StatusEffect;
import net.untitledduckmod.common.effect.IntimidationStatusEffect;
import net.untitledduckmod.common.platform.RegistryHelper;

import java.util.function.Supplier;

public class ModStatusEffects {
    public static final Supplier<StatusEffect> intimidation;

    static {
        intimidation = RegistryHelper.registerStatusEffect("intimidation", IntimidationStatusEffect::new);
    }

    // Call during mod initialization to ensure registration
    public static void init() {
    }
}

