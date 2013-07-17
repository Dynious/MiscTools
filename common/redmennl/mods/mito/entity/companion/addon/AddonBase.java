package redmennl.mods.mito.entity.companion.addon;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import redmennl.mods.mito.client.gui.GuiCompanion;
import redmennl.mods.mito.entity.companion.EntityCompanion;
import redmennl.mods.mito.entity.companion.addon.button.ButtonBase;
import redmennl.mods.mito.inventory.slots.AdvancedSlot;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class AddonBase
{
    private EntityCompanion companion;
    private int addonId;

    protected ItemStack[] inventory;
    protected ArrayList<AdvancedSlot> slots;
    protected ArrayList<ButtonBase> buttons;

    public AddonBase(EntityCompanion e, int addonId)
    {
        companion = e;
        this.addonId = addonId;
    }

    public EntityCompanion getCompanion()
    {
        return companion;
    }

    public int getAddonId()
    {
        return addonId;
    }

    public boolean hasGui()
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public void drawBackground(GuiCompanion gui, int xStart, int yStart)
    {

    }

    public boolean hasButtons()
    {
        return false;
    }

    public void buttonActions(int buttonid)
    {

    }

    public ArrayList<ButtonBase> getButtons()
    {
        return buttons;
    }

    public boolean hasInventory()
    {
        return false;
    }

    public int getSizeInventory()
    {
        if (!hasInventory())
            return 0;
        return inventory.length;
    }

    public ItemStack getStackInSlot(int i)
    {
        return inventory[i];
    }

    public void setInventorySlotContents(int i, ItemStack itemStack)
    {
        inventory[i] = itemStack;
        if (itemStack != null && itemStack.stackSize > getInventoryStackLimit())
        {
            itemStack.stackSize = getInventoryStackLimit();
        }
    }

    public int getInventoryStackLimit()
    {
        return 64;
    }

    public ArrayList<AdvancedSlot> getSlots()
    {
        return slots;
    }

    /*
     * Size the GUI needs 0 for none, 1 for full page, 2 for half page, 4 for
     * quarter page
     */
    public int guiSize()
    {
        return 0;
    }
}
