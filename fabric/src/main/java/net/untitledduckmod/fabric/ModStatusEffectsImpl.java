package net.untitledduckmod.fabric;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.untitledduckmod.DuckMod;
import net.untitledduckmod.goose.IntimidationStatusEffect;

public class ModStatusEffectsImpl {
    private static IntimidationStatusEffect intimidation;

    public static void register() {
        intimidation = Registry.register(Registry.STATUS_EFFECT, new Identifier(DuckMod.MOD_ID, "intimidation"), new IntimidationStatusEffect());
    }

    public static StatusEffect getIntimidationEffect() {
        return intimidation;
    }
}
