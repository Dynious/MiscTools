package redmennl.mods.mito.tile;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileAdvancedPortableHouseDeployer extends TilePortableHouseDeployer
{
	public int[] hasTag = new int[9*2*5];
	public NBTTagCompound tag;
	
	@Override
	public void deploy()
	{
		World world = this.getWorldObj();
		
		if (clearArea)
		{
	    	for (byte x = 0; x < size; x++) 
	    	{
	    		int xTrans = this.xCoord -((size - 1)/2) + x;
	    		
	        	for (byte y = 0; y < hight; y++) 
	        	{
	        		int yTrans = this.yCoord - 1 + y;
	        		
	            	for (byte z = 0; z < size; z++) 
	            	{
	            		int zTrans = this.zCoord -((size - 1)/2) + z;
	            		
	            		if (xTrans == this.xCoord && yTrans == this.yCoord && zTrans == this.zCoord)
	            		{
	            			
	            		}
	            		else
	            		{
	            			world.destroyBlock(xTrans, yTrans, zTrans, false);
	            		}
	            	}
	        	}
	    	}
		}
		
		int xStart = this.xCoord-((size-1)/2);
		int zStart = this.zCoord-((size-1)/2);
		for (int i = 0; i < idArr.length; i++)
		{

			if (idArr[i] != 0)
			{
				world.setBlock(xStart+xArr[i], this.yCoord-1+yArr[i], zStart+zArr[i], idArr[i], metaArr[i], 2);
				if (hasTag[i] == 1)
				{
					TileEntity TE = world.getBlockTileEntity(xStart+xArr[i], this.yCoord-1+yArr[i], zStart+zArr[i]);

					NBTTagList nbttaglist = tag.getTagList(Integer.toString(i));
					NBTTagCompound nbt = (NBTTagCompound)nbttaglist.tagAt(0);					
					nbt.setInteger("x", xStart+xArr[i]);
					nbt.setInteger("y", this.yCoord-1+yArr[i]);
					nbt.setInteger("z", zStart+zArr[i]);
					TE.readFromNBT(nbt);
				}
			}
		}

		ItemStack stack = new ItemStack(this.getBlockType(), 1, 2);
		EntityItem entityItem = new EntityItem(world, this.xCoord, this.yCoord, this.zCoord, stack);
	    world.spawnEntityInWorld(entityItem);
	    
		world.destroyBlock(this.xCoord, this.yCoord, this.zCoord, false);
	}
	
	@Override
	public void breakBlock()
	{
		World world = this.getWorldObj();
		ItemStack stack = new ItemStack(this.getBlockType(), 1, 3);
        if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
        
        stack.getTagCompound().setIntArray("id", idArr);
        stack.getTagCompound().setIntArray("meta", metaArr);
        stack.getTagCompound().setIntArray("xArr", xArr);
        stack.getTagCompound().setIntArray("yArr", yArr);
        stack.getTagCompound().setIntArray("zArr", zArr);
        stack.getTagCompound().setInteger("size", size);
        stack.getTagCompound().setInteger("hight", hight);
        if (name != null) {stack.getTagCompound().setString("name", name);}
        stack.getTagCompound().setIntArray("hasTag", hasTag);
        stack.setTagCompound(tag);

        EntityItem entityItem = new EntityItem(world, this.xCoord, this.yCoord, this.zCoord, stack);
    	world.spawnEntityInWorld(entityItem);
    	
    	world.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt = tag;
		nbt.setIntArray("mito_TPHD_id", idArr);
		nbt.setIntArray("mito_TPHD_meta", metaArr);
		nbt.setIntArray("mito_TPHD_x", xArr);
		nbt.setIntArray("mito_TPHD_y", yArr);
		nbt.setIntArray("mito_TPHD_z", zArr);
		nbt.setInteger("mito_TPHD_size", size);
		nbt.setInteger("mito_TPHD_hight", hight);
		if (name != null) {nbt.setString("mito_TPHD_name", name);}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		tag = nbt;
		idArr = nbt.getIntArray("mito_TPHD_id");
		metaArr = nbt.getIntArray("mito_TPHD_meta");
		xArr = nbt.getIntArray("mito_TPHD_x");
		yArr = nbt.getIntArray("mito_TPHD_y");
		zArr = nbt.getIntArray("mito_TPHD_z");
		size = nbt.getInteger("mito_TPHD_size");
		hight = nbt.getInteger("mito_TPHD_hight");
		if (nbt.getString("name") != null){name = nbt.getString("mito_TPHD_name");}
	}
}
