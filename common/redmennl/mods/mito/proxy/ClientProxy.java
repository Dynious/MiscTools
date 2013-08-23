package redmennl.mods.mito.proxy;

import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import redmennl.mods.mito.client.KeybindingHandler;
import redmennl.mods.mito.client.audio.SoundHandler;
import redmennl.mods.mito.client.model.ModelCompanion;
import redmennl.mods.mito.client.renderer.RendererCompanionCreator;
import redmennl.mods.mito.client.renderer.RendererCompanionCreatorBase;
import redmennl.mods.mito.client.renderer.RendererLetter;
import redmennl.mods.mito.client.renderer.RendererPortableHouse;
import redmennl.mods.mito.client.renderer.item.ItemRendererCompanion;
import redmennl.mods.mito.client.renderer.item.ItemRendererLetter;
import redmennl.mods.mito.client.renderer.item.ItemRendererPortableHouse;
import redmennl.mods.mito.client.renderer.living.RendererEntityCompanion;
import redmennl.mods.mito.entity.companion.EntityCompanion;
import redmennl.mods.mito.entity.companion.EntityCompanionDummy;
import redmennl.mods.mito.helper.ResourceLister;
import redmennl.mods.mito.lib.BlockIds;
import redmennl.mods.mito.lib.ItemIds;
import redmennl.mods.mito.lib.Resources;
import redmennl.mods.mito.tile.TileAdvancedPortableHouse;
import redmennl.mods.mito.tile.TileAdvancedPortableHouseDeployer;
import redmennl.mods.mito.tile.TileCompanionCreator;
import redmennl.mods.mito.tile.TileCompanionCreatorBase;
import redmennl.mods.mito.tile.TileLetter;
import redmennl.mods.mito.tile.TilePortableHouse;
import redmennl.mods.mito.tile.TilePortableHouseDeployer;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
    @Override
    public void registerKeyBindingHandler() {

        KeyBindingRegistry.registerKeyBinding(new KeybindingHandler());
    }
    
    @Override
    public void initRenderingAndTextures()
    {
        MinecraftForgeClient.registerItemRenderer(BlockIds.PORTABLEHOUSE,
                new ItemRendererPortableHouse());
        MinecraftForgeClient.registerItemRenderer(BlockIds.LETTER,
                new ItemRendererLetter());
        MinecraftForgeClient.registerItemRenderer(BlockIds.LETTER2,
                new ItemRendererLetter());
        MinecraftForgeClient.registerItemRenderer(ItemIds.COMPANION,
                new ItemRendererCompanion());
    }

    @Override
    public void initTileEntities()
    {
        super.initTileEntities();

        ClientRegistry.bindTileEntitySpecialRenderer(TilePortableHouse.class,
                new RendererPortableHouse());
        ClientRegistry.bindTileEntitySpecialRenderer(
                TilePortableHouseDeployer.class, new RendererPortableHouse());
        ClientRegistry.bindTileEntitySpecialRenderer(
                TileAdvancedPortableHouse.class, new RendererPortableHouse());
        ClientRegistry.bindTileEntitySpecialRenderer(
                TileAdvancedPortableHouseDeployer.class,
                new RendererPortableHouse());
        ClientRegistry.bindTileEntitySpecialRenderer(TileLetter.class,
                new RendererLetter());
        ClientRegistry.bindTileEntitySpecialRenderer(TileCompanionCreatorBase.class,
                new RendererCompanionCreatorBase());
        ClientRegistry.bindTileEntitySpecialRenderer(TileCompanionCreator.class,
                new RendererCompanionCreator());
    }

    @Override
    public void initEntities()
    {
        super.initEntities();

        RenderingRegistry.registerEntityRenderingHandler(EntityCompanion.class,
                new RendererEntityCompanion(new ModelCompanion()));
        RenderingRegistry.registerEntityRenderingHandler(EntityCompanionDummy.class,
                new RendererEntityCompanion(new ModelCompanion()));
    }

    @Override
    public void registerSoundHandler()
    {

        MinecraftForge.EVENT_BUS.register(new SoundHandler());
    }

    @Override
    public void findModels()
    {
        try
        {
            String[] modelFolderContents = ResourceLister.getResourceListing(
                    this.getClass(), Resources.MODEL_LOCATION.substring(1));
            for (String folderContent : modelFolderContents)
            {
                if (folderContent.endsWith(".obj"))
                {
                    folderContent = folderContent.substring(0,
                            folderContent.indexOf("."));
                    if (this.getClass().getResource(
                            Resources.MODEL_LOCATION + folderContent
                                    + ".properties") != null)
                    {
                        Resources.modelNames.add(folderContent);
                    }
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("Misc Tools found these Compaion models:");
        for (int i = 0; i < Resources.modelNames.size(); i++)
        {
            System.out.println(Resources.modelNames.get(i));
        }
    }
}
