package redmennl.mods.mito.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.InventoryPlayer;

import org.lwjgl.opengl.GL11;

import redmennl.mods.mito.inventory.ContainerCompanionCreator;
import redmennl.mods.mito.lib.Resources;
import redmennl.mods.mito.tile.TileCompanionCreatorBase;

public class GuiCompanionCreator extends GuiContainer
{
    TileCompanionCreatorBase tile;
    
    private int clickX;
    

    public GuiCompanionCreator(InventoryPlayer i, TileCompanionCreatorBase tile)
    {
        super(new ContainerCompanionCreator(i, tile));
        xSize = 176;
        ySize = 166;
        this.tile = tile;
    }
    
    @Override
    public void drawScreen(int i, int j, float f)
    {
        super.drawScreen(i, j, f);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2,
            int var3)
    {

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.func_110577_a(Resources.GUI_COMPANION_CREATOR);
        int xStart = (width - xSize) / 2;
        int yStart = (height - ySize) / 2;
        this.drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
        
        RenderHelper.enableStandardItemLighting();
        tile.createCompanionDummy();
        GL11.glPushMatrix();
        GL11.glTranslatef(250F, 120F, 50F);
        GL11.glScalef(50F, 50F, 50F);
        GL11.glRotatef(180F, 0F, 0F, 1F);
        GL11.glRotatef(20F, 0F, 1F, 0F);
        GL11.glRotatef(tile.companionRotation, 0F, 1F, 0F);
        RenderManager.instance.renderEntityWithPosYaw(tile.getCompanionDummy(), 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
        GL11.glPopMatrix();
    }
    
    @Override
    protected void mouseClickMove(int par1, int par2, int par3, long par4)
    {
        super.mouseClickMove(par1, par2, par3, par4);
        
        if (par4 >= 50 && par4 <= 60)
        {
            tile.companionRotationSpeed = (clickX - par1)/(par4/5);
        }
    }
    
    @Override
    protected void mouseClicked(int par1, int par2, int par3)
    {
        super.mouseClicked(par1, par2, par3);
        clickX = par1;
    }
    
    @Override
    public void onGuiClosed()
    {
        super.onGuiClosed();
        
        tile.deleteCompanionDummy();
    }
}
