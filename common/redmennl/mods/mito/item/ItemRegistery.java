package redmennl.mods.mito.item;

import net.minecraft.block.Block;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import redmennl.mods.mito.MiscTools;
import redmennl.mods.mito.entity.companion.addon.item.ItemCrafter;
import redmennl.mods.mito.entity.companion.addon.item.ItemPowerConnector;
import redmennl.mods.mito.lib.ItemIds;
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
    public static Item companion;
    public static Item connectionInterface;
    public static Item powerConnector;
    public static Item crafter;

    public static void register()
    {
        helmetTorch = new ItemHelmetTorch(ItemIds.HELMETTORCH);
        minersIronHelmet = new ItemMinersHelmet(ItemIds.MINERSIRONHELMET,
                EnumArmorMaterial.IRON, 2, 0)
                .setUnlocalizedName("minersIronHelmet");
        minersGoldHelmet = new ItemMinersHelmet(ItemIds.MINERSGOLDHELMET,
                EnumArmorMaterial.GOLD, 2, 0)
                .setUnlocalizedName("minersGoldHelmet");
        minersDiamondHelmet = new ItemMinersHelmet(ItemIds.MINERSDIAMONDHELMET,
                EnumArmorMaterial.DIAMOND, 2, 0)
                .setUnlocalizedName("minersDiamondHelmet");
        minersIronHelmetOn = new ItemMinersHelmetOn(ItemIds.MINERSIRONHELMETON,
                EnumArmorMaterial.IRON, 2, 0)
                .setUnlocalizedName("OminersIronHelmet");
        minersGoldHelmetOn = new ItemMinersHelmetOn(ItemIds.MINERSGOLDHELMETON,
                EnumArmorMaterial.GOLD, 2, 0)
                .setUnlocalizedName("OminersGoldHelmet");
        minersDiamondHelmetOn = new ItemMinersHelmetOn(
                ItemIds.MINERSDIAMONDHELMETON, EnumArmorMaterial.DIAMOND, 2, 0)
                .setUnlocalizedName("OminersDiamondHelmet");
        companion = new ItemCompanion(ItemIds.COMPANION);
        
        connectionInterface = new ItemConnectionInterface(ItemIds.CONNECTIONINTERFACE);
        crafter = new ItemCrafter(ItemIds.CRAFTER);

        LanguageRegistry.addName(helmetTorch, "Helmet Torch");
        LanguageRegistry.addName(minersIronHelmet, "Iron Miners Helmet");
        LanguageRegistry.addName(minersGoldHelmet, "Gold Miners Helmet");
        LanguageRegistry.addName(minersDiamondHelmet, "Diamond Miners Helmet");
        LanguageRegistry.addName(minersIronHelmetOn, "Iron Miners Helmet");
        LanguageRegistry.addName(minersGoldHelmetOn, "Gold Miners Helmet");
        LanguageRegistry
                .addName(minersDiamondHelmetOn, "Diamond Miners Helmet");
        LanguageRegistry.addName(companion, "Companion");
        
        LanguageRegistry.addName(connectionInterface, "Connection Interface");
        LanguageRegistry.addName(crafter, "Crafter");

        registerItemRecipes();
        registerModDependantItems();
    }

    private static void registerItemRecipes()
    {
        GameRegistry.addRecipe(new ItemStack(helmetTorch), "pp", "pt", "pp",
                't', Block.torchWood, 'p', Block.thinGlass);
        GameRegistry.addShapelessRecipe(new ItemStack(minersIronHelmet),
                Item.helmetIron, helmetTorch);
        GameRegistry.addShapelessRecipe(new ItemStack(minersGoldHelmet),
                Item.helmetGold, helmetTorch);
        GameRegistry.addShapelessRecipe(new ItemStack(minersDiamondHelmet),
                Item.helmetDiamond, helmetTorch);
        
        GameRegistry.addRecipe(new ItemStack(connectionInterface), "iri", "rgr", "iri",
                'i', Item.ingotIron, 'r', Item.redstone, 'g', Item.ingotGold);
        GameRegistry.addShapelessRecipe(new ItemStack(crafter), connectionInterface, Block.workbench);
    }
    
    private static void registerModDependantItems()
    {
        
        if ((MiscTools.hasBCE && MiscTools.hasBCT) || MiscTools.hasIC2 || MiscTools.hasUE)
        {
            powerConnector = new ItemPowerConnector(ItemIds.POWERCONNECTOR);
            LanguageRegistry.addName(powerConnector, "Power Connector");
        }
    }
    
    public static void registerModDependantRecipes()
    {
        if (MiscTools.hasBCE && MiscTools.hasBCT)
        {
            Item goldPipe = GameRegistry.findItem("BuildCraft|Transport", "item.buildcraftPipe.pipepowergold");
            if (goldPipe != null)
            {
                System.out.println(goldPipe.getUnlocalizedName());
                GameRegistry.addShapelessRecipe(new ItemStack(powerConnector, 1, 0), connectionInterface, goldPipe);
            }
        }
        if (MiscTools.hasIC2)
        {
            Item glassFiberCable = GameRegistry.findItem("IC2", "item.itemGlassCable");
            if (glassFiberCable != null)
            {
                GameRegistry.addShapelessRecipe(new ItemStack(powerConnector, 2, 0), connectionInterface, glassFiberCable);
            }
        }
    }
}
