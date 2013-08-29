package redmennl.mods.mito.inventory.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RestrictedSlot extends AdvancedSlot
{
    int id;

    public RestrictedSlot(IInventory par1iInventory, int par2, int par3,
            int par4, int id)
    {
        super(par1iInventory, par2, par3, par4);
        this.id = id;
    }

    @Override
    public boolean isItemValid(ItemStack i)
    {
        if (i.getItem() == Item.itemsList[id])
            return true;
        return false;
    }
}
