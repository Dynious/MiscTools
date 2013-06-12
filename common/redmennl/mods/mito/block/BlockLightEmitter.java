package redmennl.mods.mito.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockLightEmitter extends Block
{

	public BlockLightEmitter(int id)
	{
		super(id, Material.air);
		this.setUnlocalizedName("lightEmitter");
	    setLightValue(1.0F);
	    setHardness(0.1F);
	    setBlockBounds(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
	    setTickRandomly(true);
	  }

	  public void registerIcons(IconRegister ir)
	  {
	    this.blockIcon = ir.registerIcon("air");
	  }

	  public int quantityDropped(Random r)
	  {
	    return 0;
	  }

	  public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	  {
	    return null;
	  }

	  public boolean isOpaqueCube()
	  {
	    return false;
	  }

	  public void updateTick(World w, int x, int y, int z, Random r)
	  {
	    if (w.getBlockId(x, y, z) == this.blockID)
	      w.setBlock(x, y, z, 0, 0, 3);
	  }

	  public boolean isAirBlock(World w, int x, int y, int z)
	  {
	    if (w.getBlockId(x, y, z) == this.blockID) {
	      return true;
	    }

	    return false;
	  }
}
