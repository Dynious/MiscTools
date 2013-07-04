package redmennl.mods.mito.inventory.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class AdvancedSlot extends Slot
{
    boolean isVisible = true;
    private int xStandardPos;
    private int yStandardPos;

    public AdvancedSlot(IInventory par1iInventory, int par2, int par3, int par4)
    {
        super(par1iInventory, par2, par3, par4);
        xStandardPos = par3;
        yStandardPos = par4;
    }

    public void setInvisible()
    {
        isVisible = false;
        xDisplayPosition = 2000;
        yDisplayPosition = 2000;
    }

    public void setVisible()
    {
        isVisible = true;
        xDisplayPosition = xStandardPos;
        yDisplayPosition = yStandardPos;
    }

    public boolean isVisible()
    {
        return isVisible;
    }
}
