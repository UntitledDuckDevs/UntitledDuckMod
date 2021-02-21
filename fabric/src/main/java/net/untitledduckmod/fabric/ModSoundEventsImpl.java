package net.untitledduckmod.fabric;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.registry.Registry;
import net.untitledduckmod.registration.SoundIdentifier;


public class ModSoundEventsImpl {
    public static SoundEvent DUCK_AMBIENT = new SoundEvent(SoundIdentifier.DUCK_AMBIENT);
    public static SoundEvent DUCKLING_AMBIENT = new SoundEvent(SoundIdentifier.DUCKLING_AMBIENT);
    public static SoundEvent DUCK_STEP = new SoundEvent(SoundIdentifier.DUCK_STEP);
    public static SoundEvent DUCK_LAY_EGG = new SoundEvent(SoundIdentifier.DUCK_LAY_EGG);

    public static void register(Object optionalEvent) {
        Registry.register(Registry.SOUND_EVENT, SoundIdentifier.DUCK_AMBIENT, DUCK_AMBIENT);
        Registry.register(Registry.SOUND_EVENT, SoundIdentifier.DUCKLING_AMBIENT, DUCKLING_AMBIENT);
        Registry.register(Registry.SOUND_EVENT, SoundIdentifier.DUCK_STEP, DUCK_STEP);
        Registry.register(Registry.SOUND_EVENT, SoundIdentifier.DUCK_LAY_EGG, DUCK_LAY_EGG);
    }

    public static SoundEvent getDuckAmbientSound() {
        return DUCK_AMBIENT;
    }

    public static SoundEvent getDucklingAmbientSound() {
        return DUCKLING_AMBIENT;
    }

    public static SoundEvent getDuckStepSound() {
        return DUCK_STEP;
    }

    public static SoundEvent getDuckEggSound() {
        return DUCK_LAY_EGG;
    }
}
