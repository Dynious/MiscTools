package redmennl.mods.mito.proxy;

import java.io.File;
import java.io.FileFilter;

import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import redmennl.mods.mito.client.audio.SoundHandler;
import redmennl.mods.mito.client.model.ModelCompanion;
import redmennl.mods.mito.client.renderer.RendererLetter;
import redmennl.mods.mito.client.renderer.RendererPortableHouse;
import redmennl.mods.mito.client.renderer.item.ItemRendererCompanion;
import redmennl.mods.mito.client.renderer.item.ItemRendererLetter;
import redmennl.mods.mito.client.renderer.item.ItemRendererPortableHouse;
import redmennl.mods.mito.client.renderer.living.RendererEntityCompanion;
import redmennl.mods.mito.entity.EntityCompanion;
import redmennl.mods.mito.lib.BlockIds;
import redmennl.mods.mito.lib.ItemIds;
import redmennl.mods.mito.lib.Resources;
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
    }

    @Override
    public void initEntities()
    {
        super.initEntities();

        RenderingRegistry.registerEntityRenderingHandler(EntityCompanion.class,
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
        File dir = new File(this.getClass()
                .getResource(Resources.MODEL_LOCATION).getFile()
                .replace("%20", " "));
        System.out.println(dir);
        if (!dir.isDirectory())
        {
            System.out.println("FILE!");
        }
        if (!dir.isFile())
        {
            System.out.println("DIR!");
        }
        if (!dir.exists())
        {
            System.out.println(":(");
        }
        File[] list = dir.listFiles(new FileFilter()
        {
            @Override
            public boolean accept(File file)
            {
                return file.isFile() && file.getName().endsWith(".obj");
            }
        });

        if (list.length == 0)
            return;

        for (File file : list)
        {
            String m = file.toString().substring(
                    file.toString().lastIndexOf("\\") + 1,
                    file.toString().indexOf("."));
            if (this.getClass().getResource(
                    Resources.MODEL_LOCATION + m + ".properties") != null)
            {
                Resources.modelNames.add(m);
            }
        }
        for (int i = 0; i < Resources.modelNames.size(); i++)
        {
            System.out.println(Resources.modelNames.get(i));
        }
    }
}
