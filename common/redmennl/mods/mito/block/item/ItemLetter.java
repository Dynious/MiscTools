package redmennl.mods.mito.block.item;

import redmennl.mods.mito.lib.BlockIds;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemLetter extends ItemBlock
{
	public ItemLetter(int i)
	{
		super(i);
		setHasSubtypes(true);
	}
	
    @Override
    public int getMetadata(int par1)
    {
        return par1;
    }
    
	@Override
    public String getUnlocalizedName(ItemStack i)
    {
        return Block.blocksList[BlockIds.LETTER].getUnlocalizedName() + i.getItemDamage();
    }
}
