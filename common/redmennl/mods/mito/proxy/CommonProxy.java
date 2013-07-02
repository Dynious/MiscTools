package redmennl.mods.mito.proxy;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import redmennl.mods.mito.client.gui.GuiCompanion;
import redmennl.mods.mito.client.gui.GuiLetterConstructor;
import redmennl.mods.mito.entity.EntityCompanion;
import redmennl.mods.mito.entity.EntityPowerLaser;
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
	static int startEntityId = 300;

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
		GameRegistry.registerTileEntity(TileCompanionCreator.class, "companionCreator");
	}
	
	public void initEntities(Object mod)
	{
		//EntityRegistry.registerGlobalEntityID(EntityCompanion.class, "companion", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerModEntity(EntityCompanion.class, "companion", 1, mod, 40, 1, true);
		LanguageRegistry.instance().addStringLocalization("entity." + Library.MOD_ID + ".companion.name", "Companion");
		registerEntityEgg(EntityCompanion.class, 0xffffff, 0x000000);
		
		EntityRegistry.registerModEntity(EntityPowerLaser.class, "laser", 2, mod, 40, 1, true);
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
        else if (ID == GuiIds.COMPANION)
        {
        	EntityCompanion e = null;
        	AxisAlignedBB a = AxisAlignedBB.getBoundingBox(x - 0.4, y - 0.4, z - 0.4, x + 0.4, y + 0.4, z + 0.4);
        	try
        	{
        		e = (EntityCompanion) world.getEntitiesWithinAABB(EntityCompanion.class, a).get(0);
        	}
        	catch(Exception ex){}
        	
        	if (e != null)
        	{
        		return new ContainerCompanion(player.inventory, e);
        	}
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
        else if (ID == GuiIds.COMPANION)
        {
        	EntityCompanion e = null;
        	AxisAlignedBB a = AxisAlignedBB.getBoundingBox(x - 0.4, y - 0.4, z - 0.4, x + 0.4, y + 0.4, z + 0.4);
        	try
        	{
        		e = (EntityCompanion) world.getEntitiesWithinAABB(EntityCompanion.class, a).get(0);
        	}
        	catch(Exception ex){}
        	
        	if (e != null)
        	{
        		return new GuiCompanion(player.inventory, e, player);
        	}
        }
		return null;
	}
	
    public void registerSoundHandler() {}
    
    public void initTextureLocations() {}
    
    public static int getUniqueEntityId() 
    {
    	do
    	{
    		startEntityId++;
    	}
    	
    	while (EntityList.getStringFromID(startEntityId) != null);
    	
    	return startEntityId;
    }
    
    @SuppressWarnings("unchecked")
	public static void registerEntityEgg(Class<? extends Entity> entity, int primaryColor, int secondaryColor) 
    {
    	int id = getUniqueEntityId();
    	EntityList.IDtoClassMapping.put(id, entity);
    	EntityList.entityEggs.put(id, new EntityEggInfo(id, primaryColor, secondaryColor));
    }
}


