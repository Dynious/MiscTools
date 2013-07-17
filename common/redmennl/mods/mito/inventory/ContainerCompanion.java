package redmennl.mods.mito.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import redmennl.mods.mito.entity.companion.EntityCompanion;
import redmennl.mods.mito.entity.companion.addon.AddonBase;
import redmennl.mods.mito.inventory.slots.AdvancedSlot;

public class ContainerCompanion extends ContainerPhantom
{
    private final int PLAYER_INVENTORY_ROWS = 3;
    private final int PLAYER_INVENTORY_COLUMNS = 9;

    EntityCompanion e;

    public ContainerCompanion(InventoryPlayer i, EntityCompanion e)
    {
        this.e = e;
        for (int inventoryRowIndex = 0; inventoryRowIndex < PLAYER_INVENTORY_ROWS; ++inventoryRowIndex)
        {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < PLAYER_INVENTORY_COLUMNS; ++inventoryColumnIndex)
            {
                this.addSlotToContainer(new AdvancedSlot(i,
                        inventoryColumnIndex + inventoryRowIndex * 9 + 9,
                        8 + inventoryColumnIndex * 18,
                        93 + inventoryRowIndex * 18));
            }
        }

        for (int actionBarSlotIndex = 0; actionBarSlotIndex < PLAYER_INVENTORY_COLUMNS; ++actionBarSlotIndex)
        {
            this.addSlotToContainer(new AdvancedSlot(i, actionBarSlotIndex,
                    8 + actionBarSlotIndex * 18, 151));
        }

        for (int inventoryRowIndex = 0; inventoryRowIndex < 3; ++inventoryRowIndex)
        {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < 3; ++inventoryColumnIndex)
            {
                this.addSlotToContainer(e.slotInventory[inventoryColumnIndex
                        + inventoryRowIndex * 3] = new AdvancedSlot(e,
                        inventoryColumnIndex + inventoryRowIndex * 3,
                        116 + inventoryColumnIndex * 18,
                        25 + inventoryRowIndex * 18));
            }
        }

        if (e.getAddons() != null)
        {
            for (AddonBase addon : e.getAddons())
            {
                if (addon.hasInventory())
                {
                    for (AdvancedSlot slot : addon.getSlots())
                    {
                        this.addSlotToContainer(slot);
                    }
                }
            }
        }

        // onCraftMatrixChanged(e.craftMatrix);
    }

    @Override
    public ItemStack slotClick(int slotNum, int mouseButton, int modifier,
            EntityPlayer player)
    {
        return super.slotClick(slotNum, mouseButton, modifier, player);
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer entityPlayer,
            int slotIndex)
    {

        ItemStack newItemStack = null;
        int size = e.getSizeInventory();
        Slot slot = (Slot) inventorySlots.get(slotIndex);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemStack = slot.getStack();
            newItemStack = itemStack.copy();

            if (slotIndex < size)
            {
                if (!this.mergeItemStack(itemStack, 36,
                        36 + e.slotInventory.length, false))
                    return null;
            } else if (!this.mergeItemStack(itemStack, 0, 36, true))
                return null;

            if (itemStack.stackSize == 0)
            {
                slot.putStack((ItemStack) null);
            } else
            {
                slot.onSlotChanged();
            }
        }

        return newItemStack;
    }
}
