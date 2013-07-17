package redmennl.mods.mito.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import redmennl.mods.mito.entity.companion.EntityCompanion;

public class ContainerCompanionInventory extends Container
{
    public ContainerCompanionInventory(InventoryPlayer i, EntityCompanion e)
    {
        for (int inventoryRowIndex = 0; inventoryRowIndex < 3; ++inventoryRowIndex)
        {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < 3; ++inventoryColumnIndex)
            {
                this.addSlotToContainer(new Slot(e, inventoryColumnIndex
                        + inventoryRowIndex * 3,
                        116 + inventoryColumnIndex * 18,
                        25 + inventoryRowIndex * 18));
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return true;
    }
}
