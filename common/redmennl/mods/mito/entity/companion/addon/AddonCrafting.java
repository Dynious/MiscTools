package redmennl.mods.mito.entity.companion.addon;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import redmennl.mods.mito.client.gui.GuiCompanion;
import redmennl.mods.mito.entity.companion.EntityCompanion;
import redmennl.mods.mito.entity.companion.addon.button.ButtonBase;
import redmennl.mods.mito.inventory.slots.AdvancedSlot;
import redmennl.mods.mito.inventory.slots.PhantomSlot;
import redmennl.mods.mito.inventory.slots.SlotUntouchable;
import redmennl.mods.mito.network.PacketHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class AddonCrafting extends AddonBase
{
    public InventoryCrafting craftMatrix = new LocalInventoryCrafting();
    public IInventory craftResult = new InventoryCraftResult();

    public PhantomSlot slotCrafting[] = new PhantomSlot[9];
    public SlotUntouchable slotCraftingResult;

    public AddonCrafting(EntityCompanion e, int addonId)
    {
        super(e, addonId);
        inventory = new ItemStack[10];
        initSlots();
        initButtons();
    }

    private void initButtons()
    {
        buttons = new ArrayList<ButtonBase>();
        buttons.add(new ButtonBase(40, -65, 40, 20, "Craft"));
    }

    public void initSlots()
    {
        slots = new ArrayList<AdvancedSlot>();
        for (int inventoryRowIndex = 0; inventoryRowIndex < 3; ++inventoryRowIndex)
        {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < 3; ++inventoryColumnIndex)
            {
                slots.add(slotCrafting[inventoryColumnIndex + inventoryRowIndex
                        * 3] = new PhantomSlot(craftMatrix,
                        inventoryColumnIndex + inventoryRowIndex * 3,
                        8 + inventoryColumnIndex * 18,
                        25 + inventoryRowIndex * 18));
            }
        }
        slots.add(slotCraftingResult = new SlotUntouchable(craftResult, 0, 98,
                43));
    }

    @Override
    public boolean hasGui()
    {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawBackground(GuiCompanion gui, int xStart, int yStart)
    {
        gui.drawTexturedModalRect(xStart + 7, yStart + 24, 176, 48, 54, 54);
        gui.drawTexturedModalRect(xStart + 93, yStart + 38, 176, 102, 26, 26);
        gui.drawTexturedModalRect(xStart + 67, yStart + 43, 202, 102, 24, 15);
    }

    @Override
    public boolean hasInventory()
    {
        return true;
    }

    @Override
    public boolean hasButtons()
    {
        return true;
    }

    @Override
    public void buttonActions(int buttonid)
    {
        if (buttonid == 0)
        {
            PacketHandler.companionCraft(getCompanion());
        }
    }

    @Override
    public int guiSize()
    {
        return 1;
    }

    public ItemStack[] craftResult()
    {
        if (craftResult.getStackInSlot(0) != null)
        {
            ItemStack craftedItems = CraftingManager.getInstance()
                    .findMatchingRecipe(craftMatrix, getCompanion().worldObj);
            ItemStack[] finishedInventory = new ItemStack[9];
            for (int y = 0; y < getCompanion().slotInventory.length; ++y)
            {
                finishedInventory[y] = getCompanion().slotInventory[y]
                        .getStack();
            }
            for (int i = 0; i < craftMatrix.getSizeInventory(); ++i)
            {
                // System.out.println(i);
                ItemStack neededItemStack = craftMatrix.getStackInSlot(i);
                if (neededItemStack != null)
                {
                    for (int y = 0; y < getCompanion().slotInventory.length; ++y)
                    {
                        if (finishedInventory[y] != null
                                && neededItemStack.getItem() == finishedInventory[y]
                                        .getItem())
                        {

                            if (neededItemStack.stackSize < finishedInventory[y].stackSize)
                            {
                                finishedInventory[y].stackSize -= neededItemStack.stackSize;
                                neededItemStack = null;
                                break;
                            } else if (neededItemStack.stackSize == finishedInventory[y].stackSize)
                            {
                                neededItemStack = null;
                                finishedInventory[y] = null;
                                break;
                            } else
                            {
                                neededItemStack.stackSize -= finishedInventory[y].stackSize;
                                finishedInventory[y] = null;
                            }
                        }
                    }
                }
                if (neededItemStack != null)
                    return null;
            }
            for (int y = 0; y < finishedInventory.length; ++y)
            {
                if (finishedInventory[y] != null
                        && finishedInventory[y].getItem() == craftedItems
                                .getItem())
                {
                    System.out.println("Adding: "
                            + finishedInventory[y].stackSize + " and "
                            + craftedItems.stackSize);
                    if (finishedInventory[y].stackSize + craftedItems.stackSize <= finishedInventory[y]
                            .getMaxStackSize())
                    {
                        System.out.println("Fits!");
                        finishedInventory[y].stackSize += craftedItems.stackSize;
                        finishedInventory[y] = finishedInventory[y];
                        return finishedInventory;
                    } else if (finishedInventory[y].stackSize != finishedInventory[y]
                            .getMaxStackSize())
                    {
                        finishedInventory[y].stackSize = finishedInventory[y]
                                .getMaxStackSize();
                        finishedInventory[y] = finishedInventory[y];
                        craftedItems.stackSize -= finishedInventory[y]
                                .getMaxStackSize()
                                - finishedInventory[y].stackSize;
                        System.out.println("Didn't fit, adding "
                                + craftedItems.stackSize + " to next slot!");
                    }
                } else
                {
                    System.out.println("Stack is null OR Item != Same");
                }
            }
            for (int y = 0; y < finishedInventory.length; ++y)
            {
                if (finishedInventory[y] == null)
                {
                    System.out.println("Stack is null " + craftedItems);
                    finishedInventory[y] = craftedItems;
                    return finishedInventory;
                }
            }
        }
        return null;
    }

    public boolean canCraft(ItemStack[] finishedInventory)
    {
        if (finishedInventory != null)
            return true;
        else
            return false;
    }

    public void craft(ItemStack[] finishedInventory)
    {
        if (canCraft(finishedInventory))
        {
            for (int y = 0; y < finishedInventory.length; ++y)
            {
                getCompanion()
                        .setInventorySlotContents(y, finishedInventory[y]);
            }
        }
    }

    private class LocalInventoryCrafting extends InventoryCrafting
    {

        public LocalInventoryCrafting()
        {
            super(new Container()
            {
                @Override
                public boolean canInteractWith(EntityPlayer entityplayer)
                {
                    return false;
                }

                @Override
                public void onCraftMatrixChanged(IInventory par1IInventory)
                {
                    craftResult.setInventorySlotContents(
                            0,
                            CraftingManager.getInstance().findMatchingRecipe(
                                    craftMatrix, getCompanion().worldObj));
                }
            }, 3, 3);
        }
    }
}
