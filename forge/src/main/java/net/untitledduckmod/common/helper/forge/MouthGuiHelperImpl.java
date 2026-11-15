package net.untitledduckmod.common.helper.forge;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraftforge.network.NetworkHooks;
import net.untitledduckmod.common.entity.WaterfowlEntity;
import net.untitledduckmod.common.screen.MouthScreenHandler;

public class MouthGuiHelperImpl {
    public static void openMouthGui(PlayerEntity player, WaterfowlEntity entity) {
        if (player instanceof ServerPlayerEntity serverPlayer) {
            NetworkHooks.openScreen(serverPlayer, new NamedScreenHandlerFactory() {
                @Override
                public Text getDisplayName() {
                    return entity.getDisplayName();
                }

                @Override
                public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity p) {
                    return new MouthScreenHandler(syncId, inv, entity.getMouthInventory(), entity.getMouthHolderType(), entity.getId());
                }
            }, buf -> {
                buf.writeInt(entity.getMouthHolderType());
                buf.writeInt(entity.getId());
            });
        }
    }
}
