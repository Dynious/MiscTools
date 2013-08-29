package redmennl.mods.mito.entity.companion.addon;

import java.util.ArrayList;

import net.minecraft.inventory.IInventory;
import redmennl.mods.mito.client.gui.GuiCompanion;
import redmennl.mods.mito.entity.companion.EntityCompanion;
import redmennl.mods.mito.inventory.slots.AdvancedSlot;
import redmennl.mods.mito.lib.Resources;
import codechicken.enderstorage.api.EnderStorageManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class AddonEnderChest extends AddonBase
{
    int freq = EnderStorageManager.getFreqFromColours(15, 15, 15);
    String owner = "global";
    IInventory enderInventory;

    public AddonEnderChest(EntityCompanion e)
    {
        super(e);
        texture = Resources.GUI_COMPANION_CHEST;
        enderInventory = (IInventory) EnderStorageManager.instance(companion.worldObj.isRemote).getStorage(owner, freq, "item");
        if (enderInventory != null)
        {
            initSlots();
        }
    }
    
    public void initSlots()
    {
        slots = new ArrayList<AdvancedSlot>();
        
        for (int inventoryRowIndex = 0; inventoryRowIndex < 3; ++inventoryRowIndex)
        {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < 9; ++inventoryColumnIndex)
            {
                slots.add(new AdvancedSlot(enderInventory,
                        inventoryColumnIndex + inventoryRowIndex * 9,
                        8 + inventoryColumnIndex * 18,
                        25 + inventoryRowIndex * 18));
            }
        }
    }
    
    @Override
    public boolean hasGui()
    {
        return true;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void drawBackground(GuiCompanion gui, int xStart, int yStart)
    {
        gui.drawTexturedModalRect(xStart + 7, yStart + 24, 0, 0, 162, 54);
    }
    
    @Override
    public boolean hasInventory()
    {
        return true;
    }
    
    @Override
    public int guiSize()
    {
        return 1;
    }
    
}
