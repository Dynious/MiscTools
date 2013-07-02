package redmennl.mods.mito.lib;

import net.minecraft.client.resources.ResourceLocation;

public class Resources
{
    public static final String MODEL_LOCATION = "/assets/mito/models/";
    
    public static final String MODEL_LETTERS = MODEL_LOCATION + "letters.obj";
    
    private static final String SOUND_PREFIX = "mods.mito.sounds.";
    public static final String SOUND_LOCATION = "mods/mito/sounds/";
    
    public static final String GUI_SHEET_LOCATION = "/textures/gui/";
    public static final String ENTITY_SHEET_LOCATION = "/textures/entities/";
    public static final String MODEL_SHEET_LOCATION = "/textures/models/";
    public static final String ARMOR_SHEET_LOCATION = "/textures/armor/";
    
    public static final ResourceLocation MODEL_TEXTURE_PORTABLEHOUSE = new ResourceLocation("mito", ENTITY_SHEET_LOCATION + "portableHouse.png");
    public static final ResourceLocation MODEL_TEXTURE_ADVANCEDPORTABLEHOUSE = new ResourceLocation("mito", ENTITY_SHEET_LOCATION + "advancedPortableHouse.png");
    public static final ResourceLocation MODEL_TEXTURE_LETTER = new ResourceLocation("mito", ENTITY_SHEET_LOCATION + "letter.png");
    
    
	public static final ResourceLocation MODEL_TEXTURE_COMPANION_BODY = new ResourceLocation("mito", ENTITY_SHEET_LOCATION + "companionBody.png");
	public static final ResourceLocation MODEL_TEXTURE_COMPANION_ARMS = new ResourceLocation("mito", ENTITY_SHEET_LOCATION + "companionArms.png");
	public static final ResourceLocation MODEL_TEXTURE_COMPANION_WHEEL = new ResourceLocation("mito", ENTITY_SHEET_LOCATION + "companionWheel.png");
	
	public static final ResourceLocation GUI_PORTABLEHOUSE = new ResourceLocation("mito", GUI_SHEET_LOCATION + "portableHouse.png");
	public static final ResourceLocation GUI_LETTERCONSTRUCTOR = new ResourceLocation("mito", GUI_SHEET_LOCATION + "letterConstructor.png");
	public static final ResourceLocation GUI_COMPANION = new ResourceLocation("mito", GUI_SHEET_LOCATION + "companion.png");
	
}
