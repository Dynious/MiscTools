package redmennl.mods.mito.client.model;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import redmennl.mods.mito.item.ItemCompanion;
import cpw.mods.fml.client.FMLClientHandler;

public class ModelItemCompanion
{
    public float blockSizeOffset = 1.666F;

    public float modelOffsetY;
    public float armOffsetY;
    public boolean hasWheel = false;
    public float wheelOffsetY;
    public boolean hasLegs = false;
    public float legOffsetY;
    public float par2;
    public float par3;

    public ModelItemCompanion()
    {
    }

    public void render(ItemStack item)
    {
        ItemCompanion i = (ItemCompanion) item.getItem();
        IModelCustom model = i.model;
        GL11.glScalef(0.3F, 0.3F, 0.3F);
        GL11.glTranslatef(0F, -(modelOffsetY * blockSizeOffset) + 4.5F, 0F);
        GL11.glRotatef(180F, 1F, 0F, 0F);

        FMLClientHandler.instance().getClient().renderEngine
                .func_110577_a(i.textureBody);
        model.renderAllExcept("rightArm", "leftArm", "wheel", "rightLeg",
                "leftLeg");

        if (hasWheel)
        {
            GL11.glPushMatrix();
            FMLClientHandler.instance().getClient().renderEngine
                    .func_110577_a(i.textureWheel);
            model.renderPart("wheel");
            GL11.glPopMatrix();
        }

        if (hasLegs)
        {
            GL11.glPushMatrix();
            FMLClientHandler.instance().getClient().renderEngine
                    .func_110577_a(i.textureLegs);
            model.renderPart("leftLeg");
            GL11.glPopMatrix();

            GL11.glPushMatrix();
            FMLClientHandler.instance().getClient().renderEngine
                    .func_110577_a(i.textureLegs);
            model.renderPart("rightLeg");
            GL11.glPopMatrix();
        }

        GL11.glPushMatrix();
        FMLClientHandler.instance().getClient().renderEngine
                .func_110577_a(i.textureArms);
        model.renderPart("leftArm");
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        FMLClientHandler.instance().getClient().renderEngine
                .func_110577_a(i.textureArms);
        model.renderPart("rightArm");
        GL11.glPopMatrix();
    }
}
