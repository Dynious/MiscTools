package redmennl.mods.mito.tile;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileAdvancedPortableHouse extends TilePortableHouse
{
	@Override
	public void saveBlocks()
	{
		
		World world = this.getWorldObj();
    	
    	ItemStack stack = new ItemStack(this.getBlockType(), 1, 3);
        if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
        
        int idNum = 0;
    	int[] idArr = new int[size*size*hight];
    	int[] metaArr = new int[size*size*hight];
    	int[] xArr = new int[size*size*hight];
    	int[] yArr = new int[size*size*hight];
    	int[] zArr = new int[size*size*hight];
    	int[] hasTag = new int[size*size*hight];
    	
    	for (byte x = 0; x < size; x++) 
    	{
    		int xTrans = this.xCoord -((size - 1)/2) + x;
    		
        	for (byte y = 0; y < hight; y++) 
        	{
        		int yTrans = this.yCoord - 1 + y;
        		
            	for (byte z = 0; z < size; z++) 
            	{
            		int zTrans = this.zCoord -((size - 1)/2) + z;
            		
            		if (xTrans != this.xCoord || yTrans != this.yCoord || zTrans != this.zCoord)
            		{
            			int id = world.getBlockId(xTrans, yTrans, zTrans);
            			int meta = world.getBlockMetadata(xTrans, yTrans, zTrans);
            			if (id != 0)
            			{
            				idArr[idNum] = id;
            				metaArr[idNum] = meta;
            				xArr[idNum] = x;
            				yArr[idNum] = y;
            				zArr[idNum] = z;
            				TileEntity TE = world.getBlockTileEntity(xTrans, yTrans, zTrans);
            				if (TE != null)
            				{
            					try
            					{
            						TE.getClass().getDeclaredMethod("writeToNBT", NBTTagCompound.class);
            						NBTTagList nbttaglist = new NBTTagList();
            						
            						NBTTagCompound nbt = new NBTTagCompound();
            						TE.writeToNBT(nbt);
            						nbttaglist.appendTag(nbt);
            						stack.getTagCompound().setTag(Integer.toString(idNum), nbttaglist);
            						hasTag[idNum] = 1;
            					} catch (Exception e) {hasTag[idNum] = 0;}
            				}
            				else
            				{
            					hasTag[idNum] = 0;
            				}
            				idNum++;
            				
            				try
							{
            					Block.blocksList[id].getClass().getDeclaredMethod("canPlaceBlockAt", new Class[] { World.class, int.class, int.class, int.class });
            					
            					if(TE != null)
            					{
            						TE.invalidate();
            					}
                    			world.destroyBlock(xTrans, yTrans, zTrans, false);
							} catch (Exception e) {System.out.println(id);}
            			}
            		}
            	}
        	}
    	}
    	
    	
    	stack.getTagCompound().setIntArray("id", idArr);
        stack.getTagCompound().setIntArray("meta", metaArr);
        stack.getTagCompound().setIntArray("xArr", xArr);
        stack.getTagCompound().setIntArray("yArr", yArr);
        stack.getTagCompound().setIntArray("zArr", zArr);
        stack.getTagCompound().setInteger("size", size);
        stack.getTagCompound().setInteger("hight", hight);
        if (name != ""){stack.getTagCompound().setString("name", name);}
        stack.getTagCompound().setIntArray("hasTag", hasTag);
        
        //new DelayedAction("Portable House", 5, world, this.xCoord, this.yCoord, this.zCoord, size, hight);
        
    	for (byte x = 0; x < size; x++) 
    	{
    		int xTrans = this.xCoord -((size - 1)/2) + x;
    		
        	for (byte y = 0; y < hight; y++) 
        	{
        		int yTrans = this.yCoord - 1 + y;
        		
            	for (byte z = 0; z < size; z++) 
            	{
            		int zTrans = this.zCoord -((size - 1)/2) + z;
            		
            		if (xTrans != this.xCoord || yTrans != this.yCoord || zTrans != this.zCoord)
            		{
            			TileEntity TE = world.getBlockTileEntity(xTrans, yTrans, zTrans);
            			if (TE != null)
            			{
            				TE.invalidate();
            			}
            			world.destroyBlock(xTrans, yTrans, zTrans, false);
            		}
            	}
        	}
    	}

        Random rand = new Random();
    	float rx = rand.nextFloat() * 0.4F + 0.1F;
        float ry = rand.nextFloat() * 0.4F + 0.1F;
        float rz = rand.nextFloat() * 0.4F + 0.1F;

        EntityItem entityItem = new EntityItem(world, this.xCoord + rx, this.yCoord + ry, this.zCoord + rz, stack);
    	world.spawnEntityInWorld(entityItem);
    	
    	world.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);
	}
	
	
	@Override
	public void breakBlock()
	{
		World world = this.getWorldObj();
		ItemStack stack = new ItemStack(this.getBlockType(), 1, 2);
        if (!stack.hasTagCompound() && (size != 1 || hight != 1 || name != "")) stack.setTagCompound(new NBTTagCompound());

        if (size != 1){stack.getTagCompound().setInteger("size", size);}
        if (hight != 1){stack.getTagCompound().setInteger("hight", hight);}
        if (name != ""){stack.getTagCompound().setString("name", name);}

        EntityItem entityItem = new EntityItem(world, this.xCoord, this.yCoord, this.zCoord, stack);
    	world.spawnEntityInWorld(entityItem);
    	
    	world.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);
	}
}
