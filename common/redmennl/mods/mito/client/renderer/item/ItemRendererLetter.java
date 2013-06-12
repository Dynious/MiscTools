package redmennl.mods.mito.client.renderer.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import redmennl.mods.mito.client.model.ModelLetter;
import redmennl.mods.mito.lib.BlockIds;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ItemRendererLetter implements IItemRenderer {

    private ModelLetter modelLetter;

    public ItemRendererLetter() {

        modelLetter = new ModelLetter();
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {

        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {

        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
    	
        switch (type) {
            case ENTITY: {
                renderLetter(0.0F, 0.0F, 0.0F, 1.0F, item, 0F);
                return;
            }
            case EQUIPPED: {
                renderLetter(1.0F, 0.0F, 0.5F, 1.0F, item, 0F);
                return;
            }
            case EQUIPPED_FIRST_PERSON: {
                renderLetter(0.5F, 0.2F, 0.5F, 0.8F, item, -110F);
                return;
            }
            case INVENTORY: {
                renderLetter(0.0F, -0.6F, 0.0F, 0.9F, item, 0F);
                return;
            }
            default:
                return;
        }
    }

    private void renderLetter(float x, float y, float z, float scale, ItemStack item, float r) {
    	
    	String letter = null;
    	
    	if (item.itemID == BlockIds.LETTER)
    	{
    		int meta = item.getItemDamage();
    		switch(meta)
    		{
    			case 0:
    				letter = "A";
    				break;
    			case 1:
    				letter = "B";
    				break;
    			case 2:
    				letter = "C";
    				break;
    			case 3:
    				letter = "D";
    				break;
    			case 4:
    				letter = "E";
    				break;
    			case 5:
    				letter = "F";
    				break;
    			case 6:
    				letter = "G";
    				break;
    			case 7:
    				letter = "H";
    				break;
    			case 8:
    				letter = "I";
    				break;
    			case 9:
    				letter = "J";
    				break;
    			case 10:
    				letter = "K";
    				break;
    			case 11:
    				letter = "L";
    				break;
    			case 12:
    				letter = "M";
    				break;
    			case 13:
    				letter = "N";
    				break;
    			case 14:
    				letter = "O";
    				break;
    			case 15:
    				letter = "P";
    				break;
                default:
                    break;
    		}
    	}
    	else if (item.itemID == BlockIds.LETTER2)
    	{
    		int meta = item.getItemDamage();
    		switch(meta)
    		{
    			case 0:
    				letter = "Q";
    				break;
    			case 1:
    				letter = "R";
    				break;
    			case 2:
    				letter = "S";
    				break;
    			case 3:
    				letter = "T";
    				break;
    			case 4:
    				letter = "U";
    				break;
    			case 5:
    				letter = "V";
    				break;
    			case 6:
    				letter = "W";
    				break;
    			case 7:
    				letter = "X";
    				break;
    			case 8:
    				letter = "Y";
    				break;
    			case 9:
    				letter = "Z";
    				break;
                default:
                    break;
    		}
    	}

        GL11.glPushMatrix();
        //GL11.glDisable(GL11.GL_LIGHTING);

        // Scale, Translate, Rotate
        GL11.glTranslatef(x, y, z);
        GL11.glScalef(scale, scale, scale);
        GL11.glRotatef(45F, 0F, 1F, 0F);
        GL11.glRotatef(r, 0F, 1F, 0F);

        // Bind texture
        FMLClientHandler.instance().getClient().renderEngine.bindTexture("/textures/blocks/cloth_0.png");

        // Render
        modelLetter.renderPart(letter);

        //GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }
}
