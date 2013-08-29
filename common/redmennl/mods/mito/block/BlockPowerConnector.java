package redmennl.mods.mito.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockPowerConnector extends BlockContainer
{
    public BlockPowerConnector(int id)
    {
        super(id, Material.air);
        this.setUnlocalizedName("powerConnector");
        setBlockBounds(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return null;
    }

    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return null;
    }
    
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    @Override
    public boolean isAirBlock(World w, int x, int y, int z)
    {
        if (w.getBlockId(x, y, z) == blockID)
            return true;

        return false;
    }
}
