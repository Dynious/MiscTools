package redmennl.mods.mito.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;

import org.lwjgl.opengl.GL11;

import redmennl.mods.mito.entity.EntityCompanion;
import redmennl.mods.mito.inventory.ContainerCompanion;
import redmennl.mods.mito.lib.Library;
import cpw.mods.fml.common.FMLCommonHandler;

public class GuiCompanion extends GuiAdvancedContainer
{
	EntityCompanion e;
	EntityPlayer p;
	
    int xStart;
    int yStart;
	
    public String activeTab;
    
	public GuiButton setOwner;
	public int tabs = 5;
	public int slotInventoryIndex0, slotInventoryIndex1, slotInventoryIndex2, slotInventoryIndex3, slotInventoryIndex4, slotInventoryIndex5, slotInventoryIndex6, slotInventoryIndex7, slotInventoryIndex8;
	public int slotCraftingIndex0, slotCraftingIndex1, slotCraftingIndex2, slotCraftingIndex3, slotCraftingIndex4, slotCraftingIndex5, slotCraftingIndex6, slotCraftingIndex7, slotCraftingIndex8;

	
    public GuiCompanion(InventoryPlayer i, EntityCompanion e, EntityPlayer player)
	{
		super(new ContainerCompanion(i,e));
		xSize = 176;
		ySize = 176;
		this.e = e;
		this.p = player;
		xStart = (width - xSize) / 2;
		yStart = (height - ySize) / 2;
	}
    
	@SuppressWarnings("unchecked")
	public void initGui()
    {
		clearContainerScreen();
		activeTab = "inventory";
		drawInventoryScreen();
		
        buttonList.clear();
        buttonList.add(new GuiButton(1, width / 2 - 70, height / 2 - 65, 80, 20, "Change model"));
        buttonList.add(setOwner = new GuiButton(2, width / 2 - 70, height / 2 - 40, 80, 20, "Follow"));
        if (e.isSitting() == true)
        {
        	setOwner.displayString = "Follow";
        }
        else if (e.isSitting() == false)
        {
        	setOwner.displayString = "Stop following";
        }
        
        super.initGui();
        
    }
	
	protected void mouseClicked(int par1, int par2, int par3)
    {
        if (par3 == 0)
        {
            int x = par1 - this.guiLeft;
            int y = par2 - this.guiTop;
            if (y <= 11)
            {
            	if (x >= 0 && x <= 29)
            	{
            		if (activeTab != "inventory")
            		{
	            		clearContainerScreen();
	            		drawInventoryScreen();
	            		activeTab = "inventory";
            		}
            	}
            	else if(x >= 30 && x <= 59 && tabs >= 2)
            	{
            		if (activeTab != "crafting")
            		{
	            		clearContainerScreen();
	            		drawCraftingScreen();
	            		activeTab = "crafting";
            		}
            	}
            	else if(x >= 60 && x <= 89 && tabs >= 3)
            	{
            		e.activeTab = 3;
            		buttonList.clear();
            		clearContainerScreen();
            	}
            	else if(x >= 90 && x <= 119 && tabs >= 4)
            	{
            		e.activeTab = 4;
            		buttonList.clear();
            		clearContainerScreen();
            	}
            	else if(x >= 120 && x <= 149 && tabs >= 5)
            	{
            		e.activeTab = 5;
            		buttonList.clear();
            		clearContainerScreen();
            	}
            }
        }
        
        super.mouseClicked(par1, par2, par3);
    }
    
    @SuppressWarnings("unchecked")
	public void drawInventoryScreen()
    {
		e.activeTab = 1;
        buttonList.add(new GuiButton(1, width / 2 - 70, height / 2 - 65, 80, 20, "Change model"));
        buttonList.add(setOwner = new GuiButton(2, width / 2 - 70, height / 2 - 40, 80, 20, "Follow"));
        if (e.isSitting() == true)
        {
        	setOwner.displayString = "Follow";
        }
        else if (e.isSitting() == false)
        {
        	setOwner.displayString = "Stop following";
        }
        addInventoryScreen();
    }
    
    public void drawCraftingScreen()
    {
		e.activeTab = 2;
		addCraftingScreen();
    }
    
	public void clearContainerScreen()
    {
		buttonList.clear();
		
    	for (int i = 0; i < this.e.slotInventory.length; i++)
    	{
    		this.e.slotInventory[i].isVisible = false;
    	}
        
    	for (int i = 0; i < this.e.slotCrafting.length; i++)
    	{
    		this.e.slotCrafting[i].isVisible = false;
    	}
		
		this.e.slotCraftingResult.isVisible = false;
    }
	
	public void addInventoryScreen()
    {
    	for (int i = 0; i < this.e.slotInventory.length; i++)
    	{
    		this.e.slotInventory[i].isVisible = true;
    	}
    }
    
	public void addCraftingScreen()
    {
    	for (int i = 0; i < this.e.slotCrafting.length; i++)
    	{
    		this.e.slotCrafting[i].isVisible = true;
    	}
		
		this.e.slotCraftingResult.isVisible = true;
    }
	
    public void updateScreen()
    {
        super.updateScreen();
        
        if (e.isSitting() == true)
        {
        	setOwner.displayString = "Follow";
        }
        else if (e.isSitting() == false)
        {
        	setOwner.displayString = "Stop following";
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
    		System.out.println(e.isSitting());
            if (e.isSitting() == true)
            {
            	System.out.println("setting false...");
            	e.setSitting(false);
            }
            if (e.isSitting() == false)
            {
            	e.setSitting(true);
            }
    	}
    }
	@Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(Library.GUI_COMPANION);
		xStart = (width - xSize) / 2;
		yStart = (height - ySize) / 2;
        this.drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
        
        this.drawTexturedModalRect(xStart, yStart, 176, 12, 39, 12);
        for (int i = 1; i < tabs; i++)
        {
        	this.drawTexturedModalRect(xStart + i*30, yStart, 176, 36, 39, 12);
        }
        this.drawTexturedModalRect(xStart + (tabs-1)*30, yStart, 176, 36, 39, 12);
        
	    if(e.activeTab == 1)
	    {
	    	this.drawTexturedModalRect(xStart, yStart, 176, 0, 39, 12);
	    }
	    else if(e.activeTab == tabs)
	    {
	    	this.drawTexturedModalRect(xStart + (tabs-1)*30, yStart, 176, 24, 39, 12);
	    }
	    else
	    {
	    	this.drawTexturedModalRect(xStart + (e.activeTab-1)*30, yStart, 176, 24, 39, 12);
	    }
	    if(activeTab == "inventory")
	    {
	        this.drawTexturedModalRect(xStart + 115, yStart + 24, 176, 48, 54, 54);
	    }
	    else if (activeTab == "crafting")
	    {
	        this.drawTexturedModalRect(xStart + 7, yStart + 24, 176, 48, 54, 54);
	    }
	}
}
