package redmennl.mods.mito.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import redmennl.mods.mito.helper.Pos3d;

public abstract class EntityLaser extends Entity
{

    protected Pos3d head, tail;

    public double renderSize = 0;
    public double angleY = 0;
    public double angleZ = 0;

    private boolean isVisible = false;
    protected boolean needsUpdate = true;

    public EntityLaser(World world)
    {
        super(world);

        head = new Pos3d(0, 0, 0);
        tail = new Pos3d(0, 0, 0);

    }

    public EntityLaser(World world, Pos3d head, Pos3d tail)
    {

        super(world);

        this.head = head;
        this.tail = tail;

        setPositionAndRotation(head.x, head.y, head.z, 0, 0);
        setSize(10, 10);
    }

    @Override
    protected void entityInit()
    {
        // System.out.println("laser init");

        preventEntitySpawning = false;
        noClip = true;
        isImmuneToFire = true;

        dataWatcher.addObject(8, Integer.valueOf(0));
        dataWatcher.addObject(9, Integer.valueOf(0));
        dataWatcher.addObject(10, Integer.valueOf(0));
        dataWatcher.addObject(11, Integer.valueOf(0));
        dataWatcher.addObject(12, Integer.valueOf(0));
        dataWatcher.addObject(13, Integer.valueOf(0));

        dataWatcher.addObject(14, Byte.valueOf((byte) 0));
    }

    @Override
    public void onUpdate()
    {

        if (head == null || tail == null)
            return;

        if (!worldObj.isRemote && needsUpdate)
        {
            updateDataServer();
            needsUpdate = false;
        }

        if (worldObj.isRemote)
        {
            updateDataClient();
        }

        boundingBox.minX = Math.min(head.x, tail.x);
        boundingBox.minY = Math.min(head.y, tail.y);
        boundingBox.minZ = Math.min(head.z, tail.z);

        boundingBox.maxX = Math.max(head.x, tail.x);
        boundingBox.maxY = Math.max(head.y, tail.y);
        boundingBox.maxZ = Math.max(head.z, tail.z);

        boundingBox.minX--;
        boundingBox.minY--;
        boundingBox.minZ--;

        boundingBox.maxX++;
        boundingBox.maxY++;
        boundingBox.maxZ++;

        double dx = head.x - tail.x;
        double dy = head.y - tail.y;
        double dz = head.z - tail.z;

        renderSize = Math.sqrt(dx * dx + dy * dy + dz * dz);
        angleZ = 360 - (Math.atan2(dz, dx) * 180.0 / Math.PI + 180.0);
        dx = Math.sqrt(renderSize * renderSize - dy * dy);
        angleY = -Math.atan2(dy, dx) * 180 / Math.PI;
    }

    protected void updateDataClient()
    {
        head.x = decodeDouble(dataWatcher.getWatchableObjectInt(8));
        head.y = decodeDouble(dataWatcher.getWatchableObjectInt(9));
        head.z = decodeDouble(dataWatcher.getWatchableObjectInt(10));
        tail.x = decodeDouble(dataWatcher.getWatchableObjectInt(11));
        tail.y = decodeDouble(dataWatcher.getWatchableObjectInt(12));
        tail.z = decodeDouble(dataWatcher.getWatchableObjectInt(13));

        isVisible = dataWatcher.getWatchableObjectByte(14) == 1;
    }

    protected void updateDataServer()
    {
        dataWatcher.updateObject(8, Integer.valueOf(encodeDouble(head.x)));
        dataWatcher.updateObject(9, Integer.valueOf(encodeDouble(head.y)));
        dataWatcher.updateObject(10, Integer.valueOf(encodeDouble(head.z)));
        dataWatcher.updateObject(11, Integer.valueOf(encodeDouble(tail.x)));
        dataWatcher.updateObject(12, Integer.valueOf(encodeDouble(tail.y)));
        dataWatcher.updateObject(13, Integer.valueOf(encodeDouble(tail.z)));

        dataWatcher.updateObject(14, Byte.valueOf((byte) (isVisible ? 1 : 0)));
    }

    public void setPositions(Pos3d head, Pos3d tail)
    {
        this.head = head;
        this.tail = tail;

        setPositionAndRotation(head.x, head.y, head.z, 0, 0);

        needsUpdate = true;
    }

    public void show()
    {
        isVisible = true;
        needsUpdate = true;
    }

    public void hide()
    {
        isVisible = false;
        needsUpdate = true;
    }

    public boolean isVisible()
    {
        return isVisible;
    }

    public abstract String getTexture();

    protected int encodeDouble(double d)
    {
        return (int) (d * 8192);
    }

    protected double decodeDouble(int i)
    {
        return i / 8192D;
    }

    // The read/write to nbt seem to be useless
    @Override
    protected void readEntityFromNBT(NBTTagCompound nbt)
    {

        double headX = nbt.getDouble("headX");
        double headY = nbt.getDouble("headZ");
        double headZ = nbt.getDouble("headY");
        head = new Pos3d(headX, headY, headZ);

        double tailX = nbt.getDouble("tailX");
        double tailY = nbt.getDouble("tailZ");
        double tailZ = nbt.getDouble("tailY");
        tail = new Pos3d(tailX, tailY, tailZ);
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound nbt)
    {

        nbt.setDouble("headX", head.x);
        nbt.setDouble("headY", head.y);
        nbt.setDouble("headZ", head.z);

        nbt.setDouble("tailX", tail.x);
        nbt.setDouble("tailY", tail.y);
        nbt.setDouble("tailZ", tail.z);
    }

    // Workaround for the laser's posY loosing it's precision e.g 103.5 becomes
    // 104
    public Pos3d renderOffset()
    {
        return new Pos3d(head.x - posX, head.y - posY, head.z - posZ);
    }
}