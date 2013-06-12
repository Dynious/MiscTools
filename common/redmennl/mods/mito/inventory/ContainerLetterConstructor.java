package redmennl.mods.mito.inventory;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import redmennl.mods.mito.tile.TileLetterConstructor;

public class ContainerLetterConstructor extends Container
{
    private final int PLAYER_INVENTORY_ROWS = 3;
    private final int PLAYER_INVENTORY_COLUMNS = 9;
	
	public ContainerLetterConstructor(InventoryPlayer i, TileLetterConstructor tile)
	{
		this.addSlotToContainer(new Slot(tile, 0, 21, 32));
		
        for (int inventoryRowIndex = 1; inventoryRowIndex < 5; ++inventoryRowIndex) {
            for (int inventoryColumnIndex = 1; inventoryColumnIndex < 7; ++inventoryColumnIndex) {
                this.addSlotToContainer(new Slot(tile, (inventoryColumnIndex + ((inventoryRowIndex - 1)*6)), 62 + (inventoryColumnIndex -1) * 18, 6 + (inventoryRowIndex -1) * 18));
            }
        }
		
        // Add the player's inventory slots to the container
        for (int inventoryRowIndex = 0; inventoryRowIndex < PLAYER_INVENTORY_ROWS; ++inventoryRowIndex) {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < PLAYER_INVENTORY_COLUMNS; ++inventoryColumnIndex) {
                this.addSlotToContainer(new Slot(i, inventoryColumnIndex + inventoryRowIndex * 9 + 9, 8 + inventoryColumnIndex * 18, 84 + inventoryRowIndex * 18));
            }
        }

        // Add the player's action bar slots to the container
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

            if (slotIndex < 25) {
                
                if (!this.mergeItemStack(slotItemStack, 1, inventorySlots.size(), true)) {
                    return null;
                }
            }
            else {
                if (!this.mergeItemStack(slotItemStack, 0, 1, false) || slotItemStack.getItem().itemID != Block.stone.blockID) {
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
