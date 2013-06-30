package redmennl.mods.mito.inventory.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class AdvancedSlot extends Slot
{
	public boolean isVisible = true;

	public AdvancedSlot(IInventory par1iInventory, int par2, int par3, int par4)
	{
		super(par1iInventory, par2, par3, par4);
	}
}
