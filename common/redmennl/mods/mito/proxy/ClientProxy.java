package redmennl.mods.mito.proxy;

import net.minecraftforge.client.MinecraftForgeClient;
import redmennl.mods.mito.client.renderer.RendererLetter;
import redmennl.mods.mito.client.renderer.RendererPortableHouse;
import redmennl.mods.mito.client.renderer.item.ItemRendererLetter;
import redmennl.mods.mito.client.renderer.item.ItemRendererPortableHouse;
import redmennl.mods.mito.lib.BlockIds;
import redmennl.mods.mito.tile.TileAdvancedPortableHouse;
import redmennl.mods.mito.tile.TileAdvancedPortableHouseDeployer;
import redmennl.mods.mito.tile.TileLetter;
import redmennl.mods.mito.tile.TilePortableHouse;
import redmennl.mods.mito.tile.TilePortableHouseDeployer;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy
{
	@Override
	public void initRenderingAndTextures() 
	{
		MinecraftForgeClient.registerItemRenderer(BlockIds.PORTABLEHOUSE, new ItemRendererPortableHouse());
		MinecraftForgeClient.registerItemRenderer(BlockIds.LETTER, new ItemRendererLetter());
		MinecraftForgeClient.registerItemRenderer(BlockIds.LETTER2, new ItemRendererLetter());
    }
	
	@Override
	public void initTileEntities() 
	{
		super.initTileEntities();
		
		ClientRegistry.bindTileEntitySpecialRenderer(TilePortableHouse.class, new RendererPortableHouse());
		ClientRegistry.bindTileEntitySpecialRenderer(TilePortableHouseDeployer.class, new RendererPortableHouse());
		ClientRegistry.bindTileEntitySpecialRenderer(TileAdvancedPortableHouse.class, new RendererPortableHouse());
		ClientRegistry.bindTileEntitySpecialRenderer(TileAdvancedPortableHouseDeployer.class, new RendererPortableHouse());
		ClientRegistry.bindTileEntitySpecialRenderer(TileLetter.class, new RendererLetter());
	}
}
