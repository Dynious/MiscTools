package redmennl.mods.mito.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import redmennl.mods.mito.block.BlockRegistery;
import redmennl.mods.mito.entity.companion.EntityCompanionDummy;
import redmennl.mods.mito.helper.Pos3;

public class TileCompanionCreatorBase extends TileEntity implements IInventory
{
    private ItemStack inventory[];
    private boolean isFormed = false;
    public Pos3 furthestBlock;
    private EntityCompanionDummy companionDummy;
    
    public float companionRotation = 0F;
    public float companionRotationSpeed = 0F;
    public float companionWalkState = 0F;
    
    public TileCompanionCreatorBase()
    {
        inventory = new ItemStack[4];
    }
    
    public EntityCompanionDummy getCompanionDummy()
    {
        return companionDummy;
    }
    
    public void createCompanionDummy()
    {
        if (companionDummy == null)
        {
            companionDummy = new EntityCompanionDummy(this.worldObj);
        }
    }
    
    public void deleteCompanionDummy()
    {
        if (companionDummy != null)
            companionDummy = null;
    }
    
    public void updateEntity()
    {
        if (companionDummy != null)
        {
            if (companionRotationSpeed >= 0.1F)
            {
                companionRotationSpeed -= 0.1F;
            } 
            else if (companionRotationSpeed <= -0.1F)
            {
                companionRotationSpeed += 0.1F;
            }
            else if (companionRotationSpeed == 0)
            {}
            else
            {
                companionRotationSpeed = 0;
            }
            companionRotation += companionRotationSpeed;
            companionWalkState += 0.5F;
            companionDummy.moveEntityWithHeading(0F, companionWalkState);
        }
    }
    
    public void checkIfFormed()
    {
        System.out.println("Checking!");
        if (isFormableBlock(1, 0, 0))
        {
            if (isFormableBlock(0, 1, 0))
            {
                if (isFormableBlock(0, 0, 1))
                {
                    if (checkIfBlocksPresent(1, 1, 1))
                    {
                        System.out.println("1, 1, 1");
                        furthestBlock = new Pos3(1, 1, 1);
                        formBlocks(1, 1, 1);
                        setIsFormed(true);
                        return;
                    }
                }
                else if (isFormableBlock(0, 0, -1))
                {
                    if (checkIfBlocksPresent(1, 1, -1))
                    {
                        furthestBlock = new Pos3(1, 1, -1);
                        formBlocks(1, 1, -1);
                        setIsFormed(true);
                        return;
                    }
                }
            }
            else if (isFormableBlock(0, -1, 0))
            {
                if (isFormableBlock(0, 0, 1))
                {
                    if (checkIfBlocksPresent(1, -1, 1))
                    {
                        furthestBlock = new Pos3(1, -1, 1);
                        formBlocks(1, -1, 1);
                        setIsFormed(true);
                        return;
                    }
                }
                else if (isFormableBlock(0, 0, -1))
                {
                    if (checkIfBlocksPresent(1, -1, -1))
                    {
                        furthestBlock = new Pos3(1, -1, -1);
                        formBlocks(1, -1, -1);
                        setIsFormed(true);
                        return;
                    }
                }
            }
        }
        else if (isFormableBlock(-1, 0, 0))
        {
            if (isFormableBlock(0, 1, 0))
            {
                if (isFormableBlock(0, 0, 1))
                {
                    if (checkIfBlocksPresent(-1, 1, 1))
                    {
                        furthestBlock = new Pos3(-1, 1, 1);
                        formBlocks(-1, 1, 1);
                        setIsFormed(true);
                        return;
                    }
                }
                else if (isFormableBlock(0, 0, -1))
                {
                    if (checkIfBlocksPresent(-1, 1, -1))
                    {
                        furthestBlock = new Pos3(-1, 1, -1);
                        formBlocks(-1, 1, -1);
                        setIsFormed(true);
                        return;
                    }
                }
            }
            else if (isFormableBlock(0, -1, 0))
            {
                if (isFormableBlock(0, 0, 1))
                {
                    if (checkIfBlocksPresent(-1, -1, 1))
                    {
                        furthestBlock = new Pos3(-1, -1, 1);
                        formBlocks(-1, -1, 1);
                        setIsFormed(true);
                        return;
                    }
                }
                else if (isFormableBlock(0, 0, -1))
                {
                    if (checkIfBlocksPresent(-1, -1,-1))
                    {
                        furthestBlock = new Pos3(-1, -1, -1);
                        formBlocks(-1, -1, -1);
                        setIsFormed(true);
                        return;
                    }
                }
            }
        }
    }
    
