package redmennl.mods.mito.config;

import java.io.File;
import java.util.logging.Level;

import net.minecraftforge.common.Configuration;
import redmennl.mods.mito.lib.BlockIds;
import redmennl.mods.mito.lib.ItemIds;
import redmennl.mods.mito.lib.Library;
import cpw.mods.fml.common.FMLLog;

public class ConfigurationHandler
{
    public static Configuration configuration;

    public static void init(File configFile)
    {

        configuration = new Configuration(configFile);

        try
        {
            configuration.load();

            BlockIds.PORTABLEHOUSE = configuration.getBlock("portableHouse",
                    BlockIds.PORTABLEHOUSE_DEFAULT).getInt(
                    BlockIds.PORTABLEHOUSE_DEFAULT);
            BlockIds.LIGHTEMITTER = configuration.getBlock("lightEmitter",
                    BlockIds.LIGHTEMITTER_DEFAULT).getInt(
                    BlockIds.LIGHTEMITTER_DEFAULT);
            BlockIds.LETTER = configuration.getBlock("letter",
                    BlockIds.LETTER_DEFAULT).getInt(BlockIds.LETTER_DEFAULT);
            BlockIds.LETTER2 = configuration.getBlock("letter2",
                    BlockIds.LETTER2_DEFAULT).getInt(BlockIds.LETTER2_DEFAULT);
            BlockIds.LETTERCONSTRUCTOR = configuration.getBlock(
                    "letterConstructor", BlockIds.LETTERCONSTRUCTOR_DEFAULT)
                    .getInt(BlockIds.LETTERCONSTRUCTOR_DEFAULT);
            BlockIds.COMPANIONCREATOR = configuration.getBlock(
                    "companionCreator", BlockIds.COMPANIONCREATOR_DEFAULT)
                    .getInt(BlockIds.COMPANIONCREATOR_DEFAULT);

            ItemIds.HELMETTORCH = configuration.getItem("helmetTorch",
                    ItemIds.HELMETTORCH_DEFAULT).getInt(
                    ItemIds.HELMETTORCH_DEFAULT);
            ItemIds.MINERSIRONHELMET = configuration.getItem(
                    "minersIronHelmet", ItemIds.MINERSIRONHELMET_DEFAULT)
                    .getInt(ItemIds.MINERSIRONHELMET_DEFAULT);
            ItemIds.MINERSGOLDHELMET = configuration.getItem(
                    "minersGoldHelmet", ItemIds.MINERSGOLDHELMET_DEFAULT)
                    .getInt(ItemIds.MINERSGOLDHELMET_DEFAULT);
            ItemIds.MINERSDIAMONDHELMET = configuration.getItem(
                    "minersDiamondHelmet", ItemIds.MINERSDIAMONDHELMET_DEFAULT)
                    .getInt(ItemIds.MINERSDIAMONDHELMET_DEFAULT);
            ItemIds.MINERSIRONHELMETON = configuration.getItem(
                    "minersIronHelmetOn", ItemIds.MINERSIRONHELMETON_DEFAULT)
                    .getInt(ItemIds.MINERSIRONHELMETON_DEFAULT);
            ItemIds.MINERSGOLDHELMETON = configuration.getItem(
                    "minersGoldHelmetOn", ItemIds.MINERSGOLDHELMETON_DEFAULT)
                    .getInt(ItemIds.MINERSGOLDHELMETON_DEFAULT);
            ItemIds.MINERSDIAMONDHELMETON = configuration.getItem(
                    "minersDiamondHelmetOn",
                    ItemIds.MINERSDIAMONDHELMETON_DEFAULT).getInt(
                    ItemIds.MINERSDIAMONDHELMETON_DEFAULT);
            ItemIds.COMPANION = configuration.getItem("companion",
                    ItemIds.COMPANION_DEFAULT)
                    .getInt(ItemIds.COMPANION_DEFAULT);

        } catch (Exception e)
        {
            FMLLog.log(Level.SEVERE, e, Library.MOD_NAME
                    + " has had a problem loading its configuration");
        } finally
        {
            configuration.save();
        }
    }

}
