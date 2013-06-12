package redmennl.mods.mito.block;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import redmennl.mods.mito.block.item.ItemLetter;
import redmennl.mods.mito.block.item.ItemLetter2;
import redmennl.mods.mito.block.item.ItemPortableHouse;
import redmennl.mods.mito.lib.BlockIds;
import redmennl.mods.mito.lib.Library;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class BlockRegistery
{	
	public static Block portableHouse;
	public static Block lightEmitter;
	public static Block letter;
	public static Block letter2;
	public static Block letterConstructor;
	
	public static void register()
	{
		portableHouse = new BlockPortableHouse(BlockIds.PORTABLEHOUSE);
		lightEmitter = new BlockLightEmitter(BlockIds.LIGHTEMITTER);
		letter = new BlockLetter(BlockIds.LETTER);
		letter2 = new BlockLetter2(BlockIds.LETTER2);
		letterConstructor = new BlockLetterConstructor(BlockIds.LETTERCONSTRUCTOR);
		
		GameRegistry.registerBlock(portableHouse, ItemPortableHouse.class, Library.MOD_ID + portableHouse.getUnlocalizedName2());
		GameRegistry.registerBlock(lightEmitter, Library.MOD_ID + lightEmitter.getUnlocalizedName2());
		GameRegistry.registerBlock(letter, ItemLetter.class, Library.MOD_ID + letter.getUnlocalizedName2());
		GameRegistry.registerBlock(letter2, ItemLetter2.class, Library.MOD_ID + letter2.getUnlocalizedName2());
		GameRegistry.registerBlock(letterConstructor, Library.MOD_ID + letterConstructor.getUnlocalizedName2());
		

		LanguageRegistry.addName(new ItemStack(portableHouse, 1, 0), "Portable House");
		LanguageRegistry.addName(new ItemStack(portableHouse, 1, 1), "Portable House Deployer");
		LanguageRegistry.addName(new ItemStack(portableHouse, 1, 2), "Advanced Portable House");
		LanguageRegistry.addName(new ItemStack(portableHouse, 1, 3), "Advanced Portable House Deployer");
		
		LanguageRegistry.addName(new ItemStack(letter, 1, 0), "Letter A");
		LanguageRegistry.addName(new ItemStack(letter, 1, 1), "Letter B");
		LanguageRegistry.addName(new ItemStack(letter, 1, 2), "Letter C");
		LanguageRegistry.addName(new ItemStack(letter, 1, 3), "Letter D");
		LanguageRegistry.addName(new ItemStack(letter, 1, 4), "Letter E");
		LanguageRegistry.addName(new ItemStack(letter, 1, 5), "Letter F");
		LanguageRegistry.addName(new ItemStack(letter, 1, 6), "Letter G");
		LanguageRegistry.addName(new ItemStack(letter, 1, 7), "Letter H");
		LanguageRegistry.addName(new ItemStack(letter, 1, 8), "Letter I");
		LanguageRegistry.addName(new ItemStack(letter, 1, 9), "Letter J");
		LanguageRegistry.addName(new ItemStack(letter, 1, 10), "Letter K");
		LanguageRegistry.addName(new ItemStack(letter, 1, 11), "Letter L");
		LanguageRegistry.addName(new ItemStack(letter, 1, 12), "Letter M");
		LanguageRegistry.addName(new ItemStack(letter, 1, 13), "Letter N");
		LanguageRegistry.addName(new ItemStack(letter, 1, 14), "Letter O");
		LanguageRegistry.addName(new ItemStack(letter, 1, 15), "Letter P");
		
		LanguageRegistry.addName(new ItemStack(letter2, 1, 0), "Letter Q");
		LanguageRegistry.addName(new ItemStack(letter2, 1, 1), "Letter R");
		LanguageRegistry.addName(new ItemStack(letter2, 1, 2), "Letter S");
		LanguageRegistry.addName(new ItemStack(letter2, 1, 3), "Letter T");
		LanguageRegistry.addName(new ItemStack(letter2, 1, 4), "Letter U");
		LanguageRegistry.addName(new ItemStack(letter2, 1, 5), "Letter V");
		LanguageRegistry.addName(new ItemStack(letter2, 1, 6), "Letter W");
		LanguageRegistry.addName(new ItemStack(letter2, 1, 7), "Letter X");
		LanguageRegistry.addName(new ItemStack(letter2, 1, 8), "Letter Y");
		LanguageRegistry.addName(new ItemStack(letter2, 1, 9), "Letter Z");
		
		LanguageRegistry.addName(letterConstructor, "Letter Constructor");
		
		registerBlockRecipes();
	}
	private static void registerBlockRecipes()
	{
		GameRegistry.addRecipe(new ItemStack(portableHouse, 1, 0), "ipi", "pep", "ipi", 'i', Item.ingotIron, 'p', Block.thinGlass, 'e', Item.enderPearl);
		GameRegistry.addRecipe(new ItemStack(portableHouse, 1, 2), "ded", "ehe", "ded", 'd', Item.diamond, 'e', Item.enderPearl, 'h', new ItemStack(portableHouse, 1, 0));
		GameRegistry.addRecipe(new ItemStack(letterConstructor), "sps", "scs", "sss", 's', Block.stone, 'p', Item.pickaxeIron, 'c', Block.workbench);
	}
	
	
}
