package redmennl.mods.mito.client.renderer.living;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import redmennl.mods.mito.client.model.ModelCompanion;
import redmennl.mods.mito.entity.EntityCompanion;
import redmennl.mods.mito.lib.Resources;

public class RendererEntityCompanion extends RenderLiving
{

    protected ModelCompanion model;

    public RendererEntityCompanion(ModelBase model)
    {
        super(model, 0.3F);
        model = mainModel;
    }

    public void renderCompanion(EntityCompanion entity, double par2,
            double par4, double par6, float par8, float par9)
    {
        super.doRenderLiving(entity, par2, par4, par6, par8, par9);
    }

    @Override
    public void doRenderLiving(EntityLiving par1EntityLiving, double par2,
            double par4, double par6, float par8, float par9)
    {
        renderCompanion((EntityCompanion) par1EntityLiving, par2, par4, par6,
                par8, par9);
    }

    @Override
    public void doRender(Entity par1Entity, double par2, double par4,
            double par6, float par8, float par9)
    {
        renderCompanion((EntityCompanion) par1Entity, par2, par4, par6, par8,
                par9);
    }

    @Override
    protected ResourceLocation func_110775_a(Entity entity)
    {
        return Resources.MODEL_TEXTURE_COMPANION_BODY;
    }
}
