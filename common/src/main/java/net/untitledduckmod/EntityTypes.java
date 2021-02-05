package net.untitledduckmod;

import me.shedaniel.architectury.annotations.ExpectPlatform;
import net.minecraft.entity.EntityType;

public class EntityTypes {
    @ExpectPlatform
    public static void register(Object optionalEvent) {
        throw new AssertionError();
    }
    @ExpectPlatform
    public static void registerAttributes() {
        throw new AssertionError();
    }
    @ExpectPlatform
    public static EntityType<DuckEntity> getDuck() {
        throw new AssertionError();
    }
}
