package redmennl.mods.mito.entity.companion.addon;

import java.util.ArrayList;

import redmennl.mods.mito.entity.companion.EntityCompanion;
import redmennl.mods.mito.entity.companion.addon.button.ButtonBase;

public class AddonTest extends AddonBase
{

    public AddonTest(EntityCompanion e, int addonId)
    {
        super(e, addonId);
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
        buttons.add(new ButtonBase(0, -80, 80, 80, "TEST ME!"));
    }

    @Override
    public int guiSize()
    {
        return 1;
    }

}
