package redmennl.mods.mito.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import redmennl.mods.mito.entity.EntityCompanion;

public class EntityAIFollow extends EntityAIBase
{
    private EntityCompanion thePet;
    private Entity theOwner;
    World theWorld;
    private float field_75336_f;
    private PathNavigate petPathfinder;
    private int field_75343_h;
    float maxDist;
    float minDist;
    private boolean field_75344_i;
    public boolean shouldFollow = false;

    public EntityAIFollow(EntityCompanion e, float par2, float par3, float par4)
    {
        thePet = e;
        theWorld = e.worldObj;
        field_75336_f = par2;
        petPathfinder = e.getNavigator();
        minDist = par3;
        maxDist = par4;
        this.setMutexBits(3);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean shouldExecute()
    {
        Entity entity = thePet.getOwner();

        if (entity == null)
            return false;
        else if (thePet.isSitting())
            return false;
        else if (thePet.getDistanceSqToEntity(entity) < minDist * minDist)
            return false;
        else
        {
            theOwner = entity;
            return true;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean continueExecuting()
    {
        System.out.println(shouldFollow);
        return !petPathfinder.noPath()
                && thePet.getDistanceSqToEntity(theOwner) > maxDist * maxDist
                && !thePet.isSitting() && shouldFollow == true;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void startExecuting()
    {
        field_75343_h = 0;
        field_75344_i = thePet.getNavigator().getAvoidsWater();
        thePet.getNavigator().setAvoidsWater(false);
    }

    /**
     * Resets the task
     */
    @Override
    public void resetTask()
    {
        theOwner = null;
        petPathfinder.clearPathEntity();
        thePet.getNavigator().setAvoidsWater(field_75344_i);
    }

    /**
     * Updates the task
     */
    @Override
    public void updateTask()
    {
        thePet.getLookHelper().setLookPositionWithEntity(theOwner, 10.0F,
                thePet.getVerticalFaceSpeed());

        if (!thePet.isSitting())
        {
            if (--field_75343_h <= 0)
            {
                field_75343_h = 10;

                if (!petPathfinder.tryMoveToEntityLiving(theOwner,
                        field_75336_f))
                {
                    if (thePet.getDistanceSqToEntity(theOwner) >= 144.0D)
                    {
                        int i = MathHelper.floor_double(theOwner.posX) - 2;
                        int j = MathHelper.floor_double(theOwner.posZ) - 2;
                        int k = MathHelper
                                .floor_double(theOwner.boundingBox.minY);

                        for (int l = 0; l <= 4; ++l)
                        {
                            for (int i1 = 0; i1 <= 4; ++i1)
                            {
                                if ((l < 1 || i1 < 1 || l > 3 || i1 > 3)
                                        && theWorld
                                                .doesBlockHaveSolidTopSurface(i
                                                        + l, k - 1, j + i1)
                                        && !theWorld.isBlockNormalCube(i + l,
                                                k, j + i1)
                                        && !theWorld.isBlockNormalCube(i + l,
                                                k + 1, j + i1))
                                {
                                    thePet.setLocationAndAngles(i + l + 0.5F,
                                            k, j + i1 + 0.5F,
                                            thePet.rotationYaw,
                                            thePet.rotationPitch);
                                    petPathfinder.clearPathEntity();
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
