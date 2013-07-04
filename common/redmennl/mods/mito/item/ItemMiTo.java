package redmennl.mods.mito.item;

import redmennl.mods.mito.lib.Library;
import net.minecraft.item.Item;

public class ItemMito extends Item
{

	public ItemMito(int id)
	{
		super(id - Library.ITEM_ID_OFFSET);
	}

}
