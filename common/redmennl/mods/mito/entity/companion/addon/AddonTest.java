package redmennl.mods.mito.entity.companion.addon;

import java.util.ArrayList;

import redmennl.mods.mito.entity.companion.EntityCompanion;
import redmennl.mods.mito.entity.companion.addon.button.ButtonBase;
import redmennl.mods.mito.network.PacketHandler;

public class AddonTest extends AddonBase
{

    public AddonTest(EntityCompanion e)
    {
        super(e);
        initButtons();
    }

    @Override
    public boolean hasGui()
    {
        return true;
    }

    @Override
    public boolean hasInventory()
    {
        return false;
    }

    @Override
    public boolean hasButtons()
    {
        return true;
    }

    private void initButtons()
    {
        buttons = new ArrayList<ButtonBase>();
        buttons.add(new ButtonBase(-80, -80, 80, 20, "Toggle woodcutting"));
    }

    @Override
    public void buttonActions(int buttonid)
    {
        if (buttonid == 0)
        {
            PacketHandler.companionCutWood(getCompanion());
        }
    }

    @Override
    public int guiSize()
    {
        return 4;
    }

}
