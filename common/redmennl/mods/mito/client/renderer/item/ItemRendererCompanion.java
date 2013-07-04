package redmennl.mods.mito.client.renderer.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import redmennl.mods.mito.client.model.ModelItemCompanion;

public class ItemRendererCompanion implements IItemRenderer
{
    private ModelItemCompanion model;

    public ItemRendererCompanion()
    {
        model = new ModelItemCompanion();
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
            ItemRendererHelper helper)
    {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {

        switch (type)
        {
            case ENTITY:
            {
                renderCompanion(0.0F, 2.0F, 0.0F, 1.0F, item, 0F);
                return;
            }
            case EQUIPPED:
            {
                renderCompanion(1.0F, 2.0F, 0.5F, 1.0F, item, 0F);
                return;
            }
            case EQUIPPED_FIRST_PERSON:
            {
                renderCompanion(0.5F, 2.0F, 0.5F, 0.8F, item, -110F);
                return;
            }
            case INVENTORY:
            {
                renderCompanion(0.0F, 1.5F, 0.0F, 1.2F, item, 0F);
                return;
            }
            default:
                return;
        }
    }

    private void renderCompanion(float x, float y, float z, float scale,
            ItemStack item, float r)
    {
        GL11.glPushMatrix();
        // GL11.glDisable(GL11.GL_LIGHTING);

        // Scale, Translate, Rotate
        GL11.glTranslatef(x, y, z);
        GL11.glScalef(scale, scale, scale);
        GL11.glRotatef(r, 0F, 1F, 0F);
        // GL11.glRotatef(180F, 0F, 1F, 0F);
        GL11.glRotatef(180F, 1F, 0F, 0F);

        // Render
        model.render(item);

        // GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

}
