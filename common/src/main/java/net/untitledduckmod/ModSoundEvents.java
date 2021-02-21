package net.untitledduckmod;

import me.shedaniel.architectury.annotations.ExpectPlatform;
import net.minecraft.sound.SoundEvent;

public class ModSoundEvents {
    @ExpectPlatform
    public static void register(Object optionalEvent) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static SoundEvent getDuckAmbientSound() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static SoundEvent getDucklingAmbientSound() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static SoundEvent getDuckStepSound() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static SoundEvent getDuckEggSound() {
        throw new AssertionError();
    }
}