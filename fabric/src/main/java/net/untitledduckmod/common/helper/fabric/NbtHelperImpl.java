package net.untitledduckmod.common.helper.fabric;

import net.minecraft.component.ComponentType;
import net.minecraft.item.ItemStack;

public class NbtHelperImpl {

    public static boolean contains(ItemStack stack, ComponentType<?> type) {
        return stack.contains(type);
    }

}
