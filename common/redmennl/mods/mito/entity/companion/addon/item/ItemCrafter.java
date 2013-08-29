package redmennl.mods.mito.entity.companion.addon.item;

import net.minecraft.client.renderer.texture.IconRegister;
import redmennl.mods.mito.lib.Library;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCrafter extends ItemAddonBase
{

    public ItemCrafter(int id)
    {
        super(id);
        this.setUnlocalizedName("crafter");
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister ir)
    {
        itemIcon = ir.registerIcon(Library.MOD_ID + ":"
                + this.getUnlocalizedName().substring(5));
    }
}
