package redmennl.mods.mito.block.item;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import redmennl.mods.mito.block.BlockRegistery;

public class ItemLetter2 extends ItemBlock
{
    public ItemLetter2(int i)
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
        return BlockRegistery.letter2.getUnlocalizedName()
                + i.getItemDamage();
    }
}
