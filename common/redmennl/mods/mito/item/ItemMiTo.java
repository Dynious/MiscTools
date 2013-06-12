package redmennl.mods.mito.item;

import redmennl.mods.mito.lib.Library;
import net.minecraft.item.Item;

public class ItemMiTo extends Item
{

	public ItemMiTo(int id)
	{
		super(id - Library.ITEM_ID_OFFSET);
	}

}
