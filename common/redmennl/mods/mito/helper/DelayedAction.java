package redmennl.mods.mito.helper;

import java.util.EnumSet;

import net.minecraft.world.World;

import cpw.mods.fml.common.IScheduledTickHandler;
import cpw.mods.fml.common.TickType;

public class DelayedAction implements IScheduledTickHandler
{
	public int tickSpacing;
	public String label;
	public World world;
	public int xCoord;
	public int yCoord;
	public int zCoord;
	public int size;
	public int hight;
	public int x = 0;
	public int y = 0;
	public int z = 0;
	public boolean hasTask;
	
	public DelayedAction(String label, int tickSpacing, World world, int xCoord, int yCoord, int zCoord, int size, int hight)
	{
		this.label = label;
		this.tickSpacing = tickSpacing;
		this.world = world;
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.zCoord = zCoord;
		this.size = size;
		this.hight = hight;
		hasTask = true;
	}

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData)
	{
		
		if(hasTask)
		{
			int xTrans = this.xCoord -((size - 1)/2) + x;
			if (x == size)
			{
				x = 0;
				hasTask = false;
			}
			else
			{
				x++;
			}
			int yTrans = this.yCoord - 1 + y;
			if (y == hight)
			{
				y = 0;
				x++;
			}
			else
			{
				z++;
			}
			int zTrans = this.zCoord -((size - 1)/2) + z;
			if (z == size)
			{
				z = 0;
				y++;
			}
			else
			{
				z++;
			}
			world.destroyBlock(xTrans, yTrans, zTrans, false);
		}
		
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData)
	{
		
	}

	@Override
	public EnumSet<TickType> ticks()
	{
		return EnumSet.of(TickType.WORLD);
	}

	@Override
	public String getLabel()
	{
		return label;
	}

	@Override
	public int nextTickSpacing()
	{
		return tickSpacing;
	}
	
}
