package redmennl.mods.mito;

import net.minecraftforge.common.MinecraftForge;
import redmennl.mods.mito.block.BlockRegistery;
import redmennl.mods.mito.config.ConfigurationHandler;
import redmennl.mods.mito.event.LightEmitterEvent;
import redmennl.mods.mito.item.ItemRegistery;
import redmennl.mods.mito.lib.Library;
import redmennl.mods.mito.proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = Library.MOD_ID, name = Library.MOD_NAME, version = Library.VERSION)
@NetworkMod(clientSideRequired = true, serverSideRequired = false)


public class MiscTools
{
	@Instance(Library.MOD_ID)
    public static MiscTools instance;

    @SidedProxy(clientSide = Library.CLIENT_PROXY_CLASS, serverSide = Library.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;
	
    @PreInit
    public void preInit(FMLPreInitializationEvent event)
    {
    	ConfigurationHandler.init(event.getSuggestedConfigurationFile());
    	
    	BlockRegistery.register();
    	
    	ItemRegistery.register();
    	
    	proxy.registerSoundHandler();
    }
    
    @Init
    public void load(FMLInitializationEvent event)
    {
    	NetworkRegistry.instance().registerGuiHandler(instance, proxy);
    	
        proxy.initTileEntities();
        proxy.initRenderingAndTextures();
        
        MinecraftForge.EVENT_BUS.register(new LightEmitterEvent());
    }
    
}
