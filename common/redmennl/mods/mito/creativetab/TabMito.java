package redmennl.mods.mito.creativetab;

import redmennl.mods.mito.item.ItemRegistery;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TabMito extends CreativeTabs
{

    public TabMito(int par1, String par2Str)
    {
        super(par1, par2Str);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getIconItemStack()
    {
        return new ItemStack(ItemRegistery.companion, 1, 0);
    }
    
    public String getTranslatedTabLabel()
    {
        return "Misc Tools";
    }
}
