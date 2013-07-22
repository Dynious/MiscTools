package redmennl.mods.mito.helper;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ReportedException;

public class InventoryHelper
{
    /**
     * Returns the first item stack that is empty.
     */
    public static int getFirstEmptyStack(IInventory inventory)
    {
        for (int i = 0; i < inventory.getSizeInventory(); ++i)
        {
            System.out.println(i);
            if (inventory.getStackInSlot(i) == null)
                return i;
        }

        return -1;
    }

    /**
     * stores an itemstack in the users inventory
     */
    public static int storeItemStack(ItemStack par1ItemStack,
            IInventory inventory)
    {
        for (int i = 0; i < inventory.getSizeInventory(); ++i)
        {
            if (inventory.getStackInSlot(i) != null
                    && inventory.getStackInSlot(i).itemID == par1ItemStack.itemID
                    && inventory.getStackInSlot(i).isStackable()
                    && inventory.getStackInSlot(i).stackSize < inventory
                            .getStackInSlot(i).getMaxStackSize()
                    && inventory.getStackInSlot(i).stackSize < inventory
                            .getInventoryStackLimit()
                    && (!inventory.getStackInSlot(i).getHasSubtypes() || inventory
                            .getStackInSlot(i).getItemDamage() == par1ItemStack
                            .getItemDamage())
                    && ItemStack.areItemStackTagsEqual(
                            inventory.getStackInSlot(i), par1ItemStack))
                return i;
        }

        return -1;
    }

    /**
     * This function stores as many items of an ItemStack as possible in a
     * matching slot and returns the quantity of left over items.
     */
    public static int storePartialItemStack(ItemStack par1ItemStack,
            IInventory inventory)
    {
        int i = par1ItemStack.itemID;
        int j = par1ItemStack.stackSize;
        int k;

        if (par1ItemStack.getMaxStackSize() == 1)
        {
            k = getFirstEmptyStack(inventory);

            if (k < 0)
                return j;
            else
            {
                if (inventory.getStackInSlot(k) == null)
                {
                    inventory.setInventorySlotContents(k,
                            ItemStack.copyItemStack(par1ItemStack));
                }

                return 0;
            }
        } else
        {
            k = storeItemStack(par1ItemStack, inventory);

            if (k < 0)
            {
                k = getFirstEmptyStack(inventory);
            }

            if (k < 0)
                return j;
            else
            {
                if (inventory.getStackInSlot(k) == null)
                {
                    inventory.setInventorySlotContents(k, new ItemStack(i, 0,
                            par1ItemStack.getItemDamage()));

                    if (par1ItemStack.hasTagCompound())
                    {
                        inventory.getStackInSlot(k).setTagCompound(
                                (NBTTagCompound) par1ItemStack.getTagCompound()
                                        .copy());
                    }
                }

                int l = j;

                if (j > inventory.getStackInSlot(k).getMaxStackSize()
                        - inventory.getStackInSlot(k).stackSize)
                {
                    l = inventory.getStackInSlot(k).getMaxStackSize()
                            - inventory.getStackInSlot(k).stackSize;
                }

                if (l > inventory.getInventoryStackLimit()
                        - inventory.getStackInSlot(k).stackSize)
                {
                    l = inventory.getInventoryStackLimit()
                            - inventory.getStackInSlot(k).stackSize;
                }

                if (l == 0)
                    return j;
                else
                {
                    j -= l;
                    inventory.getStackInSlot(k).stackSize += l;
                    inventory.getStackInSlot(k).animationsToGo = 5;
                    return j;
                }
            }
        }
    }

    /**
     * Adds the item stack to the inventory, returns false if it is impossible.
     */
    public static boolean addItemStackToInventory(ItemStack par1ItemStack,
            IInventory inventory)
    {
        if (par1ItemStack == null)
            return false;
        else if (par1ItemStack.stackSize == 0)
            return false;
        else
        {
            try
            {
                int i;

                if (par1ItemStack.isItemDamaged())
                {
                    i = getFirstEmptyStack(inventory);

                    if (i >= 0)
                    {
                        inventory.setInventorySlotContents(i,
                                ItemStack.copyItemStack(par1ItemStack));
                        inventory.getStackInSlot(i).animationsToGo = 5;
                        par1ItemStack.stackSize = 0;
                        return true;
                    } else
                        return false;
                } else
                {
                    do
                    {
                        i = par1ItemStack.stackSize;
                        par1ItemStack.stackSize = storePartialItemStack(
                                par1ItemStack, inventory);
                    } while (par1ItemStack.stackSize > 0
                            && par1ItemStack.stackSize < i);

                    return par1ItemStack.stackSize < i;
                }
            } catch (Throwable throwable)
            {
                CrashReport crashreport = CrashReport.makeCrashReport(
                        throwable, "Adding item to inventory");
                CrashReportCategory crashreportcategory = crashreport
                        .makeCategory("Item being added");
                crashreportcategory.addCrashSection("Item ID",
                        Integer.valueOf(par1ItemStack.itemID));
                crashreportcategory.addCrashSection("Item data",
                        Integer.valueOf(par1ItemStack.getItemDamage()));
                throw new ReportedException(crashreport);
            }
        }
    }
}
