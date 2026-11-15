package net.untitledduckmod.common.init;

import net.minecraft.screen.ScreenHandlerType;
import net.untitledduckmod.common.platform.RegistryHelper;
import net.untitledduckmod.common.screen.MouthScreenHandler;

import java.util.function.Supplier;

public class ModScreenHandlers {
    public final static Supplier<ScreenHandlerType<MouthScreenHandler>> MOUTH;

    static {
        MOUTH = RegistryHelper.registerScreenHandler("mouth", () ->
                RegistryHelper.createExtendedScreenHandler(MouthScreenHandler::new)
        );
    }

    // Call during mod initialization to ensure registration
    public static void init() {
    }
}
