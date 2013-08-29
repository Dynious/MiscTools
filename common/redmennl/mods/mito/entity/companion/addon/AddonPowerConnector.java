package redmennl.mods.mito.entity.companion.addon;

import java.util.ArrayList;

import net.minecraft.util.MathHelper;
import redmennl.mods.mito.entity.companion.EntityCompanion;
import redmennl.mods.mito.entity.companion.addon.button.ButtonBase;
import redmennl.mods.mito.helper.Pos3;
import redmennl.mods.mito.lib.BlockIds;
import redmennl.mods.mito.network.PacketHandler;
import redmennl.mods.mito.tile.TilePowerConnector;

public class AddonPowerConnector extends AddonBase
{
    private float maxPower = 50000;
    private float storedPower = 0;
    private ArrayList<powerType> powerTypes;

    public AddonPowerConnector(EntityCompanion e, ArrayList<powerType> powerTypes)
    {
        super(e);
        this.powerTypes = powerTypes;
        initButtons();
    }
    
    public float getStoredPower()
    {
        return storedPower;
    }
    
    public float getMaxPower()
    {
        return maxPower;
    }
    
    public float addPower(float power)
    {
        float returnedPower = 0;
        storedPower += power;
        if (storedPower > maxPower)
        {
            returnedPower = storedPower - maxPower;
            storedPower = maxPower;
        }
        return returnedPower;
    }
    
    public ArrayList<powerType> getPowerTypes()
    {
        return powerTypes;
    }
    
    public void createConnection()
    {
        Pos3 connectionBlock = new Pos3(MathHelper.floor_double(companion.posX), MathHelper.floor_double(companion.posY), MathHelper.floor_double(companion.posZ));
        companion.worldObj.setBlock(connectionBlock.x, connectionBlock.y, connectionBlock.z, BlockIds.POWERCONNECTOR);
        companion.worldObj.setBlockTileEntity(connectionBlock.x, connectionBlock.y, connectionBlock.z, new TilePowerConnector(this));
        System.out.println(companion.worldObj.getBlockTileEntity(connectionBlock.x, connectionBlock.y, connectionBlock.z));
    }
    
    public void connnectionEnded()
    {
        
    }
    
    public enum powerType
    {
        MJ, EU, WATTS
    }
    
    @Override
    public boolean hasGui()
    {
        return true;
    }
    
    @Override
    public int guiSize()
    {
        return 4;
    }
    
    @Override
    public boolean hasButtons()
    {
        return true;
    }
    
    public void initButtons()
    {
        buttons = new ArrayList<ButtonBase>();
        
        buttons.add(new ButtonBase(0, -80, 40, 20, "Deploy"));
    }
    
    public void buttonActions(int buttonid)
    {
        switch(buttonid)
        {
            case 0:
                PacketHandler.companionDeployConnector(getCompanion());
        }
    }
}
