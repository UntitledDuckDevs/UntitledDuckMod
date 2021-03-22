package net.untitledduckmod.forge;

import net.minecraft.sound.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
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

    public static final SoundEvent GOOSE_HONK = new SoundEvent(SoundIdentifier.GOOSE_HONK);
    public static final SoundEvent GOOSE_LAY_EGG = new SoundEvent(SoundIdentifier.GOOSE_LAY_EGG);
    public static final SoundEvent GOOSE_DEATH = new SoundEvent(SoundIdentifier.GOOSE_DEATH);
    public static final SoundEvent GOSLING_AMBIENT = new SoundEvent(SoundIdentifier.GOSLING_AMBIENT);
    public static final SoundEvent GOSLING_DEATH = new SoundEvent(SoundIdentifier.GOSLING_DEATH);
    public static final SoundEvent GOSLING_HURT = new SoundEvent(SoundIdentifier.GOSLING_HURT);

    public static void register(Object optionalEvent) {
        assert optionalEvent != null;
        RegistryEvent.Register<SoundEvent> event = (RegistryEvent.Register<SoundEvent>) optionalEvent;
        IForgeRegistry<SoundEvent> registry = event.getRegistry();
        registry.register(DUCK_AMBIENT.setRegistryName(SoundIdentifier.DUCK_AMBIENT));
        registry.register(DUCKLING_AMBIENT.setRegistryName(SoundIdentifier.DUCKLING_AMBIENT));
        registry.register(DUCK_HURT.setRegistryName(SoundIdentifier.DUCK_HURT));
        registry.register(DUCKLING_HURT.setRegistryName(SoundIdentifier.DUCKLING_HURT));
        registry.register(DUCK_DEATH.setRegistryName(SoundIdentifier.DUCK_DEATH));
        registry.register(DUCKLING_DEATH.setRegistryName(SoundIdentifier.DUCKLING_DEATH));
        registry.register(DUCK_STEP.setRegistryName(SoundIdentifier.DUCK_STEP));
        registry.register(DUCK_LAY_EGG.setRegistryName(SoundIdentifier.DUCK_LAY_EGG));
        registry.register(DUCK_SACK_USE.setRegistryName(SoundIdentifier.DUCK_SACK_USE));

        registry.register(GOOSE_HONK.setRegistryName(SoundIdentifier.GOOSE_HONK));
        registry.register(GOOSE_LAY_EGG.setRegistryName(SoundIdentifier.GOOSE_LAY_EGG));
        registry.register(GOOSE_DEATH.setRegistryName(SoundIdentifier.GOOSE_DEATH));
        registry.register(GOSLING_AMBIENT.setRegistryName(SoundIdentifier.GOSLING_AMBIENT));
        registry.register(GOSLING_DEATH.setRegistryName(SoundIdentifier.GOSLING_DEATH));
        registry.register(GOSLING_HURT.setRegistryName(SoundIdentifier.GOSLING_HURT));
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
