package redmennl.mods.mito.item;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import redmennl.mods.mito.MiscTools;
import redmennl.mods.mito.entity.companion.EntityCompanion;
import redmennl.mods.mito.lib.Library;
import redmennl.mods.mito.lib.Resources;

public class ItemCompanion extends ItemMito
{
    public IModelCustom model;
    public String modelName = "companion";
    public boolean hasWheel = false;
    public boolean hasLegs = false;

    public ResourceLocation textureBody;
    public ResourceLocation textureArms;
    public ResourceLocation textureWheel;
    public ResourceLocation textureLegs;

    public ItemCompanion(int id)
    {
        super(id);
        this.setCreativeTab(MiscTools.tabMito);
        this.setUnlocalizedName("companion");

        model = AdvancedModelLoader.loadModel(Resources.MODEL_LOCATION
                + modelName + ".obj");
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

    @Override
    public boolean onItemUse(ItemStack par1ItemStack,
            EntityPlayer par2EntityPlayer, World par3World, int par4, int par5,
            int par6, int par7, float par8, float par9, float par10)
    {
        if (par3World.isRemote)
            return true;
        else
        {
            int i1 = par3World.getBlockId(par4, par5, par6);
            par4 += Facing.offsetsXForSide[par7];
            par5 += Facing.offsetsYForSide[par7];
            par6 += Facing.offsetsZForSide[par7];
            double d0 = 0.0D;

            if (par7 == 1 && Block.blocksList[i1] != null
                    && Block.blocksList[i1].getRenderType() == 11)
            {
                d0 = 0.5D;
            }

            Entity entity = spawnCompanion(par3World, par4 + 0.5D, par5 + d0,
                    par6 + 0.5D, par2EntityPlayer);

            if (entity != null)
            {
                if (entity instanceof EntityLivingBase
                        && par1ItemStack.hasDisplayName())
                {
                    ((EntityLiving) entity).setCustomNameTag(par1ItemStack
                            .getDisplayName());
                }

                if (!par2EntityPlayer.capabilities.isCreativeMode)
                {
                    --par1ItemStack.stackSize;
                }
            }

            return true;
        }
    }

    public Entity spawnCompanion(World par0World, double par2, double par4,
            double par6, EntityPlayer par2EntityPlayer)
    {
        EntityCompanion entity = new EntityCompanion(par0World);
        entity.setOwner(par2EntityPlayer);

        if (entity != null && entity instanceof EntityLivingBase)
        {
            entity.setLocationAndAngles(par2, par4, par6, MathHelper
                    .wrapAngleTo180_float(par0World.rand.nextFloat() * 360.0F),
                    0.0F);
            entity.rotationYawHead = entity.rotationYaw;
            entity.renderYawOffset = entity.rotationYaw;
            entity.func_110161_a((EntityLivingData) null);
            par0World.spawnEntityInWorld(entity);
        }
        return entity;
    }
}
