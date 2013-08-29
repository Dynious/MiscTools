package redmennl.mods.mito.entity.companion.addon.item;

import redmennl.mods.mito.MiscTools;
import redmennl.mods.mito.item.ItemMito;

public abstract class ItemAddonBase extends ItemMito
{

    public ItemAddonBase(int id)
    {
        super(id);
        this.setCreativeTab(MiscTools.tabMito);
    }

}
