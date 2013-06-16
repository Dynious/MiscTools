package redmennl.mods.mito.proxy;

import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import redmennl.mods.mito.client.audio.SoundHandler;
import redmennl.mods.mito.client.model.ModelCompanion;
import redmennl.mods.mito.client.renderer.RendererLetter;
import redmennl.mods.mito.client.renderer.RendererPortableHouse;
import redmennl.mods.mito.client.renderer.item.ItemRendererLetter;
import redmennl.mods.mito.client.renderer.item.ItemRendererPortableHouse;
import redmennl.mods.mito.client.renderer.living.RendererEntityCompanion;
import redmennl.mods.mito.entity.EntityCompanion;
import redmennl.mods.mito.lib.BlockIds;
import redmennl.mods.mito.tile.TileAdvancedPortableHouse;
import redmennl.mods.mito.tile.TileAdvancedPortableHouseDeployer;
import redmennl.mods.mito.tile.TileLetter;
import redmennl.mods.mito.tile.TilePortableHouse;
import redmennl.mods.mito.tile.TilePortableHouseDeployer;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

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
	
	@Override
	public void initEntities(Object mod)
	{
		super.initEntities(mod);
		
		RenderingRegistry.registerEntityRenderingHandler(EntityCompanion.class, new RendererEntityCompanion(new ModelCompanion()));
	}
	
    @Override
    public void registerSoundHandler() {

        MinecraftForge.EVENT_BUS.register(new SoundHandler());
    }
}
