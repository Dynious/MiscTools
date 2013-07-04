package redmennl.mods.mito.item;

import net.minecraft.item.Item;
import redmennl.mods.mito.lib.Library;

public class ItemMito extends Item
{

    public ItemMito(int id)
    {
        super(id - Library.ITEM_ID_OFFSET);
    }

}
