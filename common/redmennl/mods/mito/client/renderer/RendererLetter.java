package redmennl.mods.mito.client.renderer;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

import org.lwjgl.opengl.GL11;

import redmennl.mods.mito.client.model.ModelLetter;
import redmennl.mods.mito.tile.TileLetter;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RendererLetter extends TileEntitySpecialRenderer 
{
	private ModelLetter modelLetter = new ModelLetter();
	
	@Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick) {

        if (tileEntity instanceof TileLetter) {
            TileLetter tileLetter = (TileLetter) tileEntity;

            GL11.glPushMatrix();
            //GL11.glDisable(GL11.GL_LIGHTING);

            // Scale, Translate, Rotate
            if (tileLetter.orientation == ForgeDirection.SOUTH)
            {
            	GL11.glTranslatef((float) x + 0.5F, (float) y + 0.08F, (float) z + 0.25F);
            	GL11.glRotatef(180F, 0F, 1F, 0F);
            }
            
            else if (tileLetter.orientation == ForgeDirection.WEST)
            {
            	GL11.glTranslatef((float) x + 0.75F, (float) y + 0.08F, (float) z + 0.50F);
            	GL11.glRotatef(90F, 0F, 1F, 0F);
            }
            
            else if (tileLetter.orientation == ForgeDirection.NORTH)
            {
            	GL11.glTranslatef((float) x + 0.5F, (float) y + 0.08F, (float) z + 0.75F);
            }
            
            else if (tileLetter.orientation == ForgeDirection.EAST)
            {
            	GL11.glTranslatef((float) x + 0.25F, (float) y + 0.08F, (float) z + 0.50F);
            	GL11.glRotatef(-90F, 0F, 1F, 0F);
            }
            
            GL11.glScalef(0.7F, 0.70F, 2.5F);

            // Bind texture
            //TODO: Add texture
            FMLClientHandler.instance().getClient().renderEngine.bindTexture("/textures/blocks/cloth_" + tileLetter.color + ".png");
            
            modelLetter.renderPart(tileLetter.letter);

            //GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glPopMatrix();
        }
    }
}
