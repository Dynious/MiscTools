package redmennl.mods.mito.client.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import redmennl.mods.mito.lib.Resources;

@SideOnly(Side.CLIENT)
public class ModelCompanionCreator
{
    private IModelCustom modelCC1;
    private IModelCustom modelCC2;

    public ModelCompanionCreator()
    {

        modelCC1 = AdvancedModelLoader.loadModel(Resources.MODEL_COMAPNIONCREATOR1);
        modelCC2 = AdvancedModelLoader.loadModel(Resources.MODEL_COMAPNIONCREATOR2);
    }

    public void render1()
    {
        modelCC1.renderAll();
    }
    
    public void render2()
    {
        modelCC2.renderAll();
    }
}
