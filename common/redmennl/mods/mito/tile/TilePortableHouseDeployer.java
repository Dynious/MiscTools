package redmennl.mods.mito.tile;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TilePortableHouseDeployer extends TileEntity
{
    public int[] idArr = new int[9 * 2 * 5];
    public int[] metaArr = new int[9 * 2 * 5];
    public int[] xArr = new int[9 * 2 * 5];
    public int[] yArr = new int[9 * 2 * 5];
    public int[] zArr = new int[9 * 2 * 5];
    public int size;
    public int hight;
    public String name;
    public boolean clearArea = false;

    public void deploy()
    {
        World world = this.getWorldObj();

        if (clearArea)
        {
            for (byte x = 0; x < size; x++)
            {
                int xTrans = xCoord - (size - 1) / 2 + x;

                for (byte y = 0; y < hight; y++)
                {
                    int yTrans = yCoord - 1 + y;

                    for (byte z = 0; z < size; z++)
                    {
                        int zTrans = zCoord - (size - 1) / 2 + z;

                        if (xTrans != xCoord || yTrans != yCoord
                                || zTrans != zCoord)
                        {
                            world.destroyBlock(xTrans, yTrans, zTrans, false);
                        }
                    }
                }
            }
        }

        int xStart = xCoord - (size - 1) / 2;
        int zStart = zCoord - (size - 1) / 2;
        for (int i = 0; i < idArr.length; i++)
        {

            if (idArr[i] != 0)
            {
                world.setBlock(xStart + xArr[i], yCoord - 1 + yArr[i], zStart
                        + zArr[i], idArr[i], metaArr[i], 2);
            }
        }
        if (!clearArea)
        {
            ItemStack stack = new ItemStack(this.getBlockType(), 1, 0);
            EntityItem entityItem = new EntityItem(world, xCoord, yCoord,
                    zCoord, stack);
            world.spawnEntityInWorld(entityItem);
        }
        world.destroyBlock(xCoord, yCoord, zCoord, false);
    }

    public void breakBlock()
    {
        World world = this.getWorldObj();
        ItemStack stack = new ItemStack(this.getBlockType(), 1, 1);
        if (!stack.hasTagCompound())
        {
            stack.setTagCompound(new NBTTagCompound());
        }

        stack.getTagCompound().setIntArray("id", idArr);
        stack.getTagCompound().setIntArray("meta", metaArr);
        stack.getTagCompound().setIntArray("x", xArr);
        stack.getTagCompound().setIntArray("y", yArr);
        stack.getTagCompound().setIntArray("z", zArr);
        stack.getTagCompound().setInteger("size", size);
        stack.getTagCompound().setInteger("hight", hight);
        if (name != null)
        {
            stack.getTagCompound().setString("name", name);
        }

        EntityItem entityItem = new EntityItem(world, xCoord, yCoord, zCoord,
                stack);
        world.spawnEntityInWorld(entityItem);

        world.setBlockToAir(xCoord, yCoord, zCoord);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setIntArray("mito_TPHD_id", idArr);
        nbt.setIntArray("mito_TPHD_meta", metaArr);
        nbt.setIntArray("mito_TPHD_x", xArr);
        nbt.setIntArray("mito_TPHD_y", yArr);
        nbt.setIntArray("mito_TPHD_z", zArr);
        nbt.setInteger("mito_TPHD_size", size);
        nbt.setInteger("mito_TPHD_hight", hight);
        if (name != null)
        {
            nbt.setString("mito_TPHD_name", name);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        idArr = nbt.getIntArray("mito_TPHD_id");
        metaArr = nbt.getIntArray("mito_TPHD_meta");
        xArr = nbt.getIntArray("mito_TPHD_x");
        yArr = nbt.getIntArray("mito_TPHD_y");
        zArr = nbt.getIntArray("mito_TPHD_z");
        size = nbt.getInteger("mito_TPHD_size");
        hight = nbt.getInteger("mito_TPHD_hight");
        if (nbt.getString("name") != null)
        {
            name = nbt.getString("mito_TPHD_name");
        }
    }
}
