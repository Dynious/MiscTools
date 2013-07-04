package redmennl.mods.mito.lib;

public class Library
{

    public static final String MOD_ID = "mito";
    public static final String MOD_NAME = "Misc Tools";
    public static final String VERSION = "@VERSION@";
    public static final String CHANNEL_NAME = MOD_ID;
    public static final String SERVER_PROXY_CLASS = "redmennl.mods.mito.proxy.CommonProxy";
    public static final String CLIENT_PROXY_CLASS = "redmennl.mods.mito.proxy.ClientProxy";
    public static final int ITEM_ID_OFFSET = 256;

    public static final String MODEL_LOCATION = "/mods/mito/models/";

    private static final String SOUND_PREFIX = "mods.mito.sounds.";
    public static final String SOUND_LOCATION = "mods/mito/sounds/";

    public static final String GUI_SHEET_LOCATION = "/mods/mito/textures/gui/";
    public static final String ENTITY_SHEET_LOCATION = "/mods/mito/textures/entities/";
    public static final String MODEL_SHEET_LOCATION = "/mods/mito/textures/models/";
    public static final String ARMOR_SHEET_LOCATION = "/mods/mito/textures/armor/";

    public static final String GUI_PORTABLEHOUSE = GUI_SHEET_LOCATION
            + "portableHouse.png";
    public static final String GUI_LETTERCONSTRUCTOR = GUI_SHEET_LOCATION
            + "letterConstructor.png";
    public static final String GUI_COMPANION = GUI_SHEET_LOCATION
            + "companion.png";

    public static final String ENTITY_TEXTURE_LASER = ENTITY_SHEET_LOCATION
            + "laser_1.png";

    public static final String MODEL_TEXTURE_PORTABLEHOUSE = MODEL_SHEET_LOCATION
            + "portableHouse.png";
    public static final String MODEL_TEXTURE_ADVANCEDPORTABLEHOUSE = MODEL_SHEET_LOCATION
            + "advancedPortableHouse.png";
    public static final String MODEL_TEXTURE_LETTER = MODEL_SHEET_LOCATION
            + "letter.png";
    public static final String MODEL_TEXTURE_COMPANION_BODY = MODEL_SHEET_LOCATION
            + "companionBody.png";
    public static final String MODEL_TEXTURE_COMPANION_ARMS = MODEL_SHEET_LOCATION
            + "companionArms.png";
    public static final String MODEL_TEXTURE_COMPANION_EYE = MODEL_SHEET_LOCATION
            + "companionEye.png";
    public static final String MODEL_TEXTURE_COMPANION_EYELID = MODEL_SHEET_LOCATION
            + "companionEyelid.png";
    public static final String MODEL_TEXTURE_COMPANION_WHEEL = MODEL_SHEET_LOCATION
            + "companionWheel.png";

    public static final String MODEL_LETTERS = MODEL_LOCATION + "letters.obj";

    public static String[] soundFiles = { "makeLetter.ogg", "saveBlocks.ogg",
            "companionHurt1.ogg", "companionHurt2.ogg", "companionHurt3.ogg",
            "companionHurt4.ogg", "companionHurt5.ogg", "companionHurt6.ogg",
            "companionSay1.ogg", "companionSay2.ogg", "companionSay3.ogg",
            "companionSay4.ogg", "companionSay5.ogg", "companionDeath.ogg",
            "companionWalk.ogg" };

    public static final String SOUND_LETTERCONSTRUCTOR = SOUND_PREFIX
            + "makeLetter";
    public static final String SOUND_PORTABLEHOUSE = SOUND_PREFIX
            + "saveBlocks";
    public static final String SOUND_COMPANION_HURT = SOUND_PREFIX
            + "companionHurt";
    public static final String SOUND_COMPANION_SAY = SOUND_PREFIX
            + "companionSay";
    public static final String SOUND_COMPANION_DEATH = SOUND_PREFIX
            + "companionDeath";
    public static final String SOUND_COMPANION_WALK = SOUND_PREFIX
            + "companionWalk";
}
