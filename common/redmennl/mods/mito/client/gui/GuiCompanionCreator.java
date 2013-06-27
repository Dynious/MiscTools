package redmennl.mods.mito.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;

import org.lwjgl.opengl.GL11;

import redmennl.mods.mito.inventory.ContainerCompanionCreator;
import redmennl.mods.mito.lib.Library;
import redmennl.mods.mito.tile.TileCompanionCreator;

public class GuiCompanionCreator extends GuiContainer
{

    public GuiCompanionCreator(InventoryPlayer i, TileCompanionCreator tile)
	{
		super(new ContainerCompanionCreator(i, tile));
		xSize = 176;
		ySize = 166;
	}

	@Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(Library.GUI_LETTERCONSTRUCTOR);
        int xStart = (width - xSize) / 2;
        int yStart = (height - ySize) / 2;
        this.drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
    }
}
