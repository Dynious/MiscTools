package redmennl.mods.mito.entity.companion.addon.item;

import java.util.List;

import redmennl.mods.mito.MiscTools;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;


public class ItemPowerConnector extends ItemAddonBase
{

    public ItemPowerConnector(int id)
    {
        super(id);
        this.setUnlocalizedName("powerConnector");
    }
    
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        if (MiscTools.hasBCE && MiscTools.hasBCT)
        {
            par3List.add(new ItemStack(par1, 1, 0));
        }
        if (MiscTools.hasIC2)
        {
            par3List.add(new ItemStack(par1, 1, 1));
        }
        if (MiscTools.hasUE)
        {
            par3List.add(new ItemStack(par1, 1, 2));
        }
    }
    
    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void addInformation(ItemStack i, EntityPlayer p, List l, boolean b)
    {
        if (i.getItemDamage() == 0)
        {
            l.add("Accepts MJ (BuildCraft)");
        }
        if (i.getItemDamage() == 1)
        {
            l.add("Accepts EU (IndustialCraft2)");
        }
        if (i.getItemDamage() == 2)
        {
            l.add("Accepts WATTS (Universal Electricity)");
        }
    }
    
}
