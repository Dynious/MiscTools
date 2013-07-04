package redmennl.mods.mito.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import redmennl.mods.mito.entity.EntityCompanion;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelCompanion extends ModelBase{
    
    public float blockSizeOffset = 1.666F;

    public ModelCompanion()
    {
    }
    
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) 
    {
    	EntityCompanion e = (EntityCompanion)par1Entity;
    	
    	super.render(par1Entity, par2, par3, par4, par5, par6, par7);
    	
    	GL11.glScalef(0.3F, 0.3F, 0.3F);
    	GL11.glTranslatef(0F, -(e.modelOffsetY * blockSizeOffset) + 4.5F, 0F);
    	GL11.glRotatef(180F, 1F, 0F, 0F);
    	
    	FMLClientHandler.instance().getClient().renderEngine.func_110577_a(e.textureBody);
    	
    	e.model.renderAllExcept("rightArm", "leftArm", "wheel", "rightLeg", "leftLeg");
    	
    	if (e.hasWheel)
    	{
	    	GL11.glPushMatrix();
	    	FMLClientHandler.instance().getClient().renderEngine.func_110577_a(e.textureWheel);
	    	
	    	GL11.glTranslatef(0F, blockSizeOffset * e.wheelOffsetY, 0F);
	    	GL11.glRotatef(par2 * 20F, 1.0F, 0.0F, 0.0F);
	    	GL11.glTranslatef(0F, blockSizeOffset * -e.wheelOffsetY, 0F);
	    	
	    	e.model.renderPart("wheel");
	    	GL11.glPopMatrix();
    	}
    	
    	if (e.hasLegs)
    	{
        	GL11.glPushMatrix();
        	FMLClientHandler.instance().getClient().renderEngine.func_110577_a(e.textureLegs);
        	
        	GL11.glTranslatef(0F, blockSizeOffset * e.legOffsetY, 0F);
        	float LeftArmAngleX = MathHelper.cos(par2 * 0.6662F + (float)Math.PI) * 1.4F * par3;
        	GL11.glRotatef(LeftArmAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
        	GL11.glTranslatef(0F, blockSizeOffset * -e.legOffsetY, 0F);

        	e.model.renderPart("leftLeg");
        	
        	GL11.glPopMatrix();
        	
        	GL11.glPushMatrix();
        	FMLClientHandler.instance().getClient().renderEngine.func_110577_a(e.textureLegs);
        	
        	GL11.glTranslatef(0F, blockSizeOffset * e.legOffsetY, 0F);
        	float RightArmAngleX = MathHelper.cos(par2 * 0.6662F) * 1.4F * par3;
        	GL11.glRotatef(RightArmAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
        	GL11.glTranslatef(0F, blockSizeOffset * -e.legOffsetY, 0F);

        	e.model.renderPart("rightLeg");
        	
        	GL11.glPopMatrix();
    	}
    	
    	GL11.glPushMatrix();
    	FMLClientHandler.instance().getClient().renderEngine.func_110577_a(e.textureArms);
    	
    	GL11.glTranslatef(0F, blockSizeOffset * e.armOffsetY, 0F);
    	float LeftArmAngleX = MathHelper.cos(par2 * 0.6662F) * 2.0F * par3;
    	GL11.glRotatef(LeftArmAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
    	GL11.glTranslatef(0F, blockSizeOffset * -e.armOffsetY, 0F);

    	e.model.renderPart("leftArm");
    	
    	GL11.glPopMatrix();
    	
    	GL11.glPushMatrix();
    	FMLClientHandler.instance().getClient().renderEngine.func_110577_a(e.textureArms);
    	
    	GL11.glTranslatef(0F, blockSizeOffset * e.armOffsetY, 0F);
    	float RightArmAngleX = MathHelper.cos(par2 * 0.6662F + (float)Math.PI) * 2.0F * par3;
    	GL11.glRotatef(RightArmAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
    	GL11.glTranslatef(0F, blockSizeOffset * -e.armOffsetY, 0F);

    	
    	e.model.renderPart("rightArm");
    	
    	GL11.glPopMatrix();
    }
}