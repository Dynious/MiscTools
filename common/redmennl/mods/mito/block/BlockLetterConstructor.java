package redmennl.mods.mito.block;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import redmennl.mods.mito.MiscTools;
import redmennl.mods.mito.lib.GuiIds;
import redmennl.mods.mito.lib.Library;
import redmennl.mods.mito.tile.TileLetterConstructor;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockLetterConstructor extends BlockContainer
{
	private Random rand = new Random();

	protected BlockLetterConstructor(int id)
	{
		super(id, Material.rock);
		this.setUnlocalizedName("letterConstructor");
		this.setHardness(2F);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
	
	 @SideOnly(Side.CLIENT)
     private Icon[] icons;
	 
	  @SideOnly(Side.CLIENT)
      public void registerIcons(IconRegister par1IconRegister)
      {
		  icons = new Icon[2];
		  for(int i = 0; i < icons.length; i++)
          {
                 icons[i] = par1IconRegister.registerIcon(Library.MOD_ID + ":" + (this.getUnlocalizedName().substring(5)) + i);
          }
      }
	 
     @SideOnly(Side.CLIENT)
     public Icon getIcon(int par1, int par2)
     {
         switch(par1)
         {
                case 1:
                      return icons[0];
                default:
                      return icons[1];
         }
     }

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileLetterConstructor();
	}
	
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
    {
        if (world.isRemote)
        {
            return true;
        }
        else
        {
            TileLetterConstructor tile = (TileLetterConstructor)world.getBlockTileEntity(x, y, z);

            if (tile != null)
            {
            	player.openGui(MiscTools.instance, GuiIds.LETTERCONSTRUCTOR, world, x, y, z);
            }

            return true;
        }
    }
    
    @Override
    public void breakBlock(World world, int x, int y, int z, int id, int meta) {

        dropInventory(world, x, y, z);
        super.breakBlock(world, x, y, z, id, meta);
    }
    
    private void dropInventory(World world, int x, int y, int z) {

        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

        if (!(tileEntity instanceof IInventory))
            return;

        IInventory inventory = (IInventory) tileEntity;

        for (int i = 0; i < 1; i++) {

            ItemStack itemStack = inventory.getStackInSlot(i);

            if (itemStack != null && itemStack.stackSize > 0) {
                float dX = rand.nextFloat() * 0.8F + 0.1F;
                float dY = rand.nextFloat() * 0.8F + 0.1F;
                float dZ = rand.nextFloat() * 0.8F + 0.1F;

                EntityItem entityItem = new EntityItem(world, x + dX, y + dY, z + dZ, new ItemStack(itemStack.itemID, itemStack.stackSize, itemStack.getItemDamage()));

                if (itemStack.hasTagCompound()) {
                    entityItem.getEntityItem().setTagCompound((NBTTagCompound) itemStack.getTagCompound().copy());
                }

                float factor = 0.05F;
                entityItem.motionX = rand.nextGaussian() * factor;
                entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
                entityItem.motionZ = rand.nextGaussian() * factor;
                world.spawnEntityInWorld(entityItem);
                itemStack.stackSize = 0;
            }
        }
    }
}
