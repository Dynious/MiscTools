package redmennl.mods.mito.lib;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.ResourceLocation;


public class Resources
{
    public static List<String> modelNames = new ArrayList<String>();

    public static final String MODEL_LOCATION = "/assets/mito/models/";

    public static final String MODEL_LETTERS = MODEL_LOCATION + "letters.obj";

    public static final String GUI_SHEET_LOCATION = "/textures/gui/";
    public static final String ENTITY_SHEET_LOCATION = "/textures/entities/";
    public static final String MODEL_SHEET_LOCATION = "/textures/models/";
    public static final String ARMOR_SHEET_LOCATION = "/textures/armor/";

    public static final ResourceLocation MODEL_TEXTURE_PORTABLEHOUSE = new ResourceLocation(
            Library.MOD_ID, ENTITY_SHEET_LOCATION + "portableHouse.png");
    public static final ResourceLocation MODEL_TEXTURE_ADVANCEDPORTABLEHOUSE = new ResourceLocation(
            Library.MOD_ID, ENTITY_SHEET_LOCATION + "advancedPortableHouse.png");
    public static final ResourceLocation MODEL_TEXTURE_LETTER = new ResourceLocation(
            Library.MOD_ID, ENTITY_SHEET_LOCATION + "letter.png");

    public static final ResourceLocation MODEL_TEXTURE_COMPANION_BODY = new ResourceLocation(
            Library.MOD_ID, ENTITY_SHEET_LOCATION + "companionBody.png");
    public static final ResourceLocation MODEL_TEXTURE_COMPANION_ARMS = new ResourceLocation(
            Library.MOD_ID, ENTITY_SHEET_LOCATION + "companionArms.png");
    public static final ResourceLocation MODEL_TEXTURE_COMPANION_WHEEL = new ResourceLocation(
            Library.MOD_ID, ENTITY_SHEET_LOCATION + "companionWheel.png");

    public static final ResourceLocation GUI_PORTABLEHOUSE = new ResourceLocation(
            Library.MOD_ID, GUI_SHEET_LOCATION + "portableHouse.png");
    public static final ResourceLocation GUI_LETTERCONSTRUCTOR = new ResourceLocation(
            Library.MOD_ID, GUI_SHEET_LOCATION + "letterConstructor.png");
    public static final ResourceLocation GUI_COMPANION = new ResourceLocation(
            Library.MOD_ID, GUI_SHEET_LOCATION + "companion.png");

}
