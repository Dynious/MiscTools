package redmennl.mods.mito.tile;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class TileLetterConstructor extends TileEntity implements IInventory
{
	
	private ItemStack inventory[];
	
	public TileLetterConstructor()
	{
		inventory = new ItemStack[25];
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
        if (itemStack != null) {
            if (itemStack.stackSize <= j) {
                setInventorySlotContents(i, null);
            }
            else {
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
        if (itemStack != null) {
            setInventorySlotContents(i, null);
        }
        return itemStack;
	}

	@Override
	public void setInventorySlotContents(int j, ItemStack i)
	{
        inventory[j] = i;
        if (i != null && i.stackSize > getInventoryStackLimit()) {
            i.stackSize = getInventoryStackLimit();
        }
	}

	@Override
	public String getInvName()
	{
		// TODO Auto-generated method stub
		return "container.letterConstructor";
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
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
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
		if (i == 1 && itemstack.itemID == Block.stone.blockID) 
		{
			System.out.println("Yep");
			return true;
		}
		else 
		{
			System.out.println("Nope");
			return false;
		}
	}
	
}
