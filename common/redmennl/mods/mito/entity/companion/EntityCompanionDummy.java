package redmennl.mods.mito.entity.companion;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import redmennl.mods.mito.lib.Library;
import redmennl.mods.mito.lib.Resources;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class EntityCompanionDummy extends EntityTameable
{
    public String modelName = "companion";
    public IModelCustom model;

    public float modelOffsetY;
    public float armOffsetY;
    public boolean hasWheel = false;
    public float wheelOffsetY;
    public boolean hasLegs = false;
    public float legOffsetY;

    public ResourceLocation textureBody;
    public ResourceLocation textureArms;
    public ResourceLocation textureWheel;
    public ResourceLocation textureLegs;

    public EntityCompanionDummy(World par1World)
    {
        super(par1World);
        
        readModelData();
        refreshModel();
        if (!(this instanceof EntityCompanion))
        {
            this.setSize(0.0F, 0.0F);
            this.posX = Double.MAX_VALUE;
            this.posY = -1;
            this.posZ = Double.MAX_VALUE;
        }
    }
    
    @Override
    public boolean interact(EntityPlayer ep)
    {
        return false;
    }
    
    @Override
    public boolean isAIEnabled()
    {
        return false;
    }
    
    public void readModelData()
    {
        URL url = this.getClass().getResource(
                Resources.MODEL_LOCATION + modelName + ".properties");

        BufferedReader reader = null;
        InputStream inputStream = null;

        String currentLine = null;
        try
        {
            inputStream = url.openStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            while ((currentLine = reader.readLine()) != null)
            {
                currentLine = currentLine.replaceAll("\\s+", " ").trim();

                if (currentLine.startsWith("#") || currentLine.startsWith("//")
                        || currentLine.length() == 0)
                {
                    continue;
                } else if (currentLine.startsWith("modelOffsetY:"))
                {
                    modelOffsetY = Float.parseFloat(currentLine
                            .substring(currentLine.indexOf(":") + 1));
                } else if (currentLine.startsWith("armOffsetY:"))
                {
                    armOffsetY = Float.parseFloat(currentLine
                            .substring(currentLine.indexOf(":") + 1));
                } else if (currentLine.startsWith("hasWheel:"))
                {
                    if (currentLine.substring(currentLine.indexOf(":") + 1)
                            .startsWith("true"))
                    {
                        hasWheel = true;
                        wheelOffsetY = Float.parseFloat(currentLine
                                .substring(currentLine.indexOf("true:") + 5));
                    }
                } else if (currentLine.startsWith("hasLegs:"))
                {
                    if (currentLine.substring(currentLine.indexOf(":") + 1)
                            .startsWith("true"))
                    {
                        hasWheel = true;
                        legOffsetY = Float.parseFloat(currentLine
                                .substring(currentLine.indexOf("true:") + 5));
                    }
                }
            }
        } catch (Exception e)
        {
            System.out.println("Error when reading model data: " + e);
        } finally
        {
            try
            {
                reader.close();
            } catch (Exception e)
            {
            }

            try
            {
                inputStream.close();
            } catch (Exception e)
            {
            }
        }

        textureBody = new ResourceLocation(Library.MOD_ID,
                Resources.ENTITY_SHEET_LOCATION + modelName + "Body.png");
        textureArms = new ResourceLocation(Library.MOD_ID,
                Resources.ENTITY_SHEET_LOCATION + modelName + "Arms.png");
        if (hasWheel)
        {
            textureWheel = new ResourceLocation(Library.MOD_ID,
                    Resources.ENTITY_SHEET_LOCATION + modelName + "Wheel.png");
        }
        if (hasLegs)
        {
            textureLegs = new ResourceLocation(Library.MOD_ID,
                    Resources.ENTITY_SHEET_LOCATION + modelName + "Legs.png");
        }
    }
    
    public void refreshModel()
    {
        model = AdvancedModelLoader.loadModel(Resources.MODEL_LOCATION
                + modelName + ".obj");
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {}

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {}

    @Override
    public EntityAgeable createChild(EntityAgeable entityageable)
    {
        return null;
    }

}
