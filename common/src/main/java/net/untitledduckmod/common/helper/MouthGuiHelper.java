package net.untitledduckmod.common.helper;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.entity.player.PlayerEntity;
import net.untitledduckmod.common.entity.WaterfowlEntity;

public class MouthGuiHelper {
    @ExpectPlatform
    public static void openMouthGui(PlayerEntity player, WaterfowlEntity entity) {
        throw new AssertionError();
    }
}
