package redmennl.mods.mito.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import redmennl.mods.mito.lib.Resources;
import redmennl.mods.mito.tile.TilePortableHouse;
import redmennl.mods.mito.tile.TilePortableHouseDeployer;
import cpw.mods.fml.common.FMLCommonHandler;

public class GuiPortableHouse extends GuiScreen
{
	
    private TilePortableHouse tile;
    private TilePortableHouseDeployer tile2;
    
    public GuiButton clearArea;
    public GuiTextField name;
    

	public GuiPortableHouse(EntityPlayer player, World world, TilePortableHouse tile, TilePortableHouseDeployer tile2)
	{
    	this.tile = tile;
    	this.tile2 = tile2;
	}


	@SuppressWarnings("unchecked")
	public void initGui()
    {
        buttonList.clear();
        
        buttonList.add(new GuiButton(9, width / 2 + 78, height / 2 - 83, 10, 10, "X"));
        
        if (tile != null)
        {
        	buttonList.add(new GuiButton(1, width / 2 - 73, height / 2 - 63, 20, 20, "+"));
        	buttonList.add(new GuiButton(2, width / 2 - 73, height / 2 - 28, 20, 20, "-"));
        	buttonList.add(new GuiButton(3, width / 2, height / 2 - 28, 80, 20, "Clear"));
        	buttonList.add(new GuiButton(4, width / 2, height / 2 - 53, 80, 20, "Start"));
        	buttonList.add(new GuiButton(5, width / 2 - 43, height / 2 - 63, 20, 20, "+"));
        	buttonList.add(new GuiButton(6, width / 2 - 43, height / 2 - 28, 20, 20, "-"));
        	name = new GuiTextField(fontRenderer, width / 2, height / 2 - 73, 80, 15);
        	name.setFocused(true);
        	name.setMaxStringLength(16);
        	
        }
        if (tile2 != null)
        {
        	buttonList.add(new GuiButton(7, width / 2 - 40, height / 2 - 70, 80, 20, "Deploy"));
        	buttonList.add(clearArea = new GuiButton(8, width / 2 - 40, height / 2 - 30, 80, 20, "Clear Off"));
        	clearArea.displayString = tile2.clearArea ? "Clear On" : "Clear Off";
        }
        super.initGui();
    }
	
	public void keyTyped(char c, int i)
	{
		super.keyTyped(c, i);
		if (tile != null)
		{
			name.textboxKeyTyped(c, i);
			tile.name = name.getText();
		}
	}
	
    public void drawScreen(int i, int j, float f)
    {
    	try {
    		drawDefaultBackground();
    		drawContainerBackground();
    		
    		if (tile != null)
    		{
    			name.drawTextBox();
    		}
    		
    		super.drawScreen(i, j, f);//buttons
        
    		if (tile != null)
    		{
    			String numberText = tile.size + "x" + tile.size;
    			fontRenderer.drawString(numberText, width / 2 - fontRenderer.getStringWidth(numberText) / 2 - 63, height / 2 - 39, 0xFFFFFF);
    			String hightText = "" + tile.hight;
    			fontRenderer.drawString(hightText, width / 2 - fontRenderer.getStringWidth(hightText) / 2 - 33, height / 2 - 39, 0xFFFFFF);
    			fontRenderer.drawString("Size", width / 2 - fontRenderer.getStringWidth(hightText) / 2 - 70, height / 2 - 75, 0xFFFFFF);
    			fontRenderer.drawString("Hight", width / 2 - fontRenderer.getStringWidth(hightText) / 2 - 40, height / 2 - 75, 0xFFFFFF);
    			
    		}
    		if (tile2 != null)
    		{
    			
    		}
    	}
    	
    	catch (Exception e)
    	{
    		System.out.print("GUI drawing failed!");
    	}
    }
    
    public void updateScreen()
    {
        super.updateScreen();
        if (tile != null)
        {
        	name.setText(tile.name);
        }
        if (tile2 != null)
        {
        	clearArea.displayString = tile2.clearArea ? "Clear On" : "Clear Off";
        }
        
    }
    
    /*
    int button;
    @Override
    protected void mouseClicked(int par1, int par2, int par3)
    {
        button = par3;
        if(par3 == 1)
            par3 = 0;
        super.mouseClicked(par1, par2, par3);
    }
    */
    
    protected void actionPerformed(GuiButton guibutton)
    {
    	if (tile != null)
    	{
    		if (tile.lasersCreated == false)
    		{
    			//tile.createLaserBox();
    		}
    	}
        if(guibutton.id == 1 && tile.size < 9)
        {
        	tile.size = tile.size + 2;
        	tile.updateLasers();
        }
        if(guibutton.id == 2 && tile.size > 1)
        {
        	tile.size = tile.size - 2;
        	tile.updateLasers();
        }
        if(guibutton.id == 3)
        {
        	tile.size = 1;
        	tile.hight = 1;
        	name.setText("");
        	tile.updateLasers();
        }
        if(guibutton.id == 4)
        {
        	FMLCommonHandler.instance().showGuiScreen(null);
        	tile.saveBlocks();
        }
        if(guibutton.id == 5 && tile.hight < 5)
        {
        	tile.hight++;
        	tile.updateLasers();
        }
        if(guibutton.id == 6 && tile.hight > 1)
        {
        	tile.hight--;
        	tile.updateLasers();
        }
        if(guibutton.id == 7)
        {
        	FMLCommonHandler.instance().showGuiScreen(null);
        	tile2.deploy();
        }
        if(guibutton.id == 8)
        {
        	if (tile2.clearArea)
        	{
        		tile2.clearArea = false;
        	}
        	else
        	{
        		tile2.clearArea = true;
        	}
        }
        
        if(guibutton.id == 9)
        {
        	FMLCommonHandler.instance().showGuiScreen(null);
        	
        	if (tile2 != null)
        	{
        		tile2.breakBlock();
        	}
        	if (tile != null)
        	{
        		tile.breakBlock();
        	}
        }
    }
    
    private void drawContainerBackground() {
        int xSize = 176;
        int ySize = 166;

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.func_110577_a(Resources.GUI_PORTABLEHOUSE);
        int xStart = (width - xSize) / 2;
        int yStart = (height - ySize) / 2;
        this.drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
    }
    
    public boolean doesGuiPauseGame()
    {
        return false;
    }
	
}
