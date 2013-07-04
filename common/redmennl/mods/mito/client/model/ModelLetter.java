package redmennl.mods.mito.client.model;

import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import redmennl.mods.mito.lib.Resources;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelLetter
{

    private IModelCustom modelLetter;

    public ModelLetter()
    {

        modelLetter = AdvancedModelLoader.loadModel(Resources.MODEL_LETTERS);
    }

    public void render()
    {

        modelLetter.renderAll();
    }

    public void renderPart(String partName)
    {

        modelLetter.renderPart(partName);
    }
}