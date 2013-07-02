package redmennl.mods.mito.client.audio;

import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;
import redmennl.mods.mito.lib.Library;

public class SoundHandler {

    @ForgeSubscribe
    public void onSoundLoad(SoundLoadEvent event) {

        // For each custom sound file we have defined in Sounds
        for (String soundFile : Library.soundFiles) {
            // Try to add the custom sound file to the pool of sounds
            try {
                event.manager.soundPoolSounds.addSound("CHANGE!!! assets/minecraft/sounds");
            }
            // If we cannot add the custom sound file to the pool, log the exception
            catch (Exception e) {
            	System.out.println("mito had a problem loading sounds! Error: " + e);
            }
        }
    }
}

