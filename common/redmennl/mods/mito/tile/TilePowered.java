package redmennl.mods.mito.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.power.PowerHandler.PowerReceiver;

public class TilePowered extends TileEntity implements IPowerReceptor
{
    private PowerHandler PowerHandlerBC;

    @Override
    public PowerReceiver getPowerReceiver(ForgeDirection side)
    {
        return getPowerHandlerBC().getPowerReceiver();
    }
    
    public PowerHandler getPowerHandlerBC()
    {
      if (this.PowerHandlerBC == null)
      {
        this.PowerHandlerBC = new PowerHandler(this, PowerHandler.Type.STORAGE);
        if (this.PowerHandlerBC != null) {
          this.PowerHandlerBC.configure(1.0F, 320.0F, 800.0F, 640.0F);
        }
      }
      return this.PowerHandlerBC;
    }

    @Override
    public void doWork(PowerHandler workProvider)
    {
    }

    @Override
    public World getWorld()
    {
        return this.worldObj;
    }

}
