package redmennl.mods.mito.block;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import redmennl.mods.mito.MiscTools;
import redmennl.mods.mito.lib.GuiIds;
import redmennl.mods.mito.tile.TileCompanionCreator;
import redmennl.mods.mito.tile.TileCompanionCreatorBase;

public class BlockCompanionCreator extends BlockContainer
{

    public BlockCompanionCreator(int id)
    {
        super(id, Material.iron);
        this.setHardness(2F);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return null;
    }
    
    @Override
    public TileEntity createTileEntity(World world, int metadata)
    {
        switch(metadata)
        {
            case 0:
                return new TileCompanionCreatorBase();
        }
        return new TileCompanionCreator();
    }
    
    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs,
            List par3List)
    {
        for (int j = 0; j < 3; ++j)
        {
            par3List.add(new ItemStack(par1, 1, j));
        }
    }
    
    @Override
    public int damageDropped(int par1)
    {
        if (par1 == 0)
        {
            return 0;
        }
        else
        {
            return 1;
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z,
            EntityPlayer player, int par6, float par7, float par8, float par9)
    {
        if (world.isRemote)
            return true;
        else
        {
            if(world.getBlockMetadata(x, y, z) == 0)
            {
                TileCompanionCreatorBase tile = (TileCompanionCreatorBase) world
                        .getBlockTileEntity(x, y, z);
    
                if (tile != null && tile.isFormed())
                {
                    player.openGui(MiscTools.instance, GuiIds.COMPANIONCREATOR,
                            world, x, y, z);
                }
            }
            else if (world.getBlockMetadata(x, y, z) == 2)
            {
                System.out.println("OPEN!");
                TileCompanionCreator tile = (TileCompanionCreator) world
                        .getBlockTileEntity(x, y, z);
                if (tile != null && tile.tileBasePos != null)
                {
                    TileCompanionCreatorBase base = (TileCompanionCreatorBase)world.getBlockTileEntity(x - tile.getTileBasePos().x,
                            y - tile.getTileBasePos().y, z - tile.getTileBasePos().z);
                    System.out.println("Place: " + tile.getTileBasePos().z + ":" + tile.getTileBasePos().y + ":" + tile.getTileBasePos().z);
                    
                    if (base != null && base.isFormed())
                    {
                        player.openGui(MiscTools.instance, GuiIds.COMPANIONCREATOR,
                            world, x - tile.getTileBasePos().x,
                            y - tile.getTileBasePos().y, z - tile.getTileBasePos().z);
                    }
                }
            }
            else
                return false;

            return true;
        }
    }
    
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z,
            EntityLivingBase entity, ItemStack item)
    {
        if (world.getBlockMetadata(x, y, z) == 0)
        {
            System.out.println("I am the Base!");
            ((TileCompanionCreatorBase)world.getBlockTileEntity(x, y, z)).checkIfFormed();
        }
        else
        {
            System.out.println("Searching for Base!");
            searchTileBase(world, x, y, z);
        }
    }
    
    public void breakBlock(World world, int x, int y, int z, int par5, int meta)
    {
        if (meta == 0)
        {
            TileCompanionCreatorBase tile = (TileCompanionCreatorBase) world.getBlockTileEntity(x, y, z);
            if (tile != null && tile.isFormed())
            {
                tile.deformBlocks();
                tile.setIsFormed(false);
            }
        }
        else if (meta == 2)
        {
            TileCompanionCreator tile = (TileCompanionCreator) world.getBlockTileEntity(x, y, z);
            if (tile != null && tile.tileBasePos != null)
            {
                TileCompanionCreatorBase baseTile = (TileCompanionCreatorBase) world.getBlockTileEntity(x - tile.tileBasePos.x, y - tile.tileBasePos.y, z - tile.tileBasePos.z);
                if (baseTile != null)
                {
                    baseTile.deformBlocks();
                    baseTile.setIsFormed(false);
                }
            }
        }
        super.breakBlock(world, x, y, z, par5, meta);
    }
    
    public void searchTileBase(World world, int x, int y, int z)
    {
        for (int xPos = x - 1; xPos <= x + 1; xPos++)
        {
            for (int yPos = y - 1; yPos <= y + 1; yPos++)
            {
                for (int zPos = z - 1; zPos <= z + 1; zPos++)
                {
                    TileEntity tile = world.getBlockTileEntity(xPos, yPos, zPos);
                    
                    if (tile != null && tile instanceof TileCompanionCreatorBase && !((TileCompanionCreatorBase)tile).isFormed())
                    {
                        ((TileCompanionCreatorBase)tile).checkIfFormed();
                        return;
                    }
                }
            }
        }
    }
    
    @SideOnly(Side.CLIENT)
    private Icon[] icons;
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        icons = new Icon[2];
        icons[0] = par1IconRegister.registerIcon("wool_colored_white");
        icons[1] = par1IconRegister.registerIcon("wool_colored_black");
    }
    
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int par1, int par2)
    {
        if(par2 == 0 || par2 == 2)
        {
            return icons[0];
        }
        else
        {
            return icons[1];
        }
    }
    
    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public int getRenderType()
    {
        return -1;
    }
}
