package redmennl.mods.mito.client.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import redmennl.mods.mito.lib.Library;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelCompanion extends ModelBase{

    private IModelCustom modelCompanion;

    public ModelCompanion() {

    	modelCompanion = AdvancedModelLoader.loadModel(Library.MODEL_COMPANION);
    }

    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
    	
    	super.render(par1Entity, par2, par3, par4, par5, par6, par7);
    	GL11.glTranslatef(0F, 0.9F, 0F);
    	GL11.glScalef(0.3F, 0.3F, 0.3F);
    	GL11.glRotatef(180F, 1F, 0F, 0F);
    	FMLClientHandler.instance().getClient().renderEngine.bindTexture(Library.MODEL_TEXTURE_COMPANION_BODY);
    	modelCompanion.renderPart("body");
    	FMLClientHandler.instance().getClient().renderEngine.bindTexture(Library.MODEL_TEXTURE_COMPANION_EYE);
    	modelCompanion.renderPart("eye");
    	FMLClientHandler.instance().getClient().renderEngine.bindTexture(Library.MODEL_TEXTURE_COMPANION_EYELID);
    	modelCompanion.renderPart("eyelid");
    	FMLClientHandler.instance().getClient().renderEngine.bindTexture(Library.MODEL_TEXTURE_COMPANION_WHEEL);
    	modelCompanion.renderPart("wheel");
    	FMLClientHandler.instance().getClient().renderEngine.bindTexture(Library.MODEL_TEXTURE_COMPANION_ARMS);
    	modelCompanion.renderPart("leftArm");
    	modelCompanion.renderPart("rightArm");
    }
}