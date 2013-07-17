package redmennl.mods.mito.entity.companion.addon.button;

public class ButtonBase
{
    public int xPos;
    public int yPos;
    public int sizeX;
    public int sizeY;
    public String text;

    public ButtonBase(int xPos, int yPos, int sizeX, int sizeY, String text)
    {
        this.xPos = xPos;
        this.yPos = yPos;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.text = text;
    }
}
