package redmennl.mods.mito.tile;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import redmennl.mods.mito.lib.BlockIds;

public class TileLetterConstructor extends TileEntity implements IInventory
{

    private ItemStack inventory[];

    public TileLetterConstructor()
    {
        inventory = new ItemStack[27];
    }

    @Override
    public int getSizeInventory()
    {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int i)
    {
        if (inventory[0] != null && inventory[0].itemID == Block.stone.blockID)
        {
            inventory[1] = new ItemStack(BlockIds.LETTER, 1, 0);
            inventory[2] = new ItemStack(BlockIds.LETTER, 1, 1);
            inventory[3] = new ItemStack(BlockIds.LETTER, 1, 2);
            inventory[4] = new ItemStack(BlockIds.LETTER, 1, 3);
            inventory[5] = new ItemStack(BlockIds.LETTER, 1, 4);
            inventory[6] = new ItemStack(BlockIds.LETTER, 1, 5);
            inventory[7] = new ItemStack(BlockIds.LETTER, 1, 6);
            inventory[8] = new ItemStack(BlockIds.LETTER, 1, 7);
            inventory[9] = new ItemStack(BlockIds.LETTER, 1, 8);
            inventory[10] = new ItemStack(BlockIds.LETTER, 1, 9);
            inventory[11] = new ItemStack(BlockIds.LETTER, 1, 10);
            inventory[12] = new ItemStack(BlockIds.LETTER, 1, 11);
            inventory[13] = new ItemStack(BlockIds.LETTER, 1, 12);
            inventory[14] = new ItemStack(BlockIds.LETTER, 1, 13);
            inventory[15] = new ItemStack(BlockIds.LETTER, 1, 14);
            inventory[16] = new ItemStack(BlockIds.LETTER, 1, 15);
            inventory[17] = new ItemStack(BlockIds.LETTER2, 1, 0);
            inventory[18] = new ItemStack(BlockIds.LETTER2, 1, 1);
            inventory[19] = new ItemStack(BlockIds.LETTER2, 1, 2);
            inventory[20] = new ItemStack(BlockIds.LETTER2, 1, 3);
            inventory[21] = new ItemStack(BlockIds.LETTER2, 1, 4);
            inventory[22] = new ItemStack(BlockIds.LETTER2, 1, 5);
            inventory[23] = new ItemStack(BlockIds.LETTER2, 1, 6);
            inventory[24] = new ItemStack(BlockIds.LETTER2, 1, 7);
            inventory[25] = new ItemStack(BlockIds.LETTER2, 1, 8);
            inventory[26] = new ItemStack(BlockIds.LETTER2, 1, 9);
        } else
        {
            for (int y = 1; y < 27; y++)
            {
                inventory[y] = null;
            }
        }
        return inventory[i];
    }

    @Override
    public ItemStack decrStackSize(int i, int j)
    {
        ItemStack itemStack = getStackInSlot(i);
        ItemStack is = getStackInSlot(0);
        if (is != null)
        {
            if (is.stackSize <= j)
            {
                setInventorySlotContents(0, null);
            } else
            {
                is = is.splitStack(j);
                if (is.stackSize == 0)
                {
                    setInventorySlotContents(0, null);
                }
            }
            if (i != 0)
            {
                worldObj.playSoundEffect(xCoord + 0.5D, yCoord + 0.5D,
                        zCoord + 0.5D, "mito:makeLetter", 1.0F, 1.0F);
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
    public void setInventorySlotContents(int j, ItemStack i)
    {
        inventory[j] = i;
        if (i != null && i.stackSize > getInventoryStackLimit())
        {
            i.stackSize = getInventoryStackLimit();
        }
    }

    @Override
    public String getInvName()
    {
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
        return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this ? false
                : par1EntityPlayer.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D,
                        zCoord + 0.5D) <= 64.0D;
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
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {

        super.readFromNBT(nbtTagCompound);

        // Read in the ItemStacks in the inventory from NBT
        NBTTagList tagList = nbtTagCompound.getTagList("Items");
        inventory = new ItemStack[this.getSizeInventory()];
        for (int i = 0; i < tagList.tagCount(); ++i)
        {
            NBTTagCompound tagCompound = (NBTTagCompound) tagList.tagAt(i);
            byte slot = tagCompound.getByte("Slot");
            if (slot >= 0 && slot < inventory.length)
            {
                inventory[slot] = ItemStack.loadItemStackFromNBT(tagCompound);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {

        super.writeToNBT(nbtTagCompound);

        // Write the ItemStacks in the inventory to NBT
        NBTTagList tagList = new NBTTagList();
        for (int currentIndex = 0; currentIndex < inventory.length; ++currentIndex)
        {
            if (inventory[currentIndex] != null)
            {
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setByte("Slot", (byte) currentIndex);
                inventory[currentIndex].writeToNBT(tagCompound);
                tagList.appendTag(tagCompound);
            }
        }
        nbtTagCompound.setTag("Items", tagList);
    }

}
