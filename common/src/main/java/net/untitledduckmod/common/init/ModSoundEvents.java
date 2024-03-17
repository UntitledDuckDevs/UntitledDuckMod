package net.untitledduckmod.common.init;

import net.untitledduckmod.common.platform.RegistryHelper;
import net.minecraft.sound.SoundEvent;

import java.util.function.Supplier;

public class ModSoundEvents {
    public static final Supplier<SoundEvent> DUCK_AMBIENT = RegistryHelper.registerSoundEvent("duck_ambient");
    public static final Supplier<SoundEvent> DUCKLING_AMBIENT = RegistryHelper.registerSoundEvent("duckling_ambient");
    public static final Supplier<SoundEvent> DUCK_LAY_EGG = RegistryHelper.registerSoundEvent("duck_lay_egg");
    public static final Supplier<SoundEvent> DUCK_STEP = RegistryHelper.registerSoundEvent("duck_step");
    public static final Supplier<SoundEvent> DUCK_HURT = RegistryHelper.registerSoundEvent("duck_hurt");
    public static final Supplier<SoundEvent> DUCKLING_HURT = RegistryHelper.registerSoundEvent("duckling_hurt");
    public static final Supplier<SoundEvent> DUCK_DEATH = RegistryHelper.registerSoundEvent("duck_death");
    public static final Supplier<SoundEvent> DUCKLING_DEATH = RegistryHelper.registerSoundEvent("duckling_death");
    public static final Supplier<SoundEvent> DUCK_SACK_USE = RegistryHelper.registerSoundEvent("duck_sack_use");

    public static final Supplier<SoundEvent> GOOSE_HONK = RegistryHelper.registerSoundEvent("goose_honk");
    public static final Supplier<SoundEvent> GOOSE_LAY_EGG = RegistryHelper.registerSoundEvent("goose_lay_egg");
    public static final Supplier<SoundEvent> GOOSE_DEATH = RegistryHelper.registerSoundEvent("goose_death");
    public static final Supplier<SoundEvent> GOSLING_DEATH = RegistryHelper.registerSoundEvent("gosling_death");
    public static final Supplier<SoundEvent> GOSLING_AMBIENT = RegistryHelper.registerSoundEvent("gosling_ambient");
    public static final Supplier<SoundEvent> GOSLING_HURT = RegistryHelper.registerSoundEvent("gosling_hurt");

    // Call during mod initialization to ensure registration
    public static void init() {
    }


}