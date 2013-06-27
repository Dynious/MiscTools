package redmennl.mods.mito.entity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import redmennl.mods.mito.MiscTools;
import redmennl.mods.mito.lib.GuiIds;
import redmennl.mods.mito.lib.Library;

public class EntityCompanion extends EntityTameable implements IInventory
{
	private ItemStack inventory[];
	public int health = 20;
	public String modelName = "companion";
	public IModelCustom model;
	public float modelOffsetY;
	public float armOffsetY;
	public boolean hasWheel = false;
	public float wheelOffsetY;
	public boolean hasLegs = false;
	public float legOffsetY;
	
	public EntityCompanion(World world)
	{
		super(world);
		inventory = new ItemStack[9];
		this.moveSpeed = 0.3F;
		this.setSize(0.8F, 1.0F);
		this.getNavigator().setAvoidsWater(true);
		readModelData(this.getClass().getResource(Library.MODEL_LOCATION + modelName + ".properties"));
		refreshModel();
		
		this.tasks.addTask(1, new EntityAIFollowOwner(this, this.moveSpeed, 10.0F, 2.0F));
	}
	
	public boolean interact(EntityPlayer ep)
    {
		if (!this.worldObj.isRemote)
        {
			try
			{
				ep.openGui(MiscTools.instance, GuiIds.COMPANION, worldObj, (int)this.posX, (int)this.posY, (int)this.posZ);
			}
			catch(Exception e)
			{
				System.out.println("Failed to open GUI");
			}
			return true;
        }
		return super.interact(ep);
    }
	
	public void readModelData(URL url)
	{
        BufferedReader reader = null;
        InputStream inputStream = null;

        String currentLine = null;
        
        try
        {
            inputStream = url.openStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            while ((currentLine = reader.readLine()) != null)
            {
                currentLine = currentLine.replaceAll("\\s+", " ").trim();
                
                if (currentLine.startsWith("#") || currentLine.startsWith("//") || currentLine.length() == 0)
                {
                    continue;
                }
                else if (currentLine.startsWith("modelOffsetY:"))
                {
                	modelOffsetY = Float.parseFloat(currentLine.substring(currentLine.indexOf(":") + 1));
                }
                else if (currentLine.startsWith("armOffsetY:"))
                {
                	armOffsetY = Float.parseFloat(currentLine.substring(currentLine.indexOf(":") + 1));
                }
                else if (currentLine.startsWith("hasWheel:"))
                {
                	if (currentLine.substring(currentLine.indexOf(":") + 1).startsWith("true"))
                	{
                		hasWheel = true;
                		wheelOffsetY = Float.parseFloat(currentLine.substring(currentLine.indexOf("true:") + 5));
                	}
                }
                else if (currentLine.startsWith("hasLegs:"))
                {
                	if (currentLine.substring(currentLine.indexOf(":") + 1).startsWith("true"))
                	{
                		hasWheel = true;
                		legOffsetY = Float.parseFloat(currentLine.substring(currentLine.indexOf("true:") + 5));
                	}
                }
            }
        }
        catch (Exception e)
        {
        	System.out.println("Oops error: " + e);
        }
        finally
        {
            try
            {
                reader.close();
            }
            catch (IOException e)
            {
            }

            try
            {
                inputStream.close();
            }
            catch (IOException e)
            {
            }
        }
	}
	
    public boolean isAIEnabled()
    {
        return true;
    }
    
    public void refreshModel()
    {
    	model = AdvancedModelLoader.loadModel(Library.MODEL_LOCATION + modelName + ".obj");
    }

	@Override
	public EntityAgeable createChild(EntityAgeable entityageable)
	{
		return null;
	}

	@Override
	public int getMaxHealth()
	{
		return 20;
	}
	
	public int getTalkInterval()
	{
		return 1200;
	}
	
    protected float getSoundPitch()
    {
		return 1F;
    	
    }
	
    protected String getHurtSound()
    {
        return Library.SOUND_COMPANION_HURT;
    }
    
    protected String getLivingSound()
    {
        return Library.SOUND_COMPANION_SAY;
    }
    
    protected String getDeathSound()
    {
        return Library.SOUND_COMPANION_DEATH;
    }
    
    protected void playStepSound(int par1, int par2, int par3, int par4)
    {
    	this.worldObj.playSoundAtEntity(this, Library.SOUND_COMPANION_WALK, 0.15F, 1.0F);
    }

	@Override
	public int getSizeInventory()
	{
		return this.inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		return inventory[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
        ItemStack itemStack = getStackInSlot(i);
        if (itemStack != null) 
        {
            if (itemStack.stackSize <= j) 
            {
                setInventorySlotContents(i, null);
            }
            else 
            {
                itemStack = itemStack.splitStack(j);
                if (itemStack.stackSize == 0) {
                    setInventorySlotContents(i, null);
                }
            }
        }

        return itemStack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i)
	{
        ItemStack itemStack = getStackInSlot(i);
        if (itemStack != null) 
        {
            setInventorySlotContents(i, null);
        }
        return itemStack;
    }

	@Override
	public void setInventorySlotContents(int i, ItemStack itemStack)
	{
        inventory[i] = itemStack;
        if (itemStack != null && itemStack.stackSize > getInventoryStackLimit()) 
        {
            itemStack.stackSize = getInventoryStackLimit();
        }
	}

	@Override
	public String getInvName()
	{
		return "container.companion";
	}

	@Override
	public boolean isInvNameLocalized()
	{
		return false;
	}

	@Override
	public int getInventoryStackLimit()
	{
        return 64;
	}

	@Override
	public void onInventoryChanged()
	{
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		return entityplayer.getDistanceSq(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D) <= 64.0D;
    }

	@Override
	public void openChest()
	{
	}

	@Override
	public void closeChest()
	{
	}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack)
	{
		return true;
	}
}
