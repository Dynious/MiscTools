package redmennl.mods.mito.entity.companion;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class CompanionGlobalVariables
{
    public static ArrayList<ItemStack> WOOD_LOGS;
    public static ArrayList<ItemStack> TREE_LEAVES;
    @SuppressWarnings("rawtypes")
    public static ArrayList WOOD_LOG_IDS = new ArrayList();
    @SuppressWarnings("rawtypes")
    public static ArrayList TREE_LEAVES_IDS = new ArrayList();

    @SuppressWarnings("unchecked")
    public static void initBlocks()
    {
        WOOD_LOGS = OreDictionary.getOres("logWood");
        for (ItemStack item : WOOD_LOGS)
        {
            WOOD_LOG_IDS.add(item.itemID);
        }

        TREE_LEAVES = OreDictionary.getOres("treeLeaves");
        TREE_LEAVES.addAll(OreDictionary.getOres("leavesTree"));
        for (ItemStack item : TREE_LEAVES)
        {
            TREE_LEAVES_IDS.add(item.itemID);
        }
    }
}
