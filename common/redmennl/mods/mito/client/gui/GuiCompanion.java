package redmennl.mods.mito.client.gui;

import java.util.ArrayList;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiButtonMerchant;
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

    public GuiButton setOwner;

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
        getAddonTab();
    }

    public void getAddonTab()
    {
        int tab = 2;
        int halfTabSpace = 0;
        int quarterTabSpace = 0;
        for (AddonBase addon : e.getAddons())
        {
            if (addon.hasGui() && addon.getGuiLocation() == 0)
            {
                if (addon.guiSize() == 1)
                {
                    addon.setButtonActionOffset(0);
                    addon.setGuiLocation(tab);
                    tab++;
                }
                if (addon.guiSize() == 2)
                {
                    if (halfTabSpace != 0)
                    {
                        addon.setButtonActionOffset(getOffsetAtTab(halfTabSpace));
                        addon.setGuiLocation(halfTabSpace);
                        halfTabSpace = 0;
                    } else
                    {
                        addon.setButtonActionOffset(0);
                        addon.setGuiLocation(tab);
                        halfTabSpace = tab;
                        tab++;
                    }
                }
                if (addon.guiSize() == 4)
                {
                    if (quarterTabSpace != 0)
                    {
                        addon.setButtonActionOffset(getOffsetAtTab(quarterTabSpace));
                        addon.setGuiLocation(quarterTabSpace);
                        quarterTabSpace = 0;
                    } else if (halfTabSpace != 0)
                    {
                        addon.setButtonActionOffset(getOffsetAtTab(halfTabSpace));
                        addon.setGuiLocation(halfTabSpace);
                        quarterTabSpace = halfTabSpace;
                        halfTabSpace = 0;
                    } else
                    {
                        addon.setButtonActionOffset(0);
                        addon.setGuiLocation(tab);
                        halfTabSpace = tab;
                        quarterTabSpace = tab;
                        tab++;
                    }
                }
            }
        }
        if (e.tabs == 1)
        {
            e.tabs = tab - 1;
        }
    }

    public ArrayList<AddonBase> getAddonsAtTab(int tab)
    {
        ArrayList<AddonBase> addons = new ArrayList<AddonBase>();
        for (AddonBase addon : e.getAddons())
        {
            if (addon.getGuiLocation() == tab)
            {
                addons.add(addon);
            }
        }
        return addons;
    }

    public int getOffsetAtTab(int tab)
    {
        int offset = 0;
        ArrayList<AddonBase> addons = getAddonsAtTab(tab);
        for (AddonBase addon : addons)
        {
            System.out.println("Button size: " + addon.getButtons().size());
            offset += addon.getButtons().size();
        }
        return offset;
    }

    @Override
    public void initGui()
    {
        clearContainerScreen();
        if (e.activeTab == 1 || e.activeTab == 0)
        {
            drawInventoryScreen();
        }
        else
        {
            drawAddonScreen();
        }
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
                    if (e.activeTab != 1)
                    {
                        clearContainerScreen();
                        e.activeTab = 1;
                        drawInventoryScreen();
                    }
                } else if (x >= 30 && x <= 59 && e.tabs >= 2)
                {
                    if (e.activeTab != 2)
                    {
                        clearContainerScreen();
                        e.activeTab = 2;
                        drawAddonScreen();
                    }
                } else if (x >= 60 && x <= 89 && e.tabs >= 3)
                {
                    if (e.activeTab != 3)
                    {
                        clearContainerScreen();
                        e.activeTab = 3;
                        drawAddonScreen();
                    }
                } else if (x >= 90 && x <= 119 && e.tabs >= 4)
                {
                    if (e.activeTab != 4)
                    {
                        clearContainerScreen();
                        e.activeTab = 4;
                        drawAddonScreen();
                    }
                } else if (x >= 120 && x <= 149 && e.tabs >= 5)
                {
                    if (e.activeTab != 5)
                    {
                        clearContainerScreen();
                        e.activeTab = 5;
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
        buttonList.add(new GuiButton(1, width / 2 - 70, height / 2 - 65, 80,
                20, "Change model"));
        buttonList.add(setOwner = new GuiButton(2, width / 2 - 70,
                height / 2 - 40, 80, 20, "Follow"));
        if (e.getStandCloseToPlayer() == true)
        {
            setOwner.displayString = "Follow";
        } else
        {
            setOwner.displayString = "Stop following";
        }
        addInventoryScreen();
    }

    @SuppressWarnings("unchecked")
    public void drawAddonScreen()
    {
        ArrayList<AddonBase> addons = getAddonsAtTab(e.activeTab);
        System.out.println("ID = " + buttonList.size());
        for (AddonBase addon : addons)
        {
            if (addon.hasButtons())
            {
                for (ButtonBase button : addon.getButtons())
                {
                    if (button.type == 0)
                    {
                        buttonList.add(new GuiButton(buttonList.size() + 2, width / 2
                                + button.xPos, height / 2 + button.yPos,
                                button.sizeX, button.sizeY, button.text));
                    }
                    else if (button.type == 1)
                    {
                        buttonList.add(new GuiButtonMerchant(buttonList.size() + 2, width / 2
                                + button.xPos, height / 2 + button.yPos, button.inverted));
                    }
                }
            }

            if (addon.hasInventory())
            {
                addon.setSlotsVisible();
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
        
        if (e.activeTab == 1 && e.isSitting() == true)
        {
            setOwner.displayString = "Follow";
        } else if (e.activeTab == 1 && e.isSitting() == false)
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
            ArrayList<AddonBase> addons = getAddonsAtTab(e.activeTab);
            for (AddonBase addon : addons)
            {
                addon.buttonActions(guibutton.id - (3 + addon.getButtonActionOffset()));
                System.out.println("ButtonId = " + guibutton.id
                        + " ButtonOffset = " + addon.getButtonActionOffset());
            }
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

        // Unselected e.tabs
        this.drawTexturedModalRect(xStart, yStart, 176, 12, 39, 12);
        for (int i = 1; i < e.tabs; i++)
        {
            this.drawTexturedModalRect(xStart + i * 30, yStart, 176, 36, 39, 12);
        }
        this.drawTexturedModalRect(xStart + (e.tabs - 1) * 30, yStart, 176, 36,
                39, 12);

        // Selected tab
        if (e.activeTab == 1)
        {
            this.drawTexturedModalRect(xStart, yStart, 176, 0, 39, 12);
        } else if (e.activeTab == e.tabs)
        {
            this.drawTexturedModalRect(xStart + (e.tabs - 1) * 30, yStart, 176,
                    24, 39, 12);
        } else
        {
            this.drawTexturedModalRect(xStart + (e.activeTab - 1) * 30, yStart,
                    176, 24, 39, 12);
        }

        // Tab contents
        if (e.activeTab == 1)
        {
            this.drawTexturedModalRect(xStart + 115, yStart + 24, 176, 48, 54,
                    54);
        } else
        {
            ArrayList<AddonBase> addons = getAddonsAtTab(e.activeTab);
            for (AddonBase addon : addons)
            {
                if (addon.hasGui())
                {
                    if (addon.texture != null)
                    {
                        mc.renderEngine.func_110577_a(addon.texture);
                    }
                    else
                    {
                        mc.renderEngine.func_110577_a(Resources.GUI_COMPANION);
                    }
                    addon.drawBackground(this, xStart, yStart);
                }
            }
        }
    }
}
