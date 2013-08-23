package redmennl.mods.mito.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import redmennl.mods.mito.helper.Pos3;

public class TileCompanionCreator extends TileEntity
{
    public Pos3 tileBasePos;
    
    public void setTileBasePos(Pos3 pos)
    {
        this.tileBasePos = pos;
    }
    
    public Pos3 getTileBasePos()
    {
        return tileBasePos;
    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) 
    {
        super.readFromNBT(nbtTagCompound);
        
        if (nbtTagCompound.hasKey("baseX"))
            tileBasePos = new Pos3(nbtTagCompound.getInteger("baseX"), nbtTagCompound.getInteger("baseY"), nbtTagCompound.getInteger("baseZ"));
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) 
    {
        super.writeToNBT(nbtTagCompound);
        
        if (tileBasePos != null)
        {
            nbtTagCompound.setInteger("baseX", tileBasePos.x);
            nbtTagCompound.setInteger("baseY", tileBasePos.y);
            nbtTagCompound.setInteger("baseZ", tileBasePos.z);
        }
    }
    
    public Pos3 getFurthestBlock()
    {
        if (tileBasePos == null)
            return null;
        Pos3 pos = tileBasePos;
        
        TileCompanionCreatorBase base = (TileCompanionCreatorBase)worldObj.getBlockTileEntity(xCoord - getTileBasePos().x,
                yCoord - getTileBasePos().y, zCoord - getTileBasePos().z);
        if (base != null)
        {
            Pos3 pos2 = base.furthestBlock;
            
            if (pos.x == 0)
            {
                pos.x = pos2.x;
            }
            if (pos.y == 0)
            {
                pos.y = pos2.y;
            }
            if (pos.z == 0)
            {
                pos.z = pos2.z;
            }
        }
        return pos;
    }
}
