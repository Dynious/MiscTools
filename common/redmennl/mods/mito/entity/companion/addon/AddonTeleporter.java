package redmennl.mods.mito.entity.companion.addon;

import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import redmennl.mods.mito.entity.companion.EntityCompanion;
import redmennl.mods.mito.entity.companion.addon.button.ButtonBase;
import redmennl.mods.mito.inventory.slots.AdvancedSlot;
import redmennl.mods.mito.inventory.slots.RestrictedSlot;
import cpw.mods.fml.common.registry.GameRegistry;

public class AddonTeleporter extends AddonBase
{
    public RestrictedSlot slot;

    public AddonTeleporter(EntityCompanion e)
    {
        super(e);
        inventory = new ItemStack[1];
        initSlots();
        initButtons();
    }
    
    public void initSlots()
    {
        Item linkingBook = GameRegistry.findItem("Mystcraft", "myst.linkbook");
        if (linkingBook != null)
        {
            slots = new ArrayList<AdvancedSlot>();
            slots.add(slot = new RestrictedSlot(companion, 0, 0, 0, linkingBook.itemID));
        }
    }
    
    public void initButtons()
    {
        buttons = new ArrayList<ButtonBase>();
        
        buttons.add(new ButtonBase(40, -65, 40, 20, "Teleport"));
    }
    
    public boolean hasButtons()
    {
        return true;
    }

    public void buttonActions(int buttonid)
    {
        if (buttonid == 0 && inventory[0] != null)
        {
            ItemStack linkingBook = inventory[0];
            NBTTagCompound nbt = linkingBook.stackTagCompound;
            if (nbt != null)
            {
                int xPos = nbt.getInteger("SpawnX");
                int yPos = nbt.getInteger("SpawnY");
                int zPos = nbt.getInteger("SpawnZ");
                
            }
        }
    }
    
    @Override
    public boolean hasGui()
    {
        return true;
    }
    
    @Override
    public int guiSize()
    {
        return 1;
    }
    
    @Override
    public boolean hasInventory()
    {
        return true;
    }
}
