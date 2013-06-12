package redmennl.mods.mito.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
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

public class ItemMinersHelmet extends ItemArmor
{
	public ItemMinersHelmet(int id, EnumArmorMaterial par2EnumArmorMaterial, int renderIndex, int slot)
	{
		super(id - Library.ITEM_ID_OFFSET, par2EnumArmorMaterial, renderIndex, slot);
		setCreativeTab(CreativeTabs.tabCombat);
	}
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ir)
	{
		this.itemIcon = ir.registerIcon(Library.MOD_ID + ":" + this.getUnlocalizedName().substring(5));
	}
	
	@SideOnly(Side.CLIENT)
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer)
	{
		return Library.ARMOR_SHEET_LOCATION + this.getUnlocalizedName().substring(5) + ".png";
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack i, World w, EntityPlayer p)
	  {
		if (i.itemID == ItemIds.MINERSIRONHELMET)
		{
			ItemStack item = new ItemStack(Item.itemsList[ItemIds.MINERSIRONHELMETON]);
			p.inventory.setInventorySlotContents(p.inventory.currentItem, item);
		}
		else if (i.itemID == ItemIds.MINERSGOLDHELMET)
		{
			ItemStack item = new ItemStack(Item.itemsList[ItemIds.MINERSGOLDHELMETON]);
			p.inventory.setInventorySlotContents(p.inventory.currentItem, item);
		}
		else if (i.itemID == ItemIds.MINERSDIAMONDHELMET)
		{
			ItemStack item = new ItemStack(Item.itemsList[ItemIds.MINERSDIAMONDHELMETON]);
			p.inventory.setInventorySlotContents(p.inventory.currentItem, item);
		}
		return i;
	  }
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addInformation(ItemStack i, EntityPlayer p, List l, boolean b)
	{
		l.add("Right-click to turn on");
	}
}
