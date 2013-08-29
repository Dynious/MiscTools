package redmennl.mods.mito.tile;

import ic2.api.energy.tile.IEnergySink;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import redmennl.mods.mito.entity.companion.addon.AddonPowerConnector;
import universalelectricity.core.block.IElectrical;
import universalelectricity.core.electricity.ElectricityPack;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.power.PowerHandler.PowerReceiver;

public class TilePowerConnector extends TileEntity implements IPowerReceptor, IEnergySink, IElectrical
{
    private PowerHandler powerHandler;
    private AddonPowerConnector addon;
    
    public TilePowerConnector(AddonPowerConnector addon)
    {
        this.addon = addon;
    }
    public void onPowerChange()
    {
        System.out.println(addon.getStoredPower());
    }
    
    @Override
    public void updateEntity()
    {
        if (worldObj.isRemote) return;
        super.updateEntity();
        
        if (addon == null) worldObj.setBlockToAir(xCoord, yCoord, zCoord);
        
        if (MathHelper.floor_double(addon.getCompanion().posX) != this.xCoord || MathHelper.floor_double(addon.getCompanion().posY) != this.yCoord || MathHelper.floor_double(addon.getCompanion().posZ) != this.zCoord)
        {
            endConnection();
        }
        
        if (this.addon.getStoredPower() < this.addon.getMaxPower())
        {
          float neededPower = (this.addon.getMaxPower() - this.addon.getStoredPower()) / 5.0F;
          if (neededPower < 0.0F) neededPower = 0.0F;
          float usedPower = getPowerHandlerBC().useEnergy(1.0F, Math.min(neededPower, getPowerHandlerBC().getMaxEnergyStored()), true);
          if (usedPower != 0)
          {
              this.addon.addPower(usedPower * 5.0F);
              onPowerChange();
          }
        }
        else
        {
            endConnection();
        }
    }
    
    public void endConnection()
    {
        addon.connnectionEnded();
        worldObj.setBlockToAir(xCoord, yCoord, zCoord);
    }

    @Override
    public PowerReceiver getPowerReceiver(ForgeDirection side)
    {
        return addon.getPowerTypes().contains(AddonPowerConnector.powerType.MJ) ? getPowerHandlerBC().getPowerReceiver() : null;
    }
    
    public PowerHandler getPowerHandlerBC()
    {
        if (this.powerHandler == null)
        {
          this.powerHandler = new PowerHandler(this, PowerHandler.Type.STORAGE);
          if (this.powerHandler != null) {
            this.powerHandler.configure(1.0F, 100.0F, 100.0F, 100.0F);
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
        return addon.getPowerTypes().contains(AddonPowerConnector.powerType.EU) ? true : false;
    }
    
    //
    @Override
    public double demandedEnergyUnits()
    {
        float demandedEU = (this.addon.getMaxPower() - this.addon.getStoredPower()) / 2.0F;
        return demandedEU > 0 ? demandedEU : 0;
    }

    @Override
    public double injectEnergyUnits(ForgeDirection directionFrom, double amount)
    {
        this.addon.addPower((float)amount * 2);
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
        return addon.getPowerTypes().contains(AddonPowerConnector.powerType.WATTS) ? true : false;
    }

    @Override
    public float receiveElectricity(ForgeDirection from,
            ElectricityPack receive, boolean doReceive)
    {
        if (receive == null) return 0.0F;
        
        if (doReceive)
        {
          this.addon.addPower(receive.getWatts() / 0.2F);
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
        float demandedWatts = (this.addon.getMaxPower() - this.addon.getStoredPower() * 0.2F);
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
