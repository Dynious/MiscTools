package redmennl.mods.mito.tile;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TilePortableHouseDeployer extends TileEntity
{
	public int[] idArr = new int[9*2*5];
	public int[] metaArr = new int[9*2*5];
	public int[] xArr = new int[9*2*5];
	public int[] yArr = new int[9*2*5];
	public int[] zArr = new int[9*2*5];
	public int size;
	public int hight;
	public String name;
	public boolean clearArea = false;
	
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
			}
		}
		if (!clearArea)
		{ 
			ItemStack stack = new ItemStack(this.getBlockType(), 1, 0);
			EntityItem entityItem = new EntityItem(world, this.xCoord, this.yCoord, this.zCoord, stack);
	    	world.spawnEntityInWorld(entityItem);
		}
		world.destroyBlock(this.xCoord, this.yCoord, this.zCoord, false);
	}
	
	public void breakBlock()
	{
		World world = this.getWorldObj();
		ItemStack stack = new ItemStack(this.getBlockType(), 1, 1);
        if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
        
        stack.getTagCompound().setIntArray("id", idArr);
        stack.getTagCompound().setIntArray("meta", metaArr);
        stack.getTagCompound().setIntArray("x", xArr);
        stack.getTagCompound().setIntArray("y", yArr);
        stack.getTagCompound().setIntArray("z", zArr);
        stack.getTagCompound().setInteger("size", size);
        stack.getTagCompound().setInteger("hight", hight);
        if (name != null) {stack.getTagCompound().setString("name", name);}

        EntityItem entityItem = new EntityItem(world, this.xCoord, this.yCoord, this.zCoord, stack);
    	world.spawnEntityInWorld(entityItem);
    	
    	world.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setIntArray("MiTo_TPHD_id", idArr);
		nbt.setIntArray("MiTo_TPHD_meta", metaArr);
		nbt.setIntArray("MiTo_TPHD_x", xArr);
		nbt.setIntArray("MiTo_TPHD_y", yArr);
		nbt.setIntArray("MiTo_TPHD_z", zArr);
		nbt.setInteger("MiTo_TPHD_size", size);
		nbt.setInteger("MiTo_TPHD_hight", hight);
		if (name != null) {nbt.setString("MiTo_TPHD_name", name);}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		idArr = nbt.getIntArray("MiTo_TPHD_id");
		metaArr = nbt.getIntArray("MiTo_TPHD_meta");
		xArr = nbt.getIntArray("MiTo_TPHD_x");
		yArr = nbt.getIntArray("MiTo_TPHD_y");
		zArr = nbt.getIntArray("MiTo_TPHD_z");
		size = nbt.getInteger("MiTo_TPHD_size");
		hight = nbt.getInteger("MiTo_TPHD_hight");
		if (nbt.getString("name") != null){name = nbt.getString("MiTo_TPHD_name");}
	}
}
