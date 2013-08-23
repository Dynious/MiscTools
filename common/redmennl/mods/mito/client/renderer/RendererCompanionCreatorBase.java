package redmennl.mods.mito.client.renderer;

import org.lwjgl.opengl.GL11;

import redmennl.mods.mito.client.model.ModelCompanionCreator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

@SideOnly(Side.CLIENT)
public class RendererCompanionCreatorBase extends TileEntitySpecialRenderer
{
    private ModelCompanionCreator modelCC = new ModelCompanionCreator();

    @Override
    public void renderTileEntityAt(TileEntity tileentity, double x, double y,
            double z, float tick)
    {
        GL11.glPushMatrix();
        
        GL11.glTranslatef((float) x + 0.5F, (float) y + 0.1F, (float) z + 0.1F);
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        GL11.glRotatef(90F, 0F, 1F, 0F);
        
        modelCC.render1();
        
        GL11.glPopMatrix();
    }

}
