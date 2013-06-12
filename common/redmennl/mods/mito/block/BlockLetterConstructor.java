package redmennl.mods.mito.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
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

	protected BlockLetterConstructor(int id)
	{
		super(id, Material.rock);
		this.setUnlocalizedName("letterConstructor");
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
            TileLetterConstructor tileentityfurnace = (TileLetterConstructor)world.getBlockTileEntity(x, y, z);

            if (tileentityfurnace != null)
            {
            	player.openGui(MiscTools.instance, GuiIds.LETTERCONSTRUCTOR, world, x, y, z);
            }

            return true;
        }
    }
}
