package net.untitledduckmod.client.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.untitledduckmod.common.entity.WaterfowlEntity;
import net.untitledduckmod.common.screen.MouthScreenHandler;

public class MouthScreen extends HandledScreen<MouthScreenHandler> {
    private static final Identifier TEXTURE = new Identifier("textures/gui/container/horse.png");
    private WaterfowlEntity renderEntity;
    private float mouseX;
    private float mouseY;

    public MouthScreen(MouthScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        Entity entity = inventory.player.getEntityWorld().getEntityById(handler.getEntityId());
        if (entity instanceof WaterfowlEntity waterfowl) {
            renderEntity = waterfowl;
        }
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int x = (this.width - this.backgroundWidth) / 2;
        int y = (this.height - this.backgroundHeight) / 2;

        context.drawTexture(TEXTURE, x, y, 0, 0, this.backgroundWidth, this.backgroundHeight);

        context.drawTexture(TEXTURE, x + 7, y + 17, 0, this.backgroundHeight, 18, 18);

        if (this.renderEntity != null) {
            InventoryScreen.drawEntity(context, x + 51, y + 60, 30, (float)(x + 51) - this.mouseX, (float)(y + 75 - 50) - this.mouseY, this.renderEntity);
        }
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        this.mouseX = (float)mouseX;
        this.mouseY = (float)mouseY;
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
