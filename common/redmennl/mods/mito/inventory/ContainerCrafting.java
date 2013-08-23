package redmennl.mods.mito.inventory;

import net.minecraft.inventory.Container;

public abstract class ContainerCrafting extends Container
{
    int tab;
    
    public ContainerCrafting(int tab)
    {
        this.tab = tab;
    }
}
