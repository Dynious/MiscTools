package redmennl.mods.mito.entity.companion.ai;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.MathHelper;
import redmennl.mods.mito.entity.companion.CompanionGlobalVariables;
import redmennl.mods.mito.entity.companion.EntityCompanion;
import redmennl.mods.mito.helper.InventoryHelper;
import redmennl.mods.mito.helper.Pos3;

public class EntityAIBreakBlock extends EntityAIBase
{
    private EntityCompanion companion;
    private double walkSpeed;
    private PathNavigate petPathfinder;
    private ArrayList<Pos3> foundLogs = new ArrayList<Pos3>();
    private ArrayList<Pos3> unreachableLogs = new ArrayList<Pos3>();
    private Pos3 closestLog;
    private int timesTried = 0;
    private boolean shouldContinue = true;

    public EntityAIBreakBlock(EntityCompanion companion, double walkSpeed)
    {
        this.companion = companion;
        this.walkSpeed = walkSpeed;
        petPathfinder = companion.getNavigator();
        this.setMutexBits(3);
    }

    @Override
    public boolean isInterruptible()
    {
        return false;
    }

    @Override
    public boolean shouldExecute()
    {
        if (companion.getCollectLogs() && companion.owner != null
                && companion.owner.getDistanceSqToEntity(companion) < 5F * 5F
                && isLogNear())
        {
            System.out.println("Checking...");
            return true;
        }
        return false;
    }

    @Override
    public boolean continueExecuting()
    {
        return companion.getCollectLogs() && shouldContinue;
    }

    public void getLogs()
    {
        for (int x = MathHelper.floor_double(companion.posX) - 10; x < MathHelper
                .floor_double(companion.posX) + 10; x++)
        {
            for (int y = MathHelper.floor_double(companion.posY) - 10; y < MathHelper
                    .floor_double(companion.posY) + 10; y++)
            {
                for (int z = MathHelper.floor_double(companion.posZ) - 10; z < MathHelper
                        .floor_double(companion.posZ) + 10; z++)
                {
                    // System.out.println(companion.worldObj.getBlockId(x, y,
                    // z));

                    if (CompanionGlobalVariables.WOOD_LOG_IDS
                            .contains(companion.worldObj.getBlockId(x, y, z)))
                    {
                        boolean add = true;
                        for (Pos3 pos : unreachableLogs)
                        {
                            if (x == pos.x && y == pos.y && z == pos.z)
                            {
                                add = false;
                                break;
                            }

                        }
                        if (add)
                        {
                            foundLogs.add(new Pos3(x, y, z));
                        }
                        add = true;
                    }
                }
            }
        }
    }

    public void clearLeaves()
    {
        for (int x = MathHelper.floor_double(companion.posX - 1); x < MathHelper
                .floor_double(companion.posX) + 2; x++)
        {
            for (int y = MathHelper.floor_double(companion.posY); y < MathHelper
                    .floor_double(companion.posY) + 2; y++)
            {
                for (int z = MathHelper.floor_double(companion.posZ - 1); z < MathHelper
                        .floor_double(companion.posZ) + 2; z++)
                {
                    if (CompanionGlobalVariables.TREE_LEAVES_IDS
                            .contains(companion.worldObj.getBlockId(x, y, z)))
                    {
                        Block log = Block.blocksList[companion.worldObj
                                .getBlockId(x, y, z)];
                        ArrayList<ItemStack> items = log
                                .getBlockDropped(companion.worldObj, x, y, z,
                                        companion.worldObj.getBlockMetadata(x,
                                                y, z), 0);
                        for (ItemStack item : items)
                        {
                            InventoryHelper.addItemStackToInventory(item,
                                    companion);
                        }

                        companion.worldObj.destroyBlock(x, y, z, false);
                    }
                }
            }
        }
    }

    public boolean isLogNear()
    {
        getLogs();

        System.out.println("Update!");

        if (!foundLogs.isEmpty())
        {
            findClosestLog();
            return true;
        }
        return false;
    }

    public void findClosestLog()
    {
        if (foundLogs.isEmpty())
        {
            getLogs();
        }
        Pos3 currentClostestLog = new Pos3(Integer.MAX_VALUE,
                Integer.MAX_VALUE, Integer.MAX_VALUE);
        for (Pos3 pos : foundLogs)
        {
            System.out.println(companion.getDistanceSq(currentClostestLog.x,
                    currentClostestLog.y, currentClostestLog.x)
                    + " new: "
                    + companion.getDistanceSq(pos.x, pos.y, pos.x));
            if (companion.getDistanceSq(pos.x, pos.y, pos.x) < companion
                    .getDistanceSq(currentClostestLog.x, currentClostestLog.y,
                            currentClostestLog.x))
            {
                System.out.println("New value");
                currentClostestLog = pos;
            }
        }
        if (currentClostestLog.x == Integer.MAX_VALUE)
        {
            shouldContinue = false;
        }

        closestLog = currentClostestLog;
    }

    @Override
    public void updateTask()
    {
        if (closestLog == null)
        {
            shouldContinue = false;
            return;
        }

        int x = closestLog.x;
        int y = closestLog.y;
        int z = closestLog.z;
        companion.getLookHelper().setLookPosition(x, y, z, 10.0F,
                companion.getVerticalFaceSpeed());
        System.out.println(x + ":" + y + ":" + z);

        clearLeaves();

        if (companion.getCollectLogs())
        {
            if (companion.getDistanceSq(x, y, z) > 8)
            {
                if (!petPathfinder.tryMoveToXYZ(x + 0.5, y, z + 0.5, walkSpeed)
                        || timesTried > 40)
                {
                    System.out.println("Can't reach log.");
                    unreachableLogs.add(closestLog);
                    foundLogs.remove(closestLog);
                    closestLog = null;
                    timesTried = 0;
                    findClosestLog();
                    return;
                }
                if (MathHelper.floor_double(companion.posX) == x
                        && MathHelper.floor_double(companion.posZ) == z)
                {
                    companion.worldObj.destroyBlock(
                            MathHelper.floor_double(companion.posX),
                            MathHelper.floor_double(companion.posY) + 1,
                            MathHelper.floor_double(companion.posZ), false);
                    companion.posY += 1.1;
                    companion.worldObj.setBlock(x, (int) companion.posY - 1, z,
                            Block.dirt.blockID);
                    timesTried = 0;
                } else
                {
                    timesTried++;
                }
            } else
            {
                Block log = Block.blocksList[companion.worldObj.getBlockId(x,
                        y, z)];
                if (log != null)
                {
                    ArrayList<ItemStack> items = log.getBlockDropped(
                            companion.worldObj, x, y, z,
                            companion.worldObj.getBlockMetadata(x, y, z), 0);
                    for (ItemStack item : items)
                    {
                        InventoryHelper
                                .addItemStackToInventory(item, companion);
                    }
                }

                companion.worldObj.destroyBlock(x, y, z, false);
                foundLogs.remove(closestLog);
                closestLog = null;
                findClosestLog();
            }
        }
    }

    /*
     * public void resetTask() { foundLogs = null; closestLog = null; }
     */
}
