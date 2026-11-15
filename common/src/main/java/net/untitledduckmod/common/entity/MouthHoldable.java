package net.untitledduckmod.common.entity;

import net.minecraft.inventory.SimpleInventory;
import net.minecraft.text.Text;

public interface MouthHoldable {
    SimpleInventory getMouthInventory();

    Text getDisplayName();

    int getMouthHolderType();
}
