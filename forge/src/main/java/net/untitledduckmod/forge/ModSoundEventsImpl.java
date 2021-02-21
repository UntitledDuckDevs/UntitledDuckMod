package net.untitledduckmod.forge;

import net.minecraft.sound.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.untitledduckmod.registration.SoundIdentifier;


public class ModSoundEventsImpl {
    public static SoundEvent DUCK_AMBIENT = new SoundEvent(SoundIdentifier.DUCK_AMBIENT);
    public static SoundEvent DUCKLING_AMBIENT = new SoundEvent(SoundIdentifier.DUCKLING_AMBIENT);
    public static SoundEvent DUCK_STEP = new SoundEvent(SoundIdentifier.DUCK_STEP);
    public static SoundEvent DUCK_LAY_EGG = new SoundEvent(SoundIdentifier.DUCK_LAY_EGG);

    public static void register(Object optionalEvent) {
        assert optionalEvent != null;
        RegistryEvent.Register<SoundEvent> event = (RegistryEvent.Register<SoundEvent>) optionalEvent;
        IForgeRegistry<SoundEvent> registry = event.getRegistry();
        registry.register(DUCK_AMBIENT.setRegistryName(SoundIdentifier.DUCK_AMBIENT));
        registry.register(DUCKLING_AMBIENT.setRegistryName(SoundIdentifier.DUCKLING_AMBIENT));
        registry.register(DUCK_STEP.setRegistryName(SoundIdentifier.DUCK_STEP));
        registry.register(DUCK_LAY_EGG.setRegistryName(SoundIdentifier.DUCK_LAY_EGG));
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
