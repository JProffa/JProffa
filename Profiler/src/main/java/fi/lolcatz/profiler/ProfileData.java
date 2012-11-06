package fi.lolcatz.profiler;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that holds counter information from profiling. Use this class by first calling addBasicBlock command for each
 * basic block that is profiled. After that call initialize() and the public arrays become usable.
 */
public class ProfileData {

    /**
     * Holds information how many times basic block has been called. Indexes are the ones returned by addBasicBlock().
     */
    public static long[] callsToBasicBlock;
    private static int basicBlockAmount = 0;
    /**
     * How costly a basic block is. Naive way to count it is by assuming every bytecodes cost is 1 and multiply by
     * amount of bytecodes in the basic block.
     */
    public static long[] basicBlockCost;
    private static ArrayList<Long> basicBlockCostAccumulator = new ArrayList<Long>();
    private static ArrayList<String> basicBlockDesc = new ArrayList<String>();

    private static Logger logger = Logger.getLogger(ProfileData.class.getName());

    /**
     * Adds basic block with default cost (1).
     * 
     * @return Index of basic block in arrays.
     */
    public static int addBasicBlock() {
        return addBasicBlock(1L);
    }

    /**
     * Adds a new basic block.
     * 
     * @param cost
     *            Cost of the basic block.
     * @return Index of the basic block in the arrays.
     */
    public static int addBasicBlock(long cost) {
        return addBasicBlock(cost, "");
    }

    /**
     * Adds a new basic block with a description.
     * 
     * @param cost
     *            Cost of the basic block.
     * @param desc
     *            Description of the basic block.
     * @return Index of the basic block in the arrays.
     */
    public static int addBasicBlock(long cost, String desc) {
        basicBlockAmount++;
        basicBlockCostAccumulator.add(cost);
        basicBlockDesc.add(desc);
        return basicBlockAmount - 1;
    }

    public static void incrementCallsToBasicBlock(int index) {
        callsToBasicBlock[index] += 1;
    }

    /**
     * Reset callsToBasicBlock arrays elements to 0.
     */
    public static void resetCounters() {
        for (int i = 0; i < callsToBasicBlock.length; i++) {
            callsToBasicBlock[i] = 0L;
        }
    }

    /**
     * Initialize arrays from added basic blocks. Needs to be called before using the arrays.
     */
    public static void initialize() {
        callsToBasicBlock = new long[basicBlockAmount];

        basicBlockCost = new long[basicBlockCostAccumulator.size()];
        for (int i = 0; i < basicBlockCostAccumulator.size(); i++) {
            basicBlockCost[i] = basicBlockCostAccumulator.get(i);
        }
    }

    /**
     * Reset everything. Removes added basic blocks.
     */
    public static void resetBasicBlocks() {
        callsToBasicBlock = null;
        basicBlockCost = null;
        basicBlockAmount = 0;
        basicBlockCostAccumulator = new ArrayList<Long>();
    }

    /**
     * Gets total cost. This is counted by multiplying the amount of calls to a basic block by its cost and adding them
     * together.
     * 
     * @return Total cost of execution.
     */
    public static long getTotalCost() {
        return getTotalCost(null);
    }
    
    /**
     * Gets total cost. This is counted by multiplying the amount of calls to a basic block by its cost and adding them
     * together.
     * 
     * @param classBlacklist List of classnames to be ignored.
     * @return Total cost of execution.
     */
    public static long getTotalCost(List<String> classBlacklist) {
        if (callsToBasicBlock == null) {
            return 0;
        }
        if (classBlacklist == null) classBlacklist = new ArrayList<String>();
        classBlacklist.addAll(ClassBlacklist.getBlacklist());
        long totalCost = 0;

        outer:
        for (int i = 0; i < callsToBasicBlock.length; i++) {
            for (String blacklistedClass : classBlacklist) {
                if (basicBlockDesc.get(i).startsWith(blacklistedClass)) continue outer;
            }
            long calls = callsToBasicBlock[i];
            long cost = basicBlockCost[i];
            totalCost += calls * cost;
        }

        return totalCost;
    }

    /**
     * Print information about every basic block. Prints calls, cost, total cost of basic block and the saved
     * description.
     */
    public static void printBasicBlocksCost() {
        printBasicBlocksCost(true, null);
    }

    /**
     * Print information about every basic block. Prints calls, cost, total cost of basic block and the saved
     * description.
     */
    public static void printBasicBlocksCost(boolean showBlocksWithNoCalls) {
        printBasicBlocksCost(showBlocksWithNoCalls, null);
    }

    /**
     * Print information about basic blocks. Prints calls, cost, total cost of basic block and the saved description.
     *
     * @param showBlocksWithNoCalls
     *            If false, omit printing info about basic blocks that have no calls.
     * @param classBlacklist List of classnames to be ignored.
     */
    public static void printBasicBlocksCost(boolean showBlocksWithNoCalls, List<String> classBlacklist) {
        if (callsToBasicBlock == null) {
            System.out.println("ProfileData hasn't been initialized (no classes loaded).");
            return;
        }
        if (classBlacklist == null) classBlacklist = new ArrayList<String>();
        classBlacklist.addAll(ClassBlacklist.getBlacklist());
        outer:
        for (int i = 0; i < callsToBasicBlock.length; i++) {
            if (!showBlocksWithNoCalls && callsToBasicBlock[i] == 0) continue;
            for (String blacklistedClass : classBlacklist) {
                if (basicBlockDesc.get(i).startsWith(blacklistedClass)) continue outer;
            }
            System.out.printf("%5d: Calls: %4d Cost: %4d Total: %6d Desc: %s",
                    i, callsToBasicBlock[i], basicBlockCost[i], callsToBasicBlock[i] * basicBlockCost[i],
                    basicBlockDesc.get(i));
            System.out.println();

        }
    }

    public static String getBasicBlockCostsString(boolean showUncalledBlocks) {
        if (callsToBasicBlock == null) {
            logger.warn("ProfileData hasn't been initialized (no classes loaded).");
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < callsToBasicBlock.length; i++) {
            if (!showUncalledBlocks && callsToBasicBlock[i] == 0) continue;
            sb.append(String.format("%5d: Calls: %4d Cost: %4d Total: %6d Desc: %s",
                    i, callsToBasicBlock[i], basicBlockCost[i], callsToBasicBlock[i] * basicBlockCost[i],
                    basicBlockDesc.get(i)));
            sb.append(System.getProperty("line.separator"));
        }
        return sb.toString();
    }
}
