package redmennl.mods.mito.client;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import redmennl.mods.mito.MiscTools;
import redmennl.mods.mito.lib.GuiIds;
import redmennl.mods.mito.lib.Library;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;

public class KeybindingHandler extends KeyHandler
{
    public static KeyBinding accept = new KeyBinding("MiscTools accept key", Keyboard.KEY_Y);
    public static KeyBinding decline = new KeyBinding("MiscTools decline key", Keyboard.KEY_N);
    public static KeyBinding other = new KeyBinding("MiscTools other key", Keyboard.KEY_O);
    
    public static KeyBinding[] keyBindings = new KeyBinding[]{accept, decline, other};
    public static boolean[] repeats = new boolean[keyBindings.length];
    
    public KeybindingHandler()
    {
        super(keyBindings, repeats);
    }

    @Override
    public String getLabel()
    {
        return "mitoKeybindings";
    }
    
    @Override
    public void keyDown(EnumSet<TickType> types, KeyBinding kb,
            boolean tickEnd, boolean isRepeat)
    {
        EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
        
        if (player == null || tickEnd) {
            return;
        }
        
        int message = player.getEntityData().getInteger("companionHUD");
        int entityId = player.getEntityData().getInteger("companionID");
        
        if (kb.equals(accept) && message != 0) 
        {
            System.out.println("sound");
            player.playSound(Library.MOD_ID + ":accept", 1.0F, 1.0F);
            player.getEntityData().setInteger("companionHUD", 0);
        }
        else if (kb.equals(decline) && message != 0) 
        {
            player.playSound(Library.MOD_ID + ":decline", 1.0F, 1.0F);
            player.getEntityData().setInteger("companionHUD", 0);
        }
        else if (kb.equals(other) && message == 1) 
        {
            player.playSound(Library.MOD_ID + ":other", 1.0F, 1.0F);
            World world = player.worldObj;
            player.openGui(MiscTools.instance, GuiIds.COMPANION, world,
                    entityId, 0, 0);
            player.getEntityData().setInteger("companionHUD", 0);
        }
    }

    @Override
    public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public EnumSet<TickType> ticks()
    {
        return EnumSet.of(TickType.CLIENT);
    }

}