    private void formBlocks(int x, int y, int z)
    {
        for (int i = 0; i < 2; i++)
        {
            int xPos = xCoord;
            if (i == 1)
                xPos += x;
            for (int j = 0; j < 2; j++)
            {
                int yPos = yCoord;
                if (j == 1)
                    yPos += y;
                for (int k = 0; k < 2; k++)
                {
                    int zPos = zCoord;
                    if (k == 1)
                        zPos += z;
                    
                    if (i == 0 && j == 0 && k == 0)
                        continue;
                    ((TileCompanionCreator)this.worldObj.getBlockTileEntity(xPos, yPos, zPos)).setTileBasePos(new Pos3(xPos - xCoord, yPos - yCoord, zPos - zCoord));
                    this.worldObj.setBlockMetadataWithNotify(xPos, yPos, zPos, 2, 2);
                }
            }
        }
    }
    
    public void deformBlocks()
    {
        for (int i = 0; i < 2; i++)
        {
            int xPos = xCoord;
            if (i == 1)
                xPos += furthestBlock.x;
            for (int j = 0; j < 2; j++)
            {
                int yPos = yCoord;
                if (j == 1)
                    yPos += furthestBlock.y;
                for (int k = 0; k < 2; k++)
                {
                    int zPos = zCoord;
                    if (k == 1)
                        zPos += furthestBlock.z;
                    
                    if (i == 0 && j == 0 && k == 0)
                        continue;
                    ((TileCompanionCreator)this.worldObj.getBlockTileEntity(xPos, yPos, zPos)).setTileBasePos(null);
                    this.worldObj.setBlockMetadataWithNotify(xPos, yPos, zPos, 1, 2);
                }
            }
        }
    }
    
    private boolean checkIfBlocksPresent(int x, int y, int z)
    {
        for (int i = 0; i < 2; i++)
        {
            int xPos = xCoord;
            if (i == 1)
                xPos += x;
            for (int j = 0; j < 2; j++)
            {
                int yPos = yCoord;
                if (j == 1)
                    yPos += y;
                for (int k = 0; k < 2; k++)
                {
                    int zPos = zCoord;
                    if (k == 1)
                        zPos += z;
                    
                    if (i == 0 && j == 0 && k == 0)
                        continue;
                    System.out.println(xPos + ":" + yPos + ":" + zPos);
                    if (!(this.worldObj.getBlockId(xPos, yPos, zPos) == BlockRegistery.companionCreator.blockID && this.worldObj.getBlockMetadata(xPos, yPos, zPos) == 1))
                        return false;
                }
            }
        }
        
        
        
        return true;
    }

    private boolean isFormableBlock(int x, int y, int z)
    {
        System.out.println(x + ":" + y + ":" + z);
        return (this.worldObj.getBlockId(xCoord + x, yCoord + y, zCoord + z) == BlockRegistery.companionCreator.blockID && this.worldObj.getBlockMetadata(xCoord + x, yCoord + y, zCoord + z) == 1);
    }
    
    public boolean isFormed()
    {
        return isFormed;
    }
    
    public void setIsFormed(boolean isFormed)
    {
        this.isFormed = isFormed;
    }

    @Override
    public int getSizeInventory()
    {
        return inventory.length;
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
    public boolean isItemValidForSlot(int i, ItemStack itemstack)
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
        
        if (nbtTagCompound.hasKey("blockX"))
        {
            furthestBlock = new Pos3(nbtTagCompound.getInteger("blockX"), nbtTagCompound.getInteger("blockY"), nbtTagCompound.getInteger("blockZ"));
            setIsFormed(true);
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
        
        if (furthestBlock != null)
        {
            nbtTagCompound.setInteger("blockX", furthestBlock.x);
            nbtTagCompound.setInteger("blockY", furthestBlock.y);
            nbtTagCompound.setInteger("blockZ", furthestBlock.z);
        }
    }
}
