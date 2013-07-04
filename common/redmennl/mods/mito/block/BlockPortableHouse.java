package redmennl.mods.mito.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import redmennl.mods.mito.client.gui.GuiPortableHouse;
import redmennl.mods.mito.lib.Library;
import redmennl.mods.mito.tile.TileAdvancedPortableHouse;
import redmennl.mods.mito.tile.TileAdvancedPortableHouseDeployer;
import redmennl.mods.mito.tile.TilePortableHouse;
import redmennl.mods.mito.tile.TilePortableHouseDeployer;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockPortableHouse extends BlockContainer
{

    public BlockPortableHouse(int id)
    {
        super(id, Material.iron);
        this.setUnlocalizedName("portableHouse");
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setHardness(50.0F);
    }

    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return null;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata)
    {
        switch (metadata)
        {
            case 0:
                return new TilePortableHouse();
            case 1:
                return new TilePortableHouseDeployer();
            case 2:
                return new TileAdvancedPortableHouse();
            case 3:
                return new TileAdvancedPortableHouseDeployer();
        }
        return null;
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

    @Override
    public int quantityDropped(int meta, int fortune, Random random)
    {
        return 0;
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs,
            List par3List)
    {
        for (int j = 0; j < 4; ++j)
        {
            par3List.add(new ItemStack(par1, 1, j));
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z,
            EntityPlayer player, int par6, float par7, float par8, float par9)
    {

        if (player.isSneaking())
            return false;
        else
        {
            if (!world.isRemote)
            {

                TileEntity TE = world.getBlockTileEntity(x, y, z);
                if (TE instanceof TilePortableHouse)
                {
                    TilePortableHouse tile = (TilePortableHouse) world
                            .getBlockTileEntity(x, y, z);

                    if (tile != null)
                    {
                        GuiPortableHouse gui = new GuiPortableHouse(player,
                                world, tile, null);
                        FMLCommonHandler.instance().showGuiScreen(gui);
                    }
                } else if (TE instanceof TilePortableHouseDeployer)
                {
                    TilePortableHouseDeployer tile = (TilePortableHouseDeployer) world
                            .getBlockTileEntity(x, y, z);

                    if (tile != null)
                    {
                        GuiPortableHouse gui = new GuiPortableHouse(player,
                                world, null, tile);
                        FMLCommonHandler.instance().showGuiScreen(gui);
                    }
                }
            }

            return true;
        }
    }

    @Override
    public void onBlockPlacedBy(World world, int i, int j, int k,
            EntityLivingBase entity, ItemStack item)
    {
        if (item.hasTagCompound()
                && world.getBlockTileEntity(i, j, k) instanceof TileAdvancedPortableHouseDeployer)
        {
            TileAdvancedPortableHouseDeployer tile = (TileAdvancedPortableHouseDeployer) world
                    .getBlockTileEntity(i, j, k);

            if (tile != null)
            {
                tile.idArr = item.getTagCompound().getIntArray("id");
                tile.metaArr = item.getTagCompound().getIntArray("meta");
                tile.xArr = item.getTagCompound().getIntArray("xArr");
                tile.yArr = item.getTagCompound().getIntArray("yArr");
                tile.zArr = item.getTagCompound().getIntArray("zArr");
                tile.size = item.getTagCompound().getInteger("size");
                tile.hight = item.getTagCompound().getInteger("hight");
                if (item.getTagCompound().getString("name") != null)
                {
                    tile.name = item.getTagCompound().getString("name");
                }
                tile.hasTag = item.getTagCompound().getIntArray("hasTag");
                tile.tag = item.getTagCompound();
            }
        }

        else if (item.hasTagCompound()
                && world.getBlockTileEntity(i, j, k) instanceof TileAdvancedPortableHouse)
        {
            TileAdvancedPortableHouse tile = (TileAdvancedPortableHouse) world
                    .getBlockTileEntity(i, j, k);

            if (tile != null)
            {
                if (item.getTagCompound().getInteger("size") != 0)
                {
                    tile.size = item.getTagCompound().getInteger("size");
                }
                if (item.getTagCompound().getInteger("hight") != 0)
                {
                    tile.hight = item.getTagCompound().getInteger("hight");
                }
                if (item.getTagCompound().getString("name") != null)
                {
                    tile.name = item.getTagCompound().getString("name");
                }
            }
        }

        else if (item.hasTagCompound()
                && world.getBlockTileEntity(i, j, k) instanceof TilePortableHouseDeployer)
        {
            TilePortableHouseDeployer tile = (TilePortableHouseDeployer) world
                    .getBlockTileEntity(i, j, k);

            if (tile != null)
            {
                tile.idArr = item.getTagCompound().getIntArray("id");
                tile.metaArr = item.getTagCompound().getIntArray("meta");
                tile.xArr = item.getTagCompound().getIntArray("x");
                tile.yArr = item.getTagCompound().getIntArray("y");
                tile.zArr = item.getTagCompound().getIntArray("z");
                tile.size = item.getTagCompound().getInteger("size");
                tile.hight = item.getTagCompound().getInteger("hight");
                if (item.getTagCompound().getString("name") != null)
                {
                    tile.name = item.getTagCompound().getString("name");
                }
            }
        }

        else if (item.hasTagCompound()
                && world.getBlockTileEntity(i, j, k) instanceof TilePortableHouse)
        {
            TilePortableHouse tile = (TilePortableHouse) world
                    .getBlockTileEntity(i, j, k);

            if (tile != null)
            {
                if (item.getTagCompound().getInteger("size") != 0)
                {
                    tile.size = item.getTagCompound().getInteger("size");
                }
                if (item.getTagCompound().getInteger("hight") != 0)
                {
                    tile.hight = item.getTagCompound().getInteger("hight");
                }
                if (item.getTagCompound().getString("name") != null)
                {
                    tile.name = item.getTagCompound().getString("name");
                }
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        blockIcon = par1IconRegister.registerIcon(Library.MOD_ID + ":"
                + this.getUnlocalizedName().substring(5));
    }

}
