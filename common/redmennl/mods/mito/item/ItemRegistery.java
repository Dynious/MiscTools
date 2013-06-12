package redmennl.mods.mito.item;

import redmennl.mods.mito.lib.ItemIds;
import net.minecraft.block.Block;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ItemRegistery
{
	public static Item helmetTorch;
	public static Item minersIronHelmet;
	public static Item minersGoldHelmet;
	public static Item minersDiamondHelmet;
	public static Item minersIronHelmetOn;
	public static Item minersGoldHelmetOn;
	public static Item minersDiamondHelmetOn;
	
	public static void register()
	{
		helmetTorch = new ItemHelmetTorch(ItemIds.HELMETTORCH);
		minersIronHelmet = new ItemMinersHelmet(ItemIds.MINERSIRONHELMET, EnumArmorMaterial.IRON, 2, 0).setUnlocalizedName("minersIronHelmet");
		minersGoldHelmet = new ItemMinersHelmet(ItemIds.MINERSGOLDHELMET, EnumArmorMaterial.GOLD, 2, 0).setUnlocalizedName("minersGoldHelmet");
		minersDiamondHelmet = new ItemMinersHelmet(ItemIds.MINERSDIAMONDHELMET, EnumArmorMaterial.DIAMOND, 2, 0).setUnlocalizedName("minersDiamondHelmet");
		minersIronHelmetOn = new ItemMinersHelmetOn(ItemIds.MINERSIRONHELMETON, EnumArmorMaterial.IRON, 2, 0).setUnlocalizedName("OminersIronHelmet");
		minersGoldHelmetOn = new ItemMinersHelmetOn(ItemIds.MINERSGOLDHELMETON, EnumArmorMaterial.GOLD, 2, 0).setUnlocalizedName("OminersGoldHelmet");
		minersDiamondHelmetOn = new ItemMinersHelmetOn(ItemIds.MINERSDIAMONDHELMETON, EnumArmorMaterial.DIAMOND, 2, 0).setUnlocalizedName("OminersDiamondHelmet");
		
		LanguageRegistry.addName(minersIronHelmet, "Iron Miners Helmet");
		LanguageRegistry.addName(minersGoldHelmet, "Gold Miners Helmet");
		LanguageRegistry.addName(minersDiamondHelmet, "Diamond Miners Helmet");
		LanguageRegistry.addName(minersIronHelmetOn, "Iron Miners Helmet");
		LanguageRegistry.addName(minersGoldHelmetOn, "Gold Miners Helmet");
		LanguageRegistry.addName(minersDiamondHelmetOn, "Diamond Miners Helmet");
		
		registerItemRecipes();
	}
	
	private static void registerItemRecipes()
	{
		GameRegistry.addRecipe(new ItemStack(helmetTorch), "pp", "pt", "pp", 't', Block.torchWood, 'p', Block.thinGlass);
		GameRegistry.addShapelessRecipe(new ItemStack(minersIronHelmet), Item.helmetIron, helmetTorch);
		GameRegistry.addShapelessRecipe(new ItemStack(minersGoldHelmet), Item.helmetGold, helmetTorch);
		GameRegistry.addShapelessRecipe(new ItemStack(minersDiamondHelmet), Item.helmetDiamond, helmetTorch);
	}
}
