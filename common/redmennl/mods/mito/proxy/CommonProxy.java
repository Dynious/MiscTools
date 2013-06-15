package redmennl.mods.mito.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import redmennl.mods.mito.client.gui.GuiLetterConstructor;
import redmennl.mods.mito.inventory.ContainerLetterConstructor;
import redmennl.mods.mito.lib.GuiIds;
import redmennl.mods.mito.tile.TileAdvancedPortableHouse;
import redmennl.mods.mito.tile.TileAdvancedPortableHouseDeployer;
import redmennl.mods.mito.tile.TileLetter;
import redmennl.mods.mito.tile.TileLetterConstructor;
import redmennl.mods.mito.tile.TilePortableHouse;
import redmennl.mods.mito.tile.TilePortableHouseDeployer;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy implements IGuiHandler
{

	public void initRenderingAndTextures() 
	{
		
    }
	
	public void initTileEntities() 
	{
		GameRegistry.registerTileEntity(TilePortableHouse.class, "portableHouse");
		GameRegistry.registerTileEntity(TilePortableHouseDeployer.class, "portableHouseDeployer");
		GameRegistry.registerTileEntity(TileAdvancedPortableHouse.class, "advancedPortableHouse");
		GameRegistry.registerTileEntity(TileAdvancedPortableHouseDeployer.class, "advancedPortableHouseDeployer");
		GameRegistry.registerTileEntity(TileLetter.class, "letter");
		GameRegistry.registerTileEntity(TileLetterConstructor.class, "letterConstructor");
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z)
	{
        if (ID == GuiIds.LETTERCONSTRUCTOR)
        {
        	TileLetterConstructor tile = (TileLetterConstructor) world.getBlockTileEntity(x, y, z);
        	return new ContainerLetterConstructor(player.inventory, tile);
        }
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z)
	{
        if (ID == GuiIds.LETTERCONSTRUCTOR)
        {
        	TileLetterConstructor tile = (TileLetterConstructor) world.getBlockTileEntity(x, y, z);
        	return new GuiLetterConstructor(player.inventory, tile);
        }
		return null;
	}
	
    public void registerSoundHandler() {

    }
}


