package redmennl.mods.mito.lib;

public class Library
{

    public static final String MOD_ID = "MiTo";
    public static final String MOD_NAME = "Misc Tools";
    public static final String VERSION = "@VERSION@";
    public static final String CHANNEL_NAME = MOD_ID;
    public static final String SERVER_PROXY_CLASS = "redmennl.mods.mito.proxy.CommonProxy";
    public static final String CLIENT_PROXY_CLASS = "redmennl.mods.mito.proxy.ClientProxy";
    public static final int ITEM_ID_OFFSET = 256;
    
    
    public static final String MODEL_LOCATION = "/mods/MiTo/models/";
    
    private static final String SOUND_PREFIX = "mods.MiTo.sounds.";
    public static final String SOUND_LOCATION = "mods/MiTo/sounds/";
    
    public static final String GUI_SHEET_LOCATION = "/mods/MiTo/textures/gui/";
    public static final String ENTITY_SHEET_LOCATION = "/mods/MiTo/textures/entities/";
    public static final String MODEL_SHEET_LOCATION = "/mods/MiTo/textures/models/";
    public static final String ARMOR_SHEET_LOCATION = "/mods/MiTo/textures/armor/";
    
    public static final String GUI_PORTABLEHOUSE = GUI_SHEET_LOCATION + "portableHouse.png";
    public static final String GUI_LETTERCONSTRUCTOR = GUI_SHEET_LOCATION + "letterConstructor.png";
    
    public static final String ENTITY_TEXTURE_LASER = ENTITY_SHEET_LOCATION + "laser_1.png";
    
    public static final String MODEL_TEXTURE_PORTABLEHOUSE = MODEL_SHEET_LOCATION + "portableHouse.png";
    public static final String MODEL_TEXTURE_ADVANCEDPORTABLEHOUSE = MODEL_SHEET_LOCATION + "advancedPortableHouse.png";
    public static final String MODEL_TEXTURE_LETTER = MODEL_SHEET_LOCATION + "letter.png";
    
    public static final String MODEL_LETTERS = MODEL_LOCATION + "letters.obj";
    
    public static String[] soundFiles = { SOUND_LOCATION + "makeLetter.ogg", SOUND_LOCATION + "saveBlocks.ogg" };
    
    public static final String SOUND_LETTERCONSTRUCTOR = SOUND_PREFIX + "makeLetter";
    public static final String SOUND_PORTABLEHOUSE = SOUND_PREFIX + "saveBlocks";
}
