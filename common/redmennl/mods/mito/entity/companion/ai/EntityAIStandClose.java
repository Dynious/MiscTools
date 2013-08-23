package redmennl.mods.mito.entity.companion.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.MathHelper;
import redmennl.mods.mito.entity.companion.EntityCompanion;

public class EntityAIStandClose extends EntityAIBase
{
    private EntityCompanion companion;
    private EntityLivingBase target;
    private double walkSpeed;
    private PathNavigate petPathfinder;

    public EntityAIStandClose(EntityCompanion companion, double walkSpeed)
    {
        this.companion = companion;
        this.walkSpeed = walkSpeed;
        petPathfinder = companion.getNavigator();
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute()
    {

        if (companion.getStandCloseToPlayer() && companion.getOwner() != null
                && companion.getOwner().getDistanceSqToEntity(companion) > 5F * 5F)
        {
            target = companion.getOwner();
            return true;
        }
        return false;
    }

    @Override
    public boolean continueExecuting()
    {
        return !petPathfinder.noPath() && companion.getStandCloseToPlayer()
                && target.getDistanceSqToEntity(companion) > 5F * 5F;
    }

    @Override
    public void updateTask()
    {
        companion.getLookHelper().setLookPositionWithEntity(target, 10.0F,
                companion.getVerticalFaceSpeed());

        if (companion.getStandCloseToPlayer())
        {
            if (!petPathfinder.tryMoveToEntityLiving(target, walkSpeed))
            {
                if (!companion.func_110167_bD())
                {
                    if (companion.getDistanceSqToEntity(target) >= 144.0D)
                    {
                        int i = MathHelper.floor_double(target.posX) - 2;
                        int j = MathHelper.floor_double(target.posZ) - 2;
                        int k = MathHelper
                                .floor_double(target.boundingBox.minY);

                        for (int l = 0; l <= 4; ++l)
                        {
                            for (int i1 = 0; i1 <= 4; ++i1)
                            {
                                if ((l < 1 || i1 < 1 || l > 3 || i1 > 3)
                                        && companion.worldObj
                                                .doesBlockHaveSolidTopSurface(i
                                                        + l, k - 1, j + i1)
                                        && !companion.worldObj
                                                .isBlockNormalCube(i + l, k, j
                                                        + i1)
                                        && !companion.worldObj
                                                .isBlockNormalCube(i + l,
                                                        k + 1, j + i1))
                                {
                                    companion.setLocationAndAngles(
                                            i + l + 0.5F, k, j + i1 + 0.5F,
                                            companion.rotationYaw,
                                            companion.rotationPitch);
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
