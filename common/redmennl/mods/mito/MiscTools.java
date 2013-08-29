package redmennl.mods.mito;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import redmennl.mods.mito.block.BlockRegistery;
import redmennl.mods.mito.client.gui.hud.GuiCompanionHUD;
import redmennl.mods.mito.config.ConfigurationHandler;
import redmennl.mods.mito.creativetab.TabMito;
import redmennl.mods.mito.entity.companion.CompanionGlobalVariables;
import redmennl.mods.mito.event.LightEmitterEvent;
import redmennl.mods.mito.item.ItemRegistery;
import redmennl.mods.mito.lib.Library;
import redmennl.mods.mito.network.PacketHandler;
import redmennl.mods.mito.proxy.CommonProxy;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = Library.MOD_ID, name = Library.MOD_NAME, version = Library.VERSION)
@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels = Library.CHANNEL_NAME, packetHandler = PacketHandler.class)
public class MiscTools
{
    @Instance(Library.MOD_ID)
    public static MiscTools instance;

    @SidedProxy(clientSide = Library.CLIENT_PROXY_CLASS, serverSide = Library.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;
    
    public static CreativeTabs tabMito = new TabMito(CreativeTabs.getNextID(), "Misc Tools");

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        ConfigurationHandler.init(event.getSuggestedConfigurationFile());
        
        checkLoadedMods();

        BlockRegistery.register();

        ItemRegistery.register();

        proxy.registerSoundHandler();
        
        proxy.registerKeyBindingHandler();
    }

    @EventHandler
    public void load(FMLInitializationEvent event)
    {
        NetworkRegistry.instance().registerGuiHandler(instance, proxy);

        proxy.initTileEntities();

        proxy.initEntities();

        proxy.initRenderingAndTextures();

        proxy.findModels();

        MinecraftForge.EVENT_BUS.register(new LightEmitterEvent());
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new GuiCompanionHUD(Minecraft.getMinecraft()));
        CompanionGlobalVariables.initBlocks();
        
        ItemRegistery.registerModDependantRecipes();
    }
    
    public static boolean hasBCE;
    public static boolean hasBCT;
    public static boolean hasIC2;
    public static boolean hasUE;
    public static boolean hasMyst;
    
    public void checkLoadedMods()
    {
        hasBCE = Loader.isModLoaded("BuildCraft|Energy");
        hasBCT = Loader.isModLoaded("BuildCraft|Transport");
        hasIC2 = Loader.isModLoaded("IC2");
        hasUE = Loader.isModLoaded("UniversalElectricity");
        hasMyst = Loader.isModLoaded("Mystcraft");
    }
}
