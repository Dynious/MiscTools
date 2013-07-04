package redmennl.mods.mito.inventory.slots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

public class PhantomSlot extends AdvancedSlot implements IPhantomSlot
{

    public PhantomSlot(IInventory par1iInventory, int par2, int par3, int par4)
    {
        super(par1iInventory, par2, par3, par4);
    }

    @Override
    public boolean canAdjust()
    {
        return true;
    }

    @Override
    public boolean canTakeStack(EntityPlayer par1EntityPlayer)
    {
        return false;
    }

}
