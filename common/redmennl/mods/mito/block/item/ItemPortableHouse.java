package redmennl.mods.mito.block.item;

import java.util.List;

import redmennl.mods.mito.lib.BlockIds;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemPortableHouse extends ItemBlock
{
	int meta[] = new int[9*2*5];

	public ItemPortableHouse(int i)
	{
		super(i);
		setHasSubtypes(true);
	}
	
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void addInformation(ItemStack i, EntityPlayer p, List l, boolean b)
    {
    	
    	if (i.hasTagCompound())
    	{
    		if (i.getTagCompound().getString("name") != "")
    		{
    			l.add(i.getTagCompound().getString("name"));
    		}
    	}
    	
    }
    
    @Override
    public int getMetadata(int par1)
    {
        return par1;
    }
    
	@Override
    public String getUnlocalizedName(ItemStack i)
    {
        return Block.blocksList[BlockIds.PORTABLEHOUSE].getUnlocalizedName() + i.getItemDamage();
    }
}
