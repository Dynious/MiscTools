package redmennl.mods.mito.block;

import java.util.List;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import redmennl.mods.mito.MiscTools;
import redmennl.mods.mito.tile.TileLetter;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockLetter extends BlockContainer
{

    public BlockLetter(int id)
    {
        super(id, Material.rock);
        this.setUnlocalizedName("letter");
        this.setHardness(2F);
        this.setCreativeTab(MiscTools.tabMito);
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
                return new TileLetter("A");
            case 1:
                return new TileLetter("B");
            case 2:
                return new TileLetter("C");
            case 3:
                return new TileLetter("D");
            case 4:
                return new TileLetter("E");
            case 5:
                return new TileLetter("F");
            case 6:
                return new TileLetter("G");
            case 7:
                return new TileLetter("H");
            case 8:
                return new TileLetter("I");
            case 9:
                return new TileLetter("J");
            case 10:
                return new TileLetter("K");
            case 11:
                return new TileLetter("L");
            case 12:
                return new TileLetter("M");
            case 13:
                return new TileLetter("N");
            case 14:
                return new TileLetter("O");
            case 15:
                return new TileLetter("P");
            case 16:
                return new TileLetter("Q");
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
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs,
            List par3List)
    {
        for (int j = 0; j < 16; ++j)
        {
            par3List.add(new ItemStack(par1, 1, j));
        }
    }

    @Override
    public int damageDropped(int par1)
    {
        return par1;
    }

    public void onBlockPlacedBy(World world, int x, int y, int z,
            EntityLiving e, ItemStack i)
    {
        int l = MathHelper.floor_double(e.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

        if (l == 0)
        {
            TileLetter tile = (TileLetter) world.getBlockTileEntity(x, y, z);
            if (tile != null)
            {
                tile.orientation = ForgeDirection.SOUTH;
            }
        }

        if (l == 1)
        {
            TileLetter tile = (TileLetter) world.getBlockTileEntity(x, y, z);
            if (tile != null)
            {
                tile.orientation = ForgeDirection.WEST;
            }
        }

        if (l == 2)
        {
            TileLetter tile = (TileLetter) world.getBlockTileEntity(x, y, z);
            if (tile != null)
            {
                tile.orientation = ForgeDirection.NORTH;
            }
        }

        if (l == 3)
        {
            TileLetter tile = (TileLetter) world.getBlockTileEntity(x, y, z);
            if (tile != null)
            {
                tile.orientation = ForgeDirection.EAST;
            }
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z,
            EntityPlayer player, int par6, float par7, float par8, float par9)
    {
        ItemStack itemstack = player.getCurrentEquippedItem();
        if (itemstack != null
                && itemstack.getItem().itemID == Item.dyePowder.itemID)
        {
            int meta = itemstack.getItemDamage();
            TileLetter tile = (TileLetter) world.getBlockTileEntity(x, y, z);
            tile.color = 15 - meta;
            return true;
        }
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        blockIcon = par1IconRegister.registerIcon("wool_colored_white");
    }
}
