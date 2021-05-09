package net.untitledduckmod.fabric;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.registry.Registry;
import net.untitledduckmod.registration.SoundIdentifier;


public class ModSoundEventsImpl {
    public static final SoundEvent DUCK_AMBIENT = new SoundEvent(SoundIdentifier.DUCK_AMBIENT);
    public static final SoundEvent DUCKLING_AMBIENT = new SoundEvent(SoundIdentifier.DUCKLING_AMBIENT);
    public static final SoundEvent DUCK_HURT = new SoundEvent(SoundIdentifier.DUCK_HURT);
    public static final SoundEvent DUCKLING_HURT = new SoundEvent(SoundIdentifier.DUCKLING_HURT);
    public static final SoundEvent DUCK_DEATH = new SoundEvent(SoundIdentifier.DUCK_DEATH);
    public static final SoundEvent DUCKLING_DEATH = new SoundEvent(SoundIdentifier.DUCKLING_DEATH);
    public static final SoundEvent DUCK_STEP = new SoundEvent(SoundIdentifier.DUCK_STEP);
    public static final SoundEvent DUCK_LAY_EGG = new SoundEvent(SoundIdentifier.DUCK_LAY_EGG);
    public static final SoundEvent DUCK_SACK_USE = new SoundEvent(SoundIdentifier.DUCK_SACK_USE);

    public static final SoundEvent GOOSE_LAY_EGG = new SoundEvent(SoundIdentifier.GOOSE_LAY_EGG);
    public static final SoundEvent GOOSE_HONK = new SoundEvent(SoundIdentifier.GOOSE_HONK);
    public static final SoundEvent GOOSE_DEATH = new SoundEvent(SoundIdentifier.GOOSE_DEATH);
    private static final SoundEvent GOSLING_AMBIENT = new SoundEvent(SoundIdentifier.GOSLING_AMBIENT);
    private static final SoundEvent GOSLING_DEATH = new SoundEvent(SoundIdentifier.GOSLING_DEATH);
    private static final SoundEvent GOSLING_HURT = new SoundEvent(SoundIdentifier.GOSLING_HURT);

    public static void register(Object optionalEvent) {
        Registry.register(Registry.SOUND_EVENT, SoundIdentifier.DUCK_AMBIENT, DUCK_AMBIENT);
        Registry.register(Registry.SOUND_EVENT, SoundIdentifier.DUCKLING_AMBIENT, DUCKLING_AMBIENT);
        Registry.register(Registry.SOUND_EVENT, SoundIdentifier.DUCK_HURT, DUCK_HURT);
        Registry.register(Registry.SOUND_EVENT, SoundIdentifier.DUCKLING_HURT, DUCKLING_HURT);
        Registry.register(Registry.SOUND_EVENT, SoundIdentifier.DUCK_STEP, DUCK_STEP);
        Registry.register(Registry.SOUND_EVENT, SoundIdentifier.DUCK_LAY_EGG, DUCK_LAY_EGG);
        Registry.register(Registry.SOUND_EVENT, SoundIdentifier.DUCK_SACK_USE, DUCK_SACK_USE);

        Registry.register(Registry.SOUND_EVENT, SoundIdentifier.GOOSE_LAY_EGG, GOOSE_LAY_EGG);
        Registry.register(Registry.SOUND_EVENT, SoundIdentifier.GOOSE_HONK, GOOSE_HONK);
        Registry.register(Registry.SOUND_EVENT, SoundIdentifier.GOOSE_DEATH, GOOSE_DEATH);
        Registry.register(Registry.SOUND_EVENT, SoundIdentifier.GOSLING_AMBIENT, GOSLING_AMBIENT);
        Registry.register(Registry.SOUND_EVENT, SoundIdentifier.GOSLING_DEATH, GOSLING_DEATH);
        Registry.register(Registry.SOUND_EVENT, SoundIdentifier.GOSLING_HURT, GOSLING_HURT);
    }

    public static SoundEvent getDuckAmbientSound() {
        return DUCK_AMBIENT;
    }

    public static SoundEvent getDucklingAmbientSound() {
        return DUCKLING_AMBIENT;
    }

    public static SoundEvent getDuckHurtSound() {
        return DUCK_HURT;
    }

    public static SoundEvent getDucklingHurtSound() {
        return DUCKLING_HURT;
    }

    public static SoundEvent getDuckDeathSound() {
        return DUCK_DEATH;
    }

    public static SoundEvent getDucklingDeathSound() {
        return DUCKLING_DEATH;
    }

    public static SoundEvent getDuckStepSound() {
        return DUCK_STEP;
    }

    public static SoundEvent getDuckEggSound() {
        return DUCK_LAY_EGG;
    }

    public static SoundEvent getGooseHonkSound() {
        return GOOSE_HONK;
    }

    public static SoundEvent getGooseEggSound() {
        return GOOSE_LAY_EGG;
    }

    public static SoundEvent getDuckSackUse() {
        return DUCK_SACK_USE;
    }

    public static SoundEvent getGooseDeathSound() {
        return GOOSE_DEATH;
    }

    public static SoundEvent getGoslingDeathSound() {
        return GOSLING_DEATH;
    }

    public static SoundEvent getGoslingAmbientSound() {
        return GOSLING_AMBIENT;
    }

    public static SoundEvent getGoslingHurtSound() {
        return GOSLING_HURT;
    }
}
