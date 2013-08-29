package redmennl.mods.mito.entity.companion.addon;

import java.util.ArrayList;

import net.minecraft.client.gui.GuiButtonMerchant;
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
import redmennl.mods.mito.lib.Resources;
import redmennl.mods.mito.network.PacketHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class AddonCrafting extends AddonBase
{
    public ArrayList<LocalInventoryCrafting> craftMatrices = new ArrayList<LocalInventoryCrafting>();
    public ArrayList<IInventory> craftResult = new ArrayList<IInventory>();

    public ArrayList<PhantomSlot[]> slotCrafting = new ArrayList<PhantomSlot[]>();
    public ArrayList<SlotUntouchable> slotCraftingResult = new ArrayList<SlotUntouchable>();
    public boolean autoCraft = false;
    
    public int enabledTab = 0;
    public int maxTab = 0;
    
    public GuiButtonMerchant button1;
    public GuiButtonMerchant button2;

    public AddonCrafting(EntityCompanion e)
    {
        super(e);
        texture = Resources.GUI_COMPANION_CRAFTER;
        addSlots();
        initSlots();
        initButtons();
    }

    public void initButtons()
    {
        buttons = new ArrayList<ButtonBase>();
        
        buttons.add(new ButtonBase(-40, -70, false));
        buttons.add(new ButtonBase(40, -70, true));
        
        buttons.add(new ButtonBase(40, -65, 40, 20, "Craft"));
        buttons.add(new ButtonBase(40, -40, 40, 20, "Auto Craft"));
    }
    
    public void addSlots()
    {
        craftMatrices.add(new LocalInventoryCrafting(companion, 0));
        craftResult.add(new InventoryCraftResult());
        slotCrafting.add(new PhantomSlot[9]);
        slotCraftingResult.add(new SlotUntouchable(craftResult.get(0), 0, 98, 43));
        inventory = new ItemStack[10];
    }

    public void initSlots()
    {
        slots = new ArrayList<AdvancedSlot>();
        for (int y = 0; y < craftMatrices.size(); y++)
        {
            for (int inventoryRowIndex = 0; inventoryRowIndex < 3; ++inventoryRowIndex)
            {
                for (int inventoryColumnIndex = 0; inventoryColumnIndex < 3; ++inventoryColumnIndex)
                {
                    slots.add(slotCrafting.get(y)[inventoryColumnIndex + inventoryRowIndex
                            * 3] = new PhantomSlot(craftMatrices.get(y),
                            inventoryColumnIndex + inventoryRowIndex * 3,
                            8 + inventoryColumnIndex * 18,
                            25 + inventoryRowIndex * 18));
                    if (y != enabledTab)
                    {
                        slotCrafting.get(y)[inventoryColumnIndex + inventoryRowIndex
                                            * 3].setInvisible();
                    }
                }
            }
        
            slots.add(slotCraftingResult.get(y));
            if (y != enabledTab)
            {
                slotCraftingResult.get(y).setInvisible();
            }
        }
    }
    
    @Override
    public void setSlotsVisible()
    {
        for (AdvancedSlot slot : slotCrafting.get(enabledTab))
        {
            slot.setVisible();
        }
        slotCraftingResult.get(enabledTab).setVisible();
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
        gui.drawTexturedModalRect(xStart + 7, yStart + 24, 0, 0, 54, 54);
        gui.drawTexturedModalRect(xStart + 93, yStart + 38, 0, 54, 26, 26);
        gui.drawTexturedModalRect(xStart + 67, yStart + 43, 26, 54, 24, 15);
    }

    @Override
    public boolean hasInventory()
    {
        return true;
    }

    @Override
    public void onInventoryChanged()
    {
        if (autoCraft)
        {
            for (int y = 0; y < craftMatrices.size(); y++)
            {
                ItemStack[] result;
                while (canCraft((result = craftResult(y))))
                {
                    craft(result);
                }
            }
        }
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
            if (enabledTab != 0)
            {
                for (PhantomSlot slot : slotCrafting.get(enabledTab))
                {
                    slot.setInvisible();
                }
                slotCraftingResult.get(enabledTab).setInvisible();
                
                enabledTab--;
                
                for (PhantomSlot slot : slotCrafting.get(enabledTab))
                {
                    slot.setVisible();
                }
                slotCraftingResult.get(enabledTab).setVisible();
            }
        }
        if (buttonid == 1)
        {
            if (enabledTab != maxTab)
            {
                for (PhantomSlot slot : slotCrafting.get(enabledTab))
                {
                    slot.setInvisible();
                }
                slotCraftingResult.get(enabledTab).setInvisible();
                
                enabledTab++;
                
                for (PhantomSlot slot : slotCrafting.get(enabledTab))
                {
                    slot.setVisible();
                }
                slotCraftingResult.get(enabledTab).setVisible();
            }
        }
        
        if (buttonid == 2)
        {
            PacketHandler.companionCraft(getCompanion(), enabledTab);
        }
        if (buttonid == 3)
        {
            PacketHandler.companionAutoCraft(getCompanion());
        }
    }

    @Override
    public int guiSize()
    {
        return 1;
    }

    public ItemStack[] craftResult(int tab)
    {
        if (craftResult.get(tab).getStackInSlot(0) != null)
        {
            ItemStack craftedItems = CraftingManager.getInstance()
                    .findMatchingRecipe(craftMatrices.get(tab), getCompanion().worldObj);
            ItemStack[] finishedInventory = new ItemStack[9];
            for (int y = 0; y < getCompanion().slotInventory.length; ++y)
            {
                if (getCompanion().slotInventory[y].getStack() != null)
                {
                    finishedInventory[y] = getCompanion().slotInventory[y]
                            .getStack().copy();
                }
            }
            for (int i = 0; i < craftMatrices.get(tab).getSizeInventory(); ++i)
            {
                // System.out.println(i);
                ItemStack neededItemStack = null;
                
                if (craftMatrices.get(tab).getStackInSlot(i) != null)
                {
                    neededItemStack = craftMatrices.get(tab).getStackInSlot(i).copy();
                }
                if (neededItemStack != null)
                {
                    for (int y = 0; y < finishedInventory.length; ++y)
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
                {
                    System.out.println("craft failed");
                    return null;
                }
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
                        return finishedInventory;
                    }
                    
                    else if (finishedInventory[y].stackSize != finishedInventory[y]
                            .getMaxStackSize())
                    {
                        finishedInventory[y].stackSize = finishedInventory[y]
                                .getMaxStackSize();
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

    class LocalInventoryCrafting extends InventoryCrafting
    {
        
        EntityCompanion companion;
        
        public LocalInventoryCrafting(EntityCompanion e, final int tab)
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
                    craftResult.get(tab).setInventorySlotContents(
                            0,
                            CraftingManager.getInstance().findMatchingRecipe(
                                    craftMatrices.get(tab), getCompanion().worldObj));
                }
            }, 3, 3);
            this.companion = e;
        }
        
        @Override
        public void onInventoryChanged()
        {
            companion.onInventoryChanged();
        }
    }
}
