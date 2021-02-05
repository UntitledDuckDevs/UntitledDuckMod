package net.untitledduckmod;

import me.shedaniel.architectury.annotations.ExpectPlatform;
import net.minecraft.item.Item;

public class ModItems {
    public static final int DUCK_PRIMARY_COLOR = 0xd0c0c0;
    public static final int DUCK_SECONDARY_COLOR = 0x17a300;

    @ExpectPlatform
    public static void register(Object optionalEvent) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void setup(Object optionalEvent) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Item getDuckSpawnEgg() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Item getDuckEgg() {
        throw new AssertionError();
    }
}
