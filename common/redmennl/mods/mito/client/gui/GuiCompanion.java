package redmennl.mods.mito.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;

import org.lwjgl.opengl.GL11;

import redmennl.mods.mito.entity.companion.EntityCompanion;
import redmennl.mods.mito.entity.companion.addon.AddonBase;
import redmennl.mods.mito.entity.companion.addon.button.ButtonBase;
import redmennl.mods.mito.inventory.ContainerCompanion;
import redmennl.mods.mito.inventory.slots.AdvancedSlot;
import redmennl.mods.mito.lib.Resources;
import redmennl.mods.mito.network.PacketHandler;
import cpw.mods.fml.common.FMLCommonHandler;

public class GuiCompanion extends GuiAdvancedContainer
{
    EntityCompanion e;
    EntityPlayer p;

    int xStart;
    int yStart;

    private int activeTab;

    public GuiButton setOwner;
    public int tabs;

    private AddonBase currentAddon;

    public GuiCompanion(InventoryPlayer i, EntityCompanion e,
            EntityPlayer player)
    {
        super(new ContainerCompanion(i, e));
        xSize = 176;
        ySize = 176;
        this.e = e;
        p = player;
        xStart = (width - xSize) / 2;
        yStart = (height - ySize) / 2;
        if (e.getAddons() != null)
        {
            tabs = e.getAddons().size() + 1;
        } else
        {
            tabs = 1;
        }

    }

    @Override
    public void initGui()
    {
        clearContainerScreen();
        drawInventoryScreen();
        super.initGui();
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3)
    {
        if (par3 == 0)
        {
            int x = par1 - guiLeft;
            int y = par2 - guiTop;
            if (y <= 11)
            {
                if (x >= 0 && x <= 29)
                {
                    if (activeTab != 1)
                    {
                        clearContainerScreen();
                        drawInventoryScreen();
                    }
                } else if (x >= 30 && x <= 59 && tabs >= 2)
                {
                    if (activeTab != 2)
                    {
                        clearContainerScreen();
                        activeTab = 2;
                        drawAddonScreen();
                    }
                } else if (x >= 60 && x <= 89 && tabs >= 3)
                {
                    if (activeTab != 3)
                    {
                        clearContainerScreen();
                        activeTab = 3;
                        drawAddonScreen();
                    }
                } else if (x >= 90 && x <= 119 && tabs >= 4)
                {
                    if (activeTab != 4)
                    {
                        clearContainerScreen();
                        activeTab = 4;
                        drawAddonScreen();
                    }
                } else if (x >= 120 && x <= 149 && tabs >= 5)
                {
                    if (activeTab != 5)
                    {
                        clearContainerScreen();
                        activeTab = 5;
                        drawAddonScreen();
                    }
                }
            }
        }

        super.mouseClicked(par1, par2, par3);
    }

    @SuppressWarnings("unchecked")
    public void drawInventoryScreen()
    {
        activeTab = 1;
        buttonList.add(new GuiButton(1, width / 2 - 70, height / 2 - 65, 80,
                20, "Change model"));
        buttonList.add(setOwner = new GuiButton(2, width / 2 - 70,
                height / 2 - 40, 80, 20, "Follow"));
        if (e.isSitting() == true)
        {
            setOwner.displayString = "Follow";
        } else if (e.isSitting() == false)
        {
            setOwner.displayString = "Stop following";
        }
        addInventoryScreen();
    }

    @SuppressWarnings("unchecked")
    public void drawAddonScreen()
    {
        currentAddon = e.getAddons().get(activeTab - 2);
        if (currentAddon.hasButtons())
        {
            int i = 3;
            for (ButtonBase button : currentAddon.getButtons())
            {
                buttonList.add(new GuiButton(i, width / 2 + button.xPos, height
                        / 2 + button.yPos, button.sizeX, button.sizeY,
                        button.text));
                i++;
            }
        }

        if (currentAddon.hasInventory())
        {
            for (AdvancedSlot slot : currentAddon.getSlots())
            {
                slot.setVisible();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void clearContainerScreen()
    {
        buttonList.clear();
        buttonList.add(new GuiButton(0, width / 2 + 78, height / 2 - 79, 10,
                10, "X"));

        for (int i = 0; i < e.slotInventory.length; i++)
        {
            e.slotInventory[i].setInvisible();
        }

        if (e.getAddons() != null)
        {
            for (AddonBase addon : e.getAddons())
            {
                if (addon.hasInventory())
                {
                    for (AdvancedSlot slot : addon.getSlots())
                    {
                        slot.setInvisible();
                    }
                }
            }
        }
    }

    public void addInventoryScreen()
    {
        for (int i = 0; i < e.slotInventory.length; i++)
        {
            e.slotInventory[i].setVisible();
        }
    }

    @Override
    public void updateScreen()
    {
        super.updateScreen();

        if (e.isSitting() == true)
        {
            setOwner.displayString = "Follow";
        } else if (e.isSitting() == false)
        {
            setOwner.displayString = "Stop following";
        }
    }

    @Override
    protected void actionPerformed(GuiButton guibutton)
    {
        if (guibutton.id == 0)
        {
            PacketHandler.companionKill(e);
            FMLCommonHandler.instance().showGuiScreen(null);
        }
        if (guibutton.id == 1)
        {
            FMLCommonHandler.instance().showGuiScreen(null);

            int index = Resources.modelNames.indexOf(e.modelName);
            if (index != Resources.modelNames.size() - 1)
            {
                e.modelName = Resources.modelNames.get(index + 1);
            } else
            {
                e.modelName = Resources.modelNames.get(0);
            }
            e.refreshModel();
            e.readModelData();
        }
        if (guibutton.id == 2)
        {
            PacketHandler.companionFollow(e);
        }
        if (guibutton.id > 2)
        {
            currentAddon.buttonActions(guibutton.id - 2);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2,
            int var3)
    {

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.func_110577_a(Resources.GUI_COMPANION);
        xStart = (width - xSize) / 2;
        yStart = (height - ySize) / 2;
        this.drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);

        // Unselected tabs
        this.drawTexturedModalRect(xStart, yStart, 176, 12, 39, 12);
        for (int i = 1; i < tabs; i++)
        {
            this.drawTexturedModalRect(xStart + i * 30, yStart, 176, 36, 39, 12);
        }
        this.drawTexturedModalRect(xStart + (tabs - 1) * 30, yStart, 176, 36,
                39, 12);

        // Selected tab
        if (activeTab == 1)
        {
            this.drawTexturedModalRect(xStart, yStart, 176, 0, 39, 12);
        } else if (activeTab == tabs)
        {
            this.drawTexturedModalRect(xStart + (tabs - 1) * 30, yStart, 176,
                    24, 39, 12);
        } else
        {
            this.drawTexturedModalRect(xStart + (activeTab - 1) * 30, yStart,
                    176, 24, 39, 12);
        }

        // Tab contents
        if (activeTab == 1)
        {
            this.drawTexturedModalRect(xStart + 115, yStart + 24, 176, 48, 54,
                    54);
        } else
        {
            currentAddon.drawBackground(this, xStart, yStart);
        }
    }
}
