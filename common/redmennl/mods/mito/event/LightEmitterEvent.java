package redmennl.mods.mito.event;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingEvent;
import redmennl.mods.mito.helper.Pos3;
import redmennl.mods.mito.lib.BlockIds;
import redmennl.mods.mito.lib.ItemIds;

public class LightEmitterEvent
{
	@SuppressWarnings("rawtypes")
	public static HashMap usernameLastPosMap = new HashMap();
	
	@SuppressWarnings("unchecked")
	@ForgeSubscribe
	  public void onPlayerMove(LivingEvent.LivingUpdateEvent e)
	{
		if ((e.entityLiving instanceof EntityPlayer)) {
			EntityPlayer p = (EntityPlayer)e.entityLiving;
			
			if (!p.worldObj.isRemote)
			{
				if (isWearingMinersHelmet(p.inventory) || hasSelectedLightEmittingBlock(p.inventory))
				{
			          Pos3 pos = new Pos3(MathHelper.floor_double(p.posX), MathHelper.floor_double(p.posY) + 1, MathHelper.floor_double(p.posZ));

			          if (p.ridingEntity != null)
			          {
			            pos = new Pos3(MathHelper.floor_double(p.ridingEntity.posX), MathHelper.floor_double(p.ridingEntity.posY) + 1, MathHelper.floor_double(p.ridingEntity.posZ));
			          }

			          if (p.worldObj.getBlockId(pos.x, pos.y, pos.z) == 0) {
			            p.worldObj.setBlock(pos.x, pos.y, pos.z, BlockIds.LIGHTEMITTER, 0, 3);
			          }
			          
			          if (usernameLastPosMap.containsKey(p.username))
			          {
			              Pos3 pos2 = (Pos3)usernameLastPosMap.get(p.username);

			              if (((pos2.x != pos.x) || (pos2.y != pos.y) || (pos2.z != pos.z)) && (p.worldObj.getBlockId(pos2.x, pos2.y, pos2.z) == BlockIds.LIGHTEMITTER))
			              {
			                p.worldObj.setBlock(pos2.x, pos2.y, pos2.z, 0, 0, 3);
			              }
			          }
			    usernameLastPosMap.put(p.username, pos);
				}
		        else if (usernameLastPosMap.containsKey(p.username)) {
		            Pos3 pos = (Pos3)usernameLastPosMap.get(p.username);
		            
		            if (p.worldObj.getBlockId(pos.x, pos.y, pos.z) == BlockIds.LIGHTEMITTER) {
		              p.worldObj.setBlock(pos.x, pos.y, pos.z, 0, 0, 3);
		            }
		            usernameLastPosMap.remove(p.username);
		        }
			}
		}
	}
	
	public static boolean isWearingMinersHelmet(InventoryPlayer inv)
	{
	    ItemStack item = inv.armorInventory[3];
	    
	    if ((item != null) && (item.itemID == ItemIds.MINERSIRONHELMETON || item.itemID == ItemIds.MINERSGOLDHELMETON || item.itemID == ItemIds.MINERSDIAMONDHELMETON))
	    {
	    	return true;
	    }
	    
	    return false;
	}
	
	public static boolean hasSelectedLightEmittingBlock(InventoryPlayer inv)
	{
		ItemStack item = inv.getCurrentItem();
		if ((item != null) && (item.itemID == Block.torchWood.blockID || item.itemID == Block.glowStone.blockID || 
				item.itemID == Block.pumpkinLantern.blockID || item.itemID == ItemIds.MINERSIRONHELMETON || item.itemID == ItemIds.MINERSGOLDHELMETON || 
				item.itemID == ItemIds.MINERSDIAMONDHELMETON))
		{
			return true;
		}
		
		return false;
	}
}
