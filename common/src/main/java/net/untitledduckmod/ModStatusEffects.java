package net.untitledduckmod;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.entity.effect.StatusEffect;

public class ModStatusEffects {
    @ExpectPlatform
    public static void register() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static StatusEffect getIntimidationEffect() {
        throw new AssertionError();
    }
}
