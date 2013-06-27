package redmennl.mods.mito.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.FMLCommonHandler;

import redmennl.mods.mito.entity.EntityCompanion;
import redmennl.mods.mito.inventory.ContainerCompanion;
import redmennl.mods.mito.lib.Library;

public class GuiCompanion extends GuiContainer
{
	EntityCompanion e;
	EntityPlayer p;
	
	public GuiButton setOwner;

    public GuiCompanion(InventoryPlayer i, EntityCompanion e, EntityPlayer player)
	{
		super(new ContainerCompanion(i, e));
		xSize = 176;
		ySize = 166;
		this.e = e;
		this.p = player;
	}
    
	@SuppressWarnings("unchecked")
	public void initGui()
    {
        buttonList.clear();
        buttonList.add(new GuiButton(1, width / 2 - 70, height / 2 - 65, 80, 20, "Change model"));
        buttonList.add(setOwner = new GuiButton(2, width / 2 - 70, height / 2 - 40, 80, 20, "Set Owner"));
        if (e.getOwner() == null)
        {
        	setOwner.displayString = "Set Owner";
        }
        else if (e.getOwner() != null)
        {
        	setOwner.displayString = "Delete Owner";
        }
        super.initGui();
    }
	
    public void updateScreen()
    {
        super.updateScreen();
        
        if (e.getOwner() == null)
        {
        	setOwner.displayString = "Set Owner";
        }
        else if (e.getOwner() != null)
        {
        	setOwner.displayString = "Delete Owner";
        }
    }
	
    protected void actionPerformed(GuiButton guibutton)
    {
    	if (guibutton.id == 1)
    	{
    		FMLCommonHandler.instance().showGuiScreen(null);
    		
    		if (e.modelName == "companion")
    		{
    			e.modelName = "weird";
    			e.refreshModel();
    			e.readModelData(this.getClass().getResource(Library.MODEL_LOCATION + e.modelName + ".properties"));
    		}
    		else if (e.modelName == "weird")
    		{
    			e.modelName = "companion";
    			e.refreshModel();
    			e.readModelData(this.getClass().getResource(Library.MODEL_LOCATION + e.modelName + ".properties"));
    		}
    	}
    	if (guibutton.id == 2)
    	{
            if (e.getOwner() == null)
            {
            	e.setOwner(p.username);
            }
            else if (e.getOwner() != null)
            {
            	e.setOwner("");
            }
            System.out.println(e.getOwner());
    	}
    }
	@Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(Library.GUI_COMPANION);
        int xStart = (width - xSize) / 2;
        int yStart = (height - ySize) / 2;
        this.drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
    }
}
