package redmennl.mods.mito.client.gui.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeSubscribe;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import redmennl.mods.mito.client.KeybindingHandler;
import redmennl.mods.mito.lib.Library;
import redmennl.mods.mito.lib.Resources;

public class GuiCompanionHUD extends Gui
{
    Minecraft mc;
    EntityPlayer player;
    FontRenderer fontRenderer;
    long timeStart = 0L;
    double animation = 0D;
    boolean animate = true;
    int lastQuestion = 0;
    
    public GuiCompanionHUD(Minecraft mc)
    {
        super();
        
        this.mc = mc;
        this.fontRenderer = mc.fontRenderer;
    }
    @ForgeSubscribe(priority = EventPriority.NORMAL)
    public void onRenderExperienceBar(RenderGameOverlayEvent event)
    {
        this.player = this.mc.thePlayer;
        int xStart = mc.displayWidth / 2 - (int)animation;
        int yStart = mc.displayHeight / 10;
        
        if(player == null || event.isCancelable() || event.type != ElementType.CROSSHAIRS)
        {
            return;
        }
        
        int message = player.getEntityData().getInteger("companionHUD");
        if (message == 0)
        {
            if (timeStart != 0L && animate)
            {
                timeStart = Minecraft.getSystemTime();
            }
            
            if (animation > 0D)
            {
                animate = false;
                drawBackground(xStart, yStart);
                drawQuestion(lastQuestion, xStart, yStart);
                animation = 150D - (double)(Minecraft.getSystemTime() - timeStart) / 0.6D;
            }
            else
            {
                animation = 0D;
                timeStart = 0L;
                animate = true;
            }
            return;
        }
        
        if (timeStart == 0L)
        {
            timeStart = Minecraft.getSystemTime();
            player.playSound(Library.MOD_ID + ":message", 1.0F, 1.0F);
        }
        
        if (animation < 150D)
        {
            animation = (double)(Minecraft.getSystemTime() - timeStart) / 0.6D;
        }
        else
        {
            animation = 150D;
        }
        
        drawBackground(xStart, yStart);
        
        if (message == 1)
        {
            lastQuestion = 1;
            drawQuestion(message, xStart, yStart);
        }
    }
    
    public void drawBackground(int xStart, int yStart)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.func_110577_a(Resources.GUI_COMPANION_HUD);
        this.drawTexturedModalRect(xStart, yStart, 0, 0, 150, 39);
    }
    
    public void drawQuestion(int id, int xStart, int yStart)
    {
        switch(id)
        {
            case 1:
                this.drawTexturedModalRect(xStart + 100, yStart + 9, 0, 39, 22, 22);
                this.drawTexturedModalRect(xStart + 125, yStart + 9, 0, 39, 22, 22);
                
                if ((Minecraft.getSystemTime() % 2000L) < 1000L)
                {
                    this.drawTexturedModalRect(xStart + 100, yStart + 9, 44, 39, 22, 22);
                    this.drawTexturedModalRect(xStart + 125, yStart + 9, 66, 39, 22, 22);
                }
                else
                {
                    fontRenderer.drawString(Keyboard.getKeyName(KeybindingHandler.accept.keyCode),
                            xStart + 108, yStart + 16, 0xFFFFFF);
                    fontRenderer.drawString(Keyboard.getKeyName(KeybindingHandler.decline.keyCode),
                            xStart + 133, yStart + 16, 0xFFFFFF);
                }
                break;
            default:
                break;
        }
    }
}
