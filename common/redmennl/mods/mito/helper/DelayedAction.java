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
	}

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData)
	{
		
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
