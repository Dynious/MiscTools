package redmennl.mods.mito.inventory;

import redmennl.mods.mito.entity.EntityCompanion;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCompanion extends Container
{
    private final int PLAYER_INVENTORY_ROWS = 3;
    private final int PLAYER_INVENTORY_COLUMNS = 9;
    
    public ContainerCompanion(InventoryPlayer i, EntityCompanion e)
	{
        for (int inventoryRowIndex = 0; inventoryRowIndex < 3; ++inventoryRowIndex) {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < 3; ++inventoryColumnIndex) {
                this.addSlotToContainer(new Slot(e, (inventoryColumnIndex + inventoryRowIndex * 3), 116 + inventoryColumnIndex * 18, 16 + inventoryRowIndex * 18));
            }
        }

        for (int inventoryRowIndex = 0; inventoryRowIndex < PLAYER_INVENTORY_ROWS; ++inventoryRowIndex) {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < PLAYER_INVENTORY_COLUMNS; ++inventoryColumnIndex) {
                this.addSlotToContainer(new Slot(i, inventoryColumnIndex + inventoryRowIndex * 9 + 9, 8 + inventoryColumnIndex * 18, 84 + inventoryRowIndex * 18));
            }
        }

        for (int actionBarSlotIndex = 0; actionBarSlotIndex < PLAYER_INVENTORY_COLUMNS; ++actionBarSlotIndex) {
            this.addSlotToContainer(new Slot(i, actionBarSlotIndex, 8 + actionBarSlotIndex * 18, 142));
        }
	}
    
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return true;
	}
	
	@Override
    public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slotIndex) {

		ItemStack itemStack = null;
        Slot slot = (Slot) inventorySlots.get(slotIndex);

        if (slot != null && slot.getHasStack())
        {
            ItemStack slotItemStack = slot.getStack();
            itemStack = slotItemStack.copy();
            
            //TODO Set correct number of slots
            if (slotIndex < inventorySlots.size()) {
                return null;
            }
            else {
            	if (!this.mergeItemStack(slotItemStack, 0, 1, false)) {
                    return null;
            	}
            }

            if (slotItemStack.stackSize == 0) {
                slot.putStack((ItemStack) null);
            }
            else {
                slot.onSlotChanged();
            }
        }

        return itemStack;
    }
}
