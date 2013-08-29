package redmennl.mods.mito.entity.companion.addon;

import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.item.ItemStack;
import redmennl.mods.mito.entity.companion.EntityCompanion;
import redmennl.mods.mito.inventory.slots.PhantomSlot;
import redmennl.mods.mito.inventory.slots.SlotUntouchable;

public class AddonCraftingMk2 extends AddonCrafting
{
    
    public AddonCraftingMk2(EntityCompanion e)
    {
        super(e);
        inventory = new ItemStack[20];
        maxTab = 1;
    }
    
    @Override
    public void addSlots()
    {
        super.addSlots();
        craftMatrices.add(new LocalInventoryCrafting(companion, 1));
        craftResult.add(new InventoryCraftResult());
        slotCrafting.add(new PhantomSlot[9]);
        slotCraftingResult.add(new SlotUntouchable(craftResult.get(1), 0, 98, 43));
        inventory = new ItemStack[20];
    }

}
