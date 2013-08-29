package redmennl.mods.mito.tile;

import ic2.api.energy.tile.IEnergySink;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import universalelectricity.core.block.IElectrical;
import universalelectricity.core.electricity.ElectricityPack;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.power.PowerHandler.PowerReceiver;

public class TilePowered extends TileEntity implements IPowerReceptor, IEnergySink, IElectrical
{
    private PowerHandler powerHandler;
    
    public void onPowerChange()
    {
    }

    @Override
    public PowerReceiver getPowerReceiver(ForgeDirection side)
    {
        return getPowerHandlerBC().getPowerReceiver();
    }
    
    public PowerHandler getPowerHandlerBC()
    {
      if (this.powerHandler == null)
      {
        this.powerHandler = new PowerHandler(this, PowerHandler.Type.STORAGE);
        if (this.powerHandler != null) {
          this.powerHandler.configure(1.0F, 320.0F, 800.0F, 640.0F);
        }
      }
      return this.powerHandler;
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

    @Override
    public boolean acceptsEnergyFrom(TileEntity emitter,
            ForgeDirection direction)
    {
        return true;
    }

    @Override
    public double demandedEnergyUnits()
    {
        float demandedEU = (this.powerHandler.getMaxEnergyStored() - this.powerHandler.getEnergyStored()) * 2.5F;
        return demandedEU > 0 ? demandedEU : 0;
    }

    @Override
    public double injectEnergyUnits(ForgeDirection directionFrom, double amount)
    {
        this.powerHandler.addEnergy((float)amount / 0.4F);
        onPowerChange();
        return 0;
    }

    @Override
    public int getMaxSafeInput()
    {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean canConnect(ForgeDirection direction)
    {
        return true;
    }

    @Override
    public float receiveElectricity(ForgeDirection from,
            ElectricityPack receive, boolean doReceive)
    {
        if (receive == null) return 0.0F;
        
        if (doReceive)
        {
          this.powerHandler.addEnergy(receive.getWatts());
          onPowerChange();
        }

        return receive.getWatts();
    }

    @Override
    public ElectricityPack provideElectricity(ForgeDirection from,
            ElectricityPack request, boolean doProvide)
    {
        return null;
    }

    @Override
    public float getRequest(ForgeDirection direction)
    {
        float demandedWatts = (this.powerHandler.getMaxEnergyStored() - this.powerHandler.getEnergyStored());
        return demandedWatts > 0 ? demandedWatts : 0;
    }

    @Override
    public float getProvide(ForgeDirection direction)
    {
        return 0;
    }

    @Override
    public float getVoltage()
    {
        return 120.0F;
    }
}
