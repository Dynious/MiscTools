package redmennl.mods.mito.entity.companion;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import redmennl.mods.mito.MiscTools;
import redmennl.mods.mito.entity.companion.addon.AddonBase;
import redmennl.mods.mito.entity.companion.addon.AddonCrafting;
import redmennl.mods.mito.entity.companion.addon.AddonTest;
import redmennl.mods.mito.entity.companion.ai.EntityAIBreakBlock;
import redmennl.mods.mito.entity.companion.ai.EntityAIStandClose;
import redmennl.mods.mito.inventory.slots.AdvancedSlot;
import redmennl.mods.mito.item.ItemCompanion;
import redmennl.mods.mito.item.ItemRegistery;
import redmennl.mods.mito.lib.GuiIds;
import redmennl.mods.mito.lib.Library;
import redmennl.mods.mito.lib.Resources;

public class EntityCompanion extends EntityTameable implements IInventory
{
    private ItemStack inventory[];
    public int health = 20;
    public EntityLivingBase owner;

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

    public AdvancedSlot slotInventory[] = new AdvancedSlot[9];

    private ArrayList<AddonBase> addons;

    private boolean standCloseToPlayer = true;
    private boolean collectLogs = true;

    public EntityCompanion(World world)
    {
        super(world);
        inventory = new ItemStack[9];
        setEntityHealth(20F);
        // this.owner = owner;
        this.setSize(0.8F, 1.0F);
        this.getNavigator().setAvoidsWater(true);
        readModelData();
        refreshModel();

        tasks.addTask(0, new EntityAIStandClose(this, 0.5D));
        tasks.addTask(1, new EntityAIBreakBlock(this, 0.5D));

        addons = new ArrayList<AddonBase>();
        addons.add(new AddonCrafting(this));
        addons.add(new AddonTest(this));
    }

    public boolean getStandCloseToPlayer()
    {
        return standCloseToPlayer;
    }

    public void toggleStandCloseToPlayer()
    {
        standCloseToPlayer = !standCloseToPlayer;
    }

    public boolean getCollectLogs()
    {
        return collectLogs;
    }

    public void toggleCollectLogs()
    {
        collectLogs = !collectLogs;
    }

    public ArrayList<AddonBase> getAddons()
    {
        return addons;
    }

    @Override
    public boolean interact(EntityPlayer ep)
    {
        owner = ep;

        if (!worldObj.isRemote)
        {

            try
            {
                ep.openGui(MiscTools.instance, GuiIds.COMPANION, worldObj,
                        entityId, 0, 0);
            } catch (Exception e)
            {
                System.out.println("Failed to open GUI");
            }
            return true;
        }
        return super.interact(ep);
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

    public void killCompanion()
    {
        if (!worldObj.isRemote)
        {
            ItemCompanion i = (ItemCompanion) ItemRegistery.companion;
            i.hasLegs = hasLegs;
            i.hasWheel = hasWheel;
            i.modelName = modelName;
            i.model = model;
            ItemStack item = new ItemStack(i);
            EntityItem entity = new EntityItem(worldObj, posX, posY, posZ, item);
            worldObj.spawnEntityInWorld(entity);
            setDead();
        }
    }

    @Override
    public boolean isAIEnabled()
    {
        return true;
    }

    public void refreshModel()
    {
        model = AdvancedModelLoader.loadModel(Resources.MODEL_LOCATION
                + modelName + ".obj");
    }

    @Override
    public EntityAgeable createChild(EntityAgeable entityageable)
    {
        return null;
    }

    @Override
    public int getTalkInterval()
    {
        return 1200;
    }

    @Override
    protected float getSoundPitch()
    {
        return 1F;

    }

    @Override
    protected String getHurtSound()
    {
        return Library.MOD_ID + ":companionhurt";
    }

    @Override
    protected String getLivingSound()
    {
        return Library.MOD_ID + ":companionsay";
    }

    @Override
    protected String getDeathSound()
    {
        return Library.MOD_ID + ":companiondeath";
    }

    @Override
    protected void playStepSound(int par1, int par2, int par3, int par4)
    {
        worldObj.playSoundAtEntity(this, Library.MOD_ID + ":companionwalk",
                0.15F, 1.0F);
    }

    @Override
    public int getSizeInventory()
    {
        int slots = inventory.length;
        if (addons != null)
        {
            for (AddonBase addon : addons)
            {
                slots += addon.getSizeInventory();
            }
        }
        return slots;
    }

    @Override
    public ItemStack getStackInSlot(int i)
    {
        if (i >= slotInventory.length && addons != null)
        {
            for (AddonBase addon : addons)
            {
                if (i < addon.getSizeInventory())
                    return addon.getStackInSlot(i);
                i -= addon.getSizeInventory();
            }
        }
        return inventory[i];
    }

    @Override
    public ItemStack decrStackSize(int i, int j)
    {
        ItemStack itemStack = getStackInSlot(i);
        if (itemStack != null)
        {
            if (itemStack.stackSize <= j)
            {
                setInventorySlotContents(i, null);
            } else
            {
                itemStack = itemStack.splitStack(j);
                if (itemStack.stackSize == 0)
                {
                    setInventorySlotContents(i, null);
                }
            }
        }

        return itemStack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i)
    {
        ItemStack itemStack = getStackInSlot(i);
        if (itemStack != null)
        {
            setInventorySlotContents(i, null);
        }
        return itemStack;

    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemStack)
    {
        if (i >= slotInventory.length && addons != null)
        {
            for (AddonBase addon : addons)
            {
                if (i < addon.getSizeInventory())
                {
                    addon.setInventorySlotContents(i, itemStack);
                }
                i -= addon.getSizeInventory();
            }
        } else
        {
            inventory[i] = itemStack;
            if (itemStack != null
                    && itemStack.stackSize > getInventoryStackLimit())
            {
                itemStack.stackSize = getInventoryStackLimit();
            }
        }
    }

    @Override
    public String getInvName()
    {
        return "container.companion";
    }

    @Override
    public boolean isInvNameLocalized()
    {
        return false;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public void onInventoryChanged()
    {
        for (AddonBase addon : addons)
        {
            addon.onInventoryChanged();
        }
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer)
    {
        return entityplayer
                .getDistanceSq(posX + 0.5D, posY + 0.5D, posZ + 0.5D) <= 64.0D;
    }

    @Override
    public void openChest()
    {
    }

    @Override
    public void closeChest()
    {
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack)
    {
        return true;
    }
}
