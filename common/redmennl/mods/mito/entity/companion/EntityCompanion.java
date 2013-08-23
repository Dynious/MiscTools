package redmennl.mods.mito.entity.companion;

import java.util.ArrayList;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import redmennl.mods.mito.MiscTools;
import redmennl.mods.mito.entity.companion.addon.AddonBase;
import redmennl.mods.mito.entity.companion.addon.AddonCraftingMk2;
import redmennl.mods.mito.entity.companion.addon.AddonTest;
import redmennl.mods.mito.entity.companion.ai.EntityAIBreakBlock;
import redmennl.mods.mito.entity.companion.ai.EntityAIStandClose;
import redmennl.mods.mito.inventory.slots.AdvancedSlot;
import redmennl.mods.mito.item.ItemCompanion;
import redmennl.mods.mito.item.ItemRegistery;
import redmennl.mods.mito.lib.GuiIds;
import redmennl.mods.mito.lib.Library;

public class EntityCompanion extends EntityCompanionDummy implements IInventory
{
    private ItemStack inventory[];
    public int health = 20;
    private EntityPlayer owner;

    public AdvancedSlot slotInventory[] = new AdvancedSlot[9];

    private ArrayList<AddonBase> addons = new ArrayList<AddonBase>();;

    private boolean standCloseToPlayer = true;
    private boolean collectLogs = false;

    public EntityCompanion(World world)
    {
        super(world);
        inventory = new ItemStack[9];
        setEntityHealth(20F);
        this.setSize(0.8F, 1.0F);
        
        this.getNavigator().setAvoidsWater(true);

        tasks.addTask(0, new EntityAIStandClose(this, 0.5D));
        tasks.addTask(1, new EntityAIBreakBlock(this, 0.5D));

        addons.add(new AddonCraftingMk2(this));
        addons.add(new AddonTest(this));
    }
    
    public void setOwner(EntityPlayer owner)
    {
        this.owner = owner;
    }
    
    public EntityPlayer getOwner()
    {
        return owner;
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
        setOwner(ep);
        if (getOwner().getEntityData().getInteger("companionHUD") == 0)
        {
            getOwner().getEntityData().setInteger("companionHUD", 1);
            getOwner().getEntityData().setInteger("companionID", this.entityId);
        }
        else
        {
            getOwner().getEntityData().setInteger("companionHUD", 0);
        }
        System.out.println(getOwner().getEntityData().getInteger("companionHUD"));
        
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
