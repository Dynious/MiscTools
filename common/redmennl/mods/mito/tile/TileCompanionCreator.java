package redmennl.mods.mito.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileCompanionCreator extends TileEntity implements IInventory
{
	private ItemStack inventory[];
	
	public TileCompanionCreator()
	{
		inventory = new ItemStack[4];
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
        ItemStack is = getStackInSlot(0);
        if (is != null) {
            if (is.stackSize <= j) {
                setInventorySlotContents(0, null);
            }
            else {
                is = is.splitStack(j);
                if (is.stackSize == 0) {
                    setInventorySlotContents(0, null);
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
		return "container.companionCreator";
	}

	@Override
	public boolean isInvNameLocalized()
	{
		return false;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 1;
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
		return true;
	}
	
    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {

        super.readFromNBT(nbtTagCompound);

        // Read in the ItemStacks in the inventory from NBT
        NBTTagList tagList = nbtTagCompound.getTagList("Items");
        inventory = new ItemStack[this.getSizeInventory()];
        for (int i = 0; i < tagList.tagCount(); ++i) {
            NBTTagCompound tagCompound = (NBTTagCompound) tagList.tagAt(i);
            byte slot = tagCompound.getByte("Slot");
            if (slot >= 0 && slot < inventory.length) {
                inventory[slot] = ItemStack.loadItemStackFromNBT(tagCompound);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {

        super.writeToNBT(nbtTagCompound);

        // Write the ItemStacks in the inventory to NBT
        NBTTagList tagList = new NBTTagList();
        for (int currentIndex = 0; currentIndex < inventory.length; ++currentIndex) {
            if (inventory[currentIndex] != null) {
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setByte("Slot", (byte) currentIndex);
                inventory[currentIndex].writeToNBT(tagCompound);
                tagList.appendTag(tagCompound);
            }
        }
        nbtTagCompound.setTag("Items", tagList);
    }
}
