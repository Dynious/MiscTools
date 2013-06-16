package redmennl.mods.mito.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import redmennl.mods.mito.MiscTools;
import redmennl.mods.mito.lib.GuiIds;
import redmennl.mods.mito.tile.TileCompanionCreator;

public class BlockCompanionCreator extends BlockContainer
{

	public BlockCompanionCreator(int id)
	{
		super(id, Material.rock);
		this.setHardness(2F);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileCompanionCreator();
	}
	
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
    {
        if (world.isRemote)
        {
            return true;
        }
        else
        {
            TileCompanionCreator tile = (TileCompanionCreator)world.getBlockTileEntity(x, y, z);

            if (tile != null)
            {
            	player.openGui(MiscTools.instance, GuiIds.COMPANIONCREATOR, world, x, y, z);
            }

            return true;
        }
    }
}
