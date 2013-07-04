package redmennl.mods.mito.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import redmennl.mods.mito.lib.ItemIds;
import redmennl.mods.mito.lib.Library;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMinersHelmetOn extends ItemArmor
{

    public ItemMinersHelmetOn(int id, EnumArmorMaterial par2EnumArmorMaterial,
            int renderIndex, int slot)
    {
        super(id - Library.ITEM_ID_OFFSET, par2EnumArmorMaterial, renderIndex,
                slot);
        this.setCreativeTab(null);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister ir)
    {
        itemIcon = ir.registerIcon(Library.MOD_ID + ":"
                + this.getUnlocalizedName().substring(6));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getArmorTexture(ItemStack stack, Entity entity, int slot,
            int layer)
    {
        return Library.ARMOR_SHEET_LOCATION
                + this.getUnlocalizedName().substring(6) + ".png";
    }

    @Override
    public ItemStack onItemRightClick(ItemStack i, World w, EntityPlayer p)
    {
        if (i.itemID == ItemIds.MINERSIRONHELMETON)
        {
            ItemStack item = new ItemStack(
                    Item.itemsList[ItemIds.MINERSIRONHELMET]);
            p.inventory.setInventorySlotContents(p.inventory.currentItem, item);
        } else if (i.itemID == ItemIds.MINERSGOLDHELMETON)
        {
            ItemStack item = new ItemStack(
                    Item.itemsList[ItemIds.MINERSGOLDHELMET]);
            p.inventory.setInventorySlotContents(p.inventory.currentItem, item);
        } else if (i.itemID == ItemIds.MINERSDIAMONDHELMETON)
        {
            ItemStack item = new ItemStack(
                    Item.itemsList[ItemIds.MINERSDIAMONDHELMET]);
            p.inventory.setInventorySlotContents(p.inventory.currentItem, item);
        }
        return i;
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void addInformation(ItemStack i, EntityPlayer p, List l, boolean b)
    {
        l.add("Right-click to turn off");
    }
}
