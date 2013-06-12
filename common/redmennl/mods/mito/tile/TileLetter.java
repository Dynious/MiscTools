package redmennl.mods.mito.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

public class TileLetter extends TileEntity
{
	public String letter;
	public ForgeDirection orientation = ForgeDirection.SOUTH;
	public int color;
	
	public TileLetter(String letter)
	{
		this.letter = letter;
	}
}
