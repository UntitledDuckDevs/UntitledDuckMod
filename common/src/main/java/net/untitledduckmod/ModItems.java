package net.untitledduckmod;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.item.Item;

public class ModItems {
    public static final int DUCK_PRIMARY_COLOR = 0xd0c0c0;
    public static final int DUCK_SECONDARY_COLOR = 0x17a300;
    public static final int GOOSE_PRIMARY_COLOR = 0xd0c0c0;
    public static final int GOOSE_SECONDARY_COLOR = 0xffe100;

    @ExpectPlatform
    public static void register(Object optionalEvent) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void setupItemGroups(Object optionalEvent) {
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

    @ExpectPlatform
    public static Item getGooseSpawnEgg() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Item getGooseEgg() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Item getGooseFoot() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Item getDuckSack() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Item getEmptyDuckSack() {
        throw new AssertionError();
    }
}
