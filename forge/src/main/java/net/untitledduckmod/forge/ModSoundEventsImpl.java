package net.untitledduckmod.forge;

import net.minecraft.sound.SoundEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.untitledduckmod.registration.SoundIdentifier;


public class ModSoundEventsImpl {
    public static final SoundEvent DUCK_AMBIENT = SoundEvent.of(SoundIdentifier.DUCK_AMBIENT);
    public static final SoundEvent DUCKLING_AMBIENT = SoundEvent.of(SoundIdentifier.DUCKLING_AMBIENT);
    public static final SoundEvent DUCK_HURT = SoundEvent.of(SoundIdentifier.DUCK_HURT);
    public static final SoundEvent DUCKLING_HURT = SoundEvent.of(SoundIdentifier.DUCKLING_HURT);
    public static final SoundEvent DUCK_DEATH = SoundEvent.of(SoundIdentifier.DUCK_DEATH);
    public static final SoundEvent DUCKLING_DEATH = SoundEvent.of(SoundIdentifier.DUCKLING_DEATH);
    public static final SoundEvent DUCK_STEP = SoundEvent.of(SoundIdentifier.DUCK_STEP);
    public static final SoundEvent DUCK_LAY_EGG = SoundEvent.of(SoundIdentifier.DUCK_LAY_EGG);
    public static final SoundEvent DUCK_SACK_USE = SoundEvent.of(SoundIdentifier.DUCK_SACK_USE);

    public static final SoundEvent GOOSE_HONK = SoundEvent.of(SoundIdentifier.GOOSE_HONK);
    public static final SoundEvent GOOSE_LAY_EGG = SoundEvent.of(SoundIdentifier.GOOSE_LAY_EGG);
    public static final SoundEvent GOOSE_DEATH = SoundEvent.of(SoundIdentifier.GOOSE_DEATH);
    public static final SoundEvent GOSLING_AMBIENT = SoundEvent.of(SoundIdentifier.GOSLING_AMBIENT);
    public static final SoundEvent GOSLING_DEATH = SoundEvent.of(SoundIdentifier.GOSLING_DEATH);
    public static final SoundEvent GOSLING_HURT = SoundEvent.of(SoundIdentifier.GOSLING_HURT);

    public static void register(Object optionalEvent) {
        assert optionalEvent != null;
        RegisterEvent event = (RegisterEvent) optionalEvent;
        event.register(ForgeRegistries.Keys.SOUND_EVENTS, registry -> {
            registry.register(SoundIdentifier.DUCK_AMBIENT, DUCK_AMBIENT);
            registry.register(SoundIdentifier.DUCKLING_AMBIENT, DUCKLING_AMBIENT);
            registry.register(SoundIdentifier.DUCK_HURT, DUCK_HURT);
            registry.register(SoundIdentifier.DUCKLING_HURT, DUCKLING_HURT);
            registry.register(SoundIdentifier.DUCK_DEATH, DUCK_DEATH);
            registry.register(SoundIdentifier.DUCKLING_DEATH, DUCKLING_DEATH);
            registry.register(SoundIdentifier.DUCK_STEP, DUCK_STEP);
            registry.register(SoundIdentifier.DUCK_LAY_EGG, DUCK_LAY_EGG);
            registry.register(SoundIdentifier.DUCK_SACK_USE, DUCK_SACK_USE);

            registry.register(SoundIdentifier.GOOSE_HONK, GOOSE_HONK);
            registry.register(SoundIdentifier.GOOSE_LAY_EGG, GOOSE_LAY_EGG);
            registry.register(SoundIdentifier.GOOSE_DEATH, GOOSE_DEATH);
            registry.register(SoundIdentifier.GOSLING_AMBIENT, GOSLING_AMBIENT);
            registry.register(SoundIdentifier.GOSLING_DEATH, GOSLING_DEATH);
            registry.register(SoundIdentifier.GOSLING_HURT, GOSLING_HURT);
        });
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

    public static SoundEvent getGoslingAmbientSound() {
        return GOSLING_AMBIENT;
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

    public static SoundEvent getGoslingHurtSound() {
        return GOSLING_HURT;
    }
}
