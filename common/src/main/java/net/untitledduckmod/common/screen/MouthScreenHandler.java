package net.untitledduckmod.common.screen;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.untitledduckmod.common.entity.DuckEntity;
import net.untitledduckmod.common.entity.WaterfowlEntity;
import net.untitledduckmod.common.init.ModItems;
import net.untitledduckmod.common.init.ModScreenHandlers;
import net.untitledduckmod.common.init.ModTags;

public class MouthScreenHandler extends ScreenHandler implements ExtendedFactory<MouthScreenHandler> {
    private final Inventory entityInventory;
    private final int entityId;
    private WaterfowlEntity entity;

    public MouthScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        this(syncId, inventory, new SimpleInventory(1), buf.readInt(), buf.readInt());
    }

    public MouthScreenHandler(int syncId, PlayerInventory playerInventory, Inventory entityInventory, int holderType, int entityId) {
        super(ModScreenHandlers.MOUTH.get(), syncId);
        checkSize(entityInventory, 1);
        this.entityInventory = entityInventory;
        this.entityId = entityId;
        Entity e = playerInventory.player.getWorld().getEntityById(entityId);
        if (e instanceof WaterfowlEntity waterfowl) {
            entity = waterfowl;
        }
        entityInventory.onOpen(playerInventory.player);

        this.addSlot(new Slot(entityInventory, 0, 8, 18) {
            @Override
            public boolean canInsert(ItemStack stack) {
                if (stack.isIn(ModTags.ItemTags.COOKED_MEAT) || stack.isIn(ModTags.ItemTags.RAW_MEAT)) return false;
                if (holderType == 1 && stack.isOf(ModItems.GOOSE_EGG.get())) return false;
                if (holderType == 2 && (stack.isOf(ModItems.DUCK_EGG.get()) || !DuckEntity.FOOD_INGREDIENT.test(stack))) return false;
                return super.canInsert(stack);
            }
            @Override
            public int getMaxItemCount() {
                return 1;
            }
        });

        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }
    }

    public int getEntityId() {
        return this.entityId;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.entityInventory.canPlayerUse(player) && this.entity.isAlive() && this.entity.distanceTo(player) < 8.0F;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();

            if (invSlot == 0) {
                if (!this.insertItem(itemStack2, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (this.getSlot(0).canInsert(itemStack2)) {
                    if (!this.insertItem(itemStack2, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    return ItemStack.EMPTY;
                }
            }

            if (itemStack2.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }
        return itemStack;
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.entityInventory.onClose(player);
    }

    @Override
    public MouthScreenHandler create(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        return new MouthScreenHandler(syncId, inventory, buf);
    }
}
