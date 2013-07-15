package redmennl.mods.mito.tile;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import redmennl.mods.mito.entity.EntityPowerLaser;
import redmennl.mods.mito.helper.Pos3d;
import redmennl.mods.mito.lib.Library;

public class TilePortableHouse extends TileEntity
{
    public int size = 3;
    public int hight = 2;
    public String name = "";
    public boolean lasersCreated;
    public int num = 0;

    public void saveBlocks()
    {

        World world = this.getWorldObj();

        ItemStack stack = new ItemStack(this.getBlockType(), 1, 1);
        if (!stack.hasTagCompound())
        {
            stack.setTagCompound(new NBTTagCompound());
        }

        int idNum = 0;
        int[] idArr = new int[size * size * hight];
        int[] metaArr = new int[size * size * hight];
        int[] xArr = new int[size * size * hight];
        int[] yArr = new int[size * size * hight];
        int[] zArr = new int[size * size * hight];

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
                        int id = world.getBlockId(xTrans, yTrans, zTrans);
                        int meta = world.getBlockMetadata(xTrans, yTrans,
                                zTrans);
                        if (id != 0)
                        {
                            idArr[idNum] = id;
                            metaArr[idNum] = meta;
                            xArr[idNum] = x;
                            yArr[idNum] = y;
                            zArr[idNum] = z;
                            idNum++;

                            try
                            {
                                Block.blocksList[id].getClass()
                                        .getDeclaredMethod(
                                                "canPlaceBlockAt",
                                                new Class[] { World.class,
                                                        int.class, int.class,
                                                        int.class });
                                world.destroyBlock(xTrans, yTrans, zTrans,
                                        false);
                            } catch (Exception e)
                            {
                            }
                        }
                    }
                }
            }
        }

        stack.getTagCompound().setIntArray("id", idArr);
        stack.getTagCompound().setIntArray("meta", metaArr);
        stack.getTagCompound().setIntArray("x", xArr);
        stack.getTagCompound().setIntArray("y", yArr);
        stack.getTagCompound().setIntArray("z", zArr);
        stack.getTagCompound().setInteger("size", size);
        stack.getTagCompound().setInteger("hight", hight);
        if (name != "")
        {
            stack.getTagCompound().setString("name", name);
        }

        world.playSoundEffect(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D,
                Library.MOD_ID + ":saveblocks", 1.0F, 1.0F);

        // new DelayedAction("Portable House", 5, world, this.xCoord,
        // this.yCoord, this.zCoord, size, hight);

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

        Random rand = new Random();
        float rx = rand.nextFloat() * 0.4F + 0.1F;
        float ry = rand.nextFloat() * 0.4F + 0.1F;
        float rz = rand.nextFloat() * 0.4F + 0.1F;

        EntityItem entityItem = new EntityItem(world, xCoord + rx, yCoord + ry,
                zCoord + rz, stack);
        world.spawnEntityInWorld(entityItem);

        world.setBlockToAir(xCoord, yCoord, zCoord);

    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();
        // createLaserBox();
        // System.out.println("update");
    }

    public void createLaserBox()
    {
        double xMin = xCoord - (size - 1) / 2;
        double xMax = xCoord + (size - 1) / 2;
        double yMin = yCoord - 1;
        double yMax = yCoord - 1 + hight;
        double zMin = zCoord - (size - 1) / 2;
        double zMax = zCoord + (size - 1) / 2;
        Pos3d[] p = new Pos3d[8];

        p[0] = new Pos3d(xMin, yMin, zMin);
        p[1] = new Pos3d(xMax, yMin, zMin);
        p[2] = new Pos3d(xMin, yMax, zMin);
        p[3] = new Pos3d(xMax, yMax, zMin);
        p[4] = new Pos3d(xMin, yMin, zMax);
        p[5] = new Pos3d(xMax, yMin, zMax);
        p[6] = new Pos3d(xMin, yMax, zMax);
        p[7] = new Pos3d(xMax, yMax, zMax);

        createLaser(p[0], p[1]);
        createLaser(p[0], p[2]);
        createLaser(p[2], p[3]);
        createLaser(p[1], p[3]);
        createLaser(p[4], p[5]);
        createLaser(p[4], p[6]);
        createLaser(p[5], p[7]);
        createLaser(p[6], p[7]);
        createLaser(p[0], p[4]);
        createLaser(p[1], p[5]);
        createLaser(p[2], p[6]);
        createLaser(p[3], p[7]);
        System.out.println("box creation");
        lasersCreated = true;
    }

    public void createLaser(Pos3d head, Pos3d tail)
    {

        if (worldObj.isRemote)
            return;

        EntityPowerLaser laser = new EntityPowerLaser(worldObj, head, tail);
        laser.setTexture(Library.ENTITY_TEXTURE_LASER);
        laser.show();

        worldObj.spawnEntityInWorld(laser);
        if (num != 11)
        {
            num++;
        } else
        {
            num = 0;
        }
    }

    public void updateLasers()
    {

    }

    public void breakBlock()
    {
        World world = this.getWorldObj();
        ItemStack stack = new ItemStack(this.getBlockType(), 1, 0);
        if (!stack.hasTagCompound() && (size != 1 || hight != 1 || name != ""))
        {
            stack.setTagCompound(new NBTTagCompound());
        }

        if (size != 1)
        {
            stack.getTagCompound().setInteger("size", size);
        }
        if (hight != 1)
        {
            stack.getTagCompound().setInteger("hight", hight);
        }
        if (name != "")
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
        if (size != 1)
        {
            nbt.setInteger("mito_TPH_size", size);
        }
        if (hight != 1)
        {
            nbt.setInteger("mito_TPH_hight", hight);
        }
        if (name != "")
        {
            nbt.setString("mito_TPH_name", name);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        size = nbt.getInteger("mito_TPH_size");
        hight = nbt.getInteger("mito_TPH_hight");
        name = nbt.getString("mito_TPH_name");
    }
}
