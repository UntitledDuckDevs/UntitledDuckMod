package net.untitledduckmod.common.screen;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;

public interface ExtendedFactory<T> {
    T create(int syncId, PlayerInventory inventory, PacketByteBuf buf);
}