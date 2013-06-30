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
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.world.World;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import redmennl.mods.mito.MiscTools;
import redmennl.mods.mito.inventory.slots.AdvancedSlot;
import redmennl.mods.mito.inventory.slots.PhantomSlot;
import redmennl.mods.mito.inventory.slots.SlotUntouchable;
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
	public int activeTab = 1;
	
	public InventoryCrafting craftMatrix = new LocalInventoryCrafting();
	public IInventory craftResult = new InventoryCraftResult();
	
    public AdvancedSlot slotInventory[] = new AdvancedSlot[9];
    public PhantomSlot slotCrafting[] = new PhantomSlot[9];
    public SlotUntouchable slotCraftingResult;
	
	private class LocalInventoryCrafting extends InventoryCrafting {

		public LocalInventoryCrafting() {
			super(new Container() {
				@Override
				public boolean canInteractWith(EntityPlayer entityplayer) {
					return false;
				}
			    
			    public void onCraftMatrixChanged(IInventory par1IInventory)
			    {
			    	craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(craftMatrix, worldObj));
			        System.out.println(canCraft());
			    }
			}, 3, 3);
		}
	}
	
	public EntityCompanion(World world)
	{
		super(world);
		inventory = new ItemStack[71];
		this.moveSpeed = 0.3F;
		this.setSize(0.8F, 1.0F);
		this.getNavigator().setAvoidsWater(true);
		readModelData(this.getClass().getResource(Library.MODEL_LOCATION + modelName + ".properties"));
		refreshModel();
		
        this.tasks.addTask(1, this.aiSit);
        this.tasks.addTask(2, new EntityAIFollowOwner(this, this.moveSpeed, 10.0F, 2.0F));
	}
	
	public boolean interact(EntityPlayer ep)
    {
		if (!this.worldObj.isRemote)
        {
			/*
			try
			{
				ep.openGui(MiscTools.instance, GuiIds.COMPANION, worldObj, (int)this.posX, (int)this.posY, (int)this.posZ);
			}
			catch(Exception e)
			{
				System.out.println("Failed to open GUI");
			}
			*/
			ep.openGui(MiscTools.instance, GuiIds.COMPANION, worldObj, (int)this.posX, (int)this.posY, (int)this.posZ);
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
        	System.out.println("Error when reading model data: " + e);
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
	
	public boolean canCraft()
    {
    	if (craftResult.getStackInSlot(0) != null)
    	{
    		AdvancedSlot tempInvItems[] = slotInventory.clone();
    		
    		//List neededItems = new ArrayList();
    		for (int i = 0; i < this.craftMatrix.getSizeInventory(); ++i)
            {
    			//System.out.println(i);
                ItemStack neededItemStack = this.craftMatrix.getStackInSlot(i);
                //neededItems.add(neededItemStack);
                if (neededItemStack != null)
                {
	                for (int y = 0; y < tempInvItems.length; ++y)
	                {
	                	ItemStack inventoryItemStack = tempInvItems[y].getStack();
	                	//System.out.println(inventoryItemStack);
	                	if (inventoryItemStack != null && neededItemStack != null && neededItemStack.getItem() == inventoryItemStack.getItem())
	                	{
	                		
	                		if(neededItemStack.stackSize < inventoryItemStack.stackSize)
	                		{
	                			inventoryItemStack.stackSize = inventoryItemStack.stackSize - neededItemStack.stackSize;
	                			tempInvItems[y].inventory.setInventorySlotContents(y, inventoryItemStack);
	                			neededItemStack = null;
	                			break;
	                		}
	                		else if(neededItemStack.stackSize == inventoryItemStack.stackSize)
	                		{
	                			neededItemStack = null;
	                			tempInvItems[y].inventory.setInventorySlotContents(y, null);
	                			break;
	                		}
	                		else
	                		{
	                			neededItemStack.stackSize = neededItemStack.stackSize - inventoryItemStack.stackSize;
	                			tempInvItems[y].inventory.setInventorySlotContents(y, null);
	                			tempInvItems[y].inventory.getStackInSlot(0);
	                		}
	                	}
	                }
                }
                if (neededItemStack != null)
                {
                	return false;
                }
            }
    		for (int y = 0; y < tempInvItems.length; ++y)
            {
    			System.out.println("y = " + y);
    			ItemStack inventoryItemStack = tempInvItems[y].getStack();
    			//System.out.println(inventoryItemStack.stackSize + " : " + slotCraftingResult.getStack().stackSize);
    			if (inventoryItemStack != null) 
    			{
    				System.out.println("StackSize = " + inventoryItemStack.stackSize);
	    			if (inventoryItemStack.getItem() == slotCraftingResult.getStack().getItem() && inventoryItemStack.stackSize + slotCraftingResult.getStack().stackSize <= 64)
	    			{
	    				System.out.println("Adding...");
	    				inventoryItemStack.stackSize = inventoryItemStack.stackSize + slotCraftingResult.getStack().stackSize;
	    				tempInvItems[y].inventory.setInventorySlotContents(y, inventoryItemStack);
	    				return true;
	    			}
	    			else
	    				System.out.println("Impossibru");
    			}
    			else
				{
					System.out.println("Stack is null");
    				tempInvItems[y].inventory.setInventorySlotContents(y, slotCraftingResult.inventory.getStackInSlot(0));
					return true;
				}
            }
    	}
    	return false;
    }
	
	@Override
    public void setSitting(boolean par1)
    {
		super.setSitting(par1);
		this.aiSit.setSitting(par1);
    }
	
	@Override
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
        canCraft();
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
