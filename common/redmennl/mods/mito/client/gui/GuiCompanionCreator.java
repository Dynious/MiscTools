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
        xSize = 256;
        ySize = 198;
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
        GL11.glTranslatef(xStart + 45, yStart + 188, 50F);
        GL11.glScalef(45F, 45F, 45F);
        GL11.glRotatef(180F, 0F, 0F, 1F);
        GL11.glRotatef(20F, 0F, 1F, 0F);
        GL11.glRotatef(tile.companionRotation, 0F, 1F, 0F);
        RenderManager.instance.renderEntityWithPosYaw(tile.getCompanionDummy(), 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
        GL11.glPopMatrix();
    }
    
    /*
    @Override
    protected void mouseClickMove(int par1, int par2, int par3, long par4)
    {
        super.mouseClickMove(par1, par2, par3, par4);
        
        if (par4 >= 50 && par4 <= 60)
        {
            tile.companionRotationSpeed = (clickX - par1)/(par4/5);
        }
    }
    */
    
    @Override
    protected void mouseClicked(int par1, int par2, int par3)
    {
        super.mouseClicked(par1, par2, par3);
        
        int xStart = (width - xSize) / 2;
        int yStart = (height - ySize) / 2;
        
        if (par1 > xStart + 8 && par1 < xStart + 83 && par2 > yStart + 115 && par2 < yStart + 192)
        {
            clickX = par1;
        }
        else 
        {
            clickX = 300000;
        }
    }
    
    @Override
    protected void mouseMovedOrUp(int par1, int par2, int par3)
    {
        super.mouseMovedOrUp(par1, par2, par3);
         if (par3 == 0 && clickX != 300000)
         {
             tile.companionRotationSpeed = (clickX - par1) / 5;
         }
    }
    
    @Override
    public void onGuiClosed()
    {
        super.onGuiClosed();
        
        tile.deleteCompanionDummy();
    }
}
