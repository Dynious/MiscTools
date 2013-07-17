package redmennl.mods.mito.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import redmennl.mods.mito.entity.companion.EntityCompanion;
import redmennl.mods.mito.inventory.ContainerCompanionInventory;

public class GuiCompanionInventory extends GuiContainer
{
    EntityCompanion e;
    EntityPlayer p;

    public GuiCompanionInventory(InventoryPlayer i, EntityCompanion e,
            EntityPlayer player)
    {
        super(new ContainerCompanionInventory(i, e));
        xSize = 176;
        ySize = 176;
        this.e = e;
        p = player;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {

    }
}
