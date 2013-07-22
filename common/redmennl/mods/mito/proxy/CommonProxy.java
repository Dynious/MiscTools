package redmennl.mods.mito.proxy;

import java.util.Iterator;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import redmennl.mods.mito.MiscTools;
import redmennl.mods.mito.client.gui.GuiCompanion;
import redmennl.mods.mito.client.gui.GuiLetterConstructor;
import redmennl.mods.mito.entity.EntityPowerLaser;
import redmennl.mods.mito.entity.companion.EntityCompanion;
import redmennl.mods.mito.inventory.ContainerCompanion;
import redmennl.mods.mito.inventory.ContainerLetterConstructor;
import redmennl.mods.mito.lib.GuiIds;
import redmennl.mods.mito.lib.Library;
import redmennl.mods.mito.tile.TileAdvancedPortableHouse;
import redmennl.mods.mito.tile.TileAdvancedPortableHouseDeployer;
import redmennl.mods.mito.tile.TileCompanionCreator;
import redmennl.mods.mito.tile.TileLetter;
import redmennl.mods.mito.tile.TileLetterConstructor;
import redmennl.mods.mito.tile.TilePortableHouse;
import redmennl.mods.mito.tile.TilePortableHouseDeployer;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class CommonProxy implements IGuiHandler
{
    public void initRenderingAndTextures()
    {

    }

    public void initTileEntities()
    {
        GameRegistry.registerTileEntity(TilePortableHouse.class,
                "portableHouse");
        GameRegistry.registerTileEntity(TilePortableHouseDeployer.class,
                "portableHouseDeployer");
        GameRegistry.registerTileEntity(TileAdvancedPortableHouse.class,
                "advancedPortableHouse");
        GameRegistry.registerTileEntity(
                TileAdvancedPortableHouseDeployer.class,
                "advancedPortableHouseDeployer");
        GameRegistry.registerTileEntity(TileLetter.class, "letter");
        GameRegistry.registerTileEntity(TileLetterConstructor.class,
                "letterConstructor");
        GameRegistry.registerTileEntity(TileCompanionCreator.class,
                "companionCreator");
    }

    public void initEntities()
    {
        // EntityRegistry.registerGlobalEntityID(EntityCompanion.class,
        // "companion", EntityRegistry.findGlobalUniqueEntityId());
        EntityRegistry.registerModEntity(EntityCompanion.class, "companion", 0,
                MiscTools.instance, 80, 1, true);
        LanguageRegistry.instance().addStringLocalization(
                "entity." + Library.MOD_ID + ".companion.name", "Companion");

        EntityRegistry.registerModEntity(EntityPowerLaser.class, "laser", 1,
                MiscTools.instance, 40, 1, true);
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world,
            int x, int y, int z)
    {
        if (ID == GuiIds.LETTERCONSTRUCTOR)
        {
            TileLetterConstructor tile = (TileLetterConstructor) world
                    .getBlockTileEntity(x, y, z);
            return new ContainerLetterConstructor(player.inventory, tile);
        } else if (ID == GuiIds.COMPANION)
        {
            EntityCompanion e = findCompanion(x, world);
            if (e != null)
                return new ContainerCompanion(player.inventory, e);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world,
            int x, int y, int z)
    {
        if (ID == GuiIds.LETTERCONSTRUCTOR)
        {
            TileLetterConstructor tile = (TileLetterConstructor) world
                    .getBlockTileEntity(x, y, z);
            return new GuiLetterConstructor(player.inventory, tile);
        } else if (ID == GuiIds.COMPANION)
        {
            EntityCompanion e = findCompanion(x, world);
            if (e != null)
                return new GuiCompanion(player.inventory, e, player);
        }
        return null;
    }

    @SuppressWarnings("rawtypes")
    private EntityCompanion findCompanion(int ID, World world)
    {
        for (Iterator i$ = world.loadedEntityList.iterator(); i$.hasNext();)
        {
            Object e = i$.next();
            if (e instanceof Entity && ((Entity) e).entityId == ID
                    && e instanceof EntityCompanion)
                return (EntityCompanion) e;
        }
        return null;
    }

    public void registerSoundHandler()
    {
    }

    public void findModels()
    {
    }
}
