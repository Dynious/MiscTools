package redmennl.mods.mito.client.renderer;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import redmennl.mods.mito.client.model.ModelCompanionCreator;
import redmennl.mods.mito.helper.Pos3;
import redmennl.mods.mito.lib.Resources;
import redmennl.mods.mito.tile.TileCompanionCreator;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RendererCompanionCreator extends TileEntitySpecialRenderer
{
    private ModelCompanionCreator modelCC = new ModelCompanionCreator();

    @Override
    public void renderTileEntityAt(TileEntity tileentity, double x, double y,
            double z, float tick)
    {
        TileCompanionCreator tile = (TileCompanionCreator)tileentity;
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 0.1F, (float) z + 0.1F);
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        
        FMLClientHandler.instance().getClient().renderEngine
        .func_110577_a(Resources.MODEL_TEXTURE_LETTER);
        
        Pos3 furthestBlock = tile.getFurthestBlock();
        if (furthestBlock != null)
        {
            System.out.println(furthestBlock.x + ":" + furthestBlock.y + ":" + furthestBlock.z);
            
            if (furthestBlock.x == 1 && furthestBlock.y == 1 && furthestBlock.z == 1)
            {
            }
            else if (furthestBlock.x == 1 && furthestBlock.y == 1 && furthestBlock.z == -1)
            {
                GL11.glTranslatef(-0.8F, 0.0F, 0.8F);
                GL11.glRotatef(90F, 0F, 1F, 0F);
            }
            else if (furthestBlock.x == 1 && furthestBlock.y == -1 && furthestBlock.z == 1)
            {
                GL11.glTranslatef(0.0F, 1.6F, 0.0F);
                GL11.glRotatef(90F, 1F, 0F, 0F);
            }
            else if (furthestBlock.x == 1 && furthestBlock.y == -1 && furthestBlock.z == -1)
            {
                GL11.glTranslatef(-0.8F, 1.6F, 0.8F);
                GL11.glRotatef(90F, 1F, 0F, 0F);
                GL11.glRotatef(-90F, 0F, 0F, 1F);
            }
            else if (furthestBlock.x == -1 && furthestBlock.y == 1 && furthestBlock.z == 1)
            {
                GL11.glTranslatef(0.8F, 0.0F, 0.8F);
                GL11.glRotatef(-90F, 0F, 1F, 0F);
            }
            else if (furthestBlock.x == -1 && furthestBlock.y == 1 && furthestBlock.z == -1)
            {
                GL11.glTranslatef(0.0F, 0.0F, 1.6F);
                GL11.glRotatef(180F, 0F, 1F, 0F);
            }
            else if (furthestBlock.x == -1 && furthestBlock.y == -1 && furthestBlock.z == 1)
            {
                GL11.glTranslatef(0.8F, 0.8F, 0.0F);
                GL11.glRotatef(90F, 1F, 0F, 0F);
                GL11.glRotatef(-90F, 0F, 1F, 0F);
            }
            else if (furthestBlock.x == -1 && furthestBlock.y == -1 && furthestBlock.z == -1)
            {
                GL11.glTranslatef(0.8F, 1.6F, 0.8F);
                GL11.glRotatef(180F, 1F, 0F, 0F);
                GL11.glRotatef(-90F, 0F, 1F, 0F);
            }
        }
        
        GL11.glRotatef(90F, 0F, 1F, 0F);
        modelCC.render1();
        GL11.glPopMatrix();
    }

}
