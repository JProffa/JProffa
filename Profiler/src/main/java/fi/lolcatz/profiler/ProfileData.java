package fi.lolcatz.profiler;

import java.util.ArrayList;

/**
 * Class that holds counter information from profiling. Use this class by first calling addBasicBlock command for each
 * basic block that is profiled. After that call initialize() and the public arrays become usable.
 * 
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

    private static ArrayList<Long> basicBlockCostList = new ArrayList<Long>();

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
     * @param cost Cost of the basic block.
     * @return Index of the basic block in the arrays.
     */
    public static int addBasicBlock(long cost) {
        basicBlockAmount++;
        basicBlockCostList.add(cost);
        return basicBlockAmount - 1;
    }
    
    public static void incrementCallsToBasicBlock(int index) {
        callsToBasicBlock[index] += 1;
    }

    /**
     * Reset callsToBasicBlock arrays elements to 0.
     */
    public static void resetCounters() {
        callsToBasicBlock = new long[callsToBasicBlock.length];
    }

    /**
     * Initialize arrays from added basic blocks. Needs to be called before using the arrays.
     */
    public static void initialize() {
        callsToBasicBlock = new long[basicBlockAmount];
        basicBlockCost = new long[basicBlockCostList.size()];
        for (int i = 0; i < basicBlockCostList.size(); i++) {
            basicBlockCost[i] = basicBlockCostList.get(i);
        }
    }

    /**
     * Reset everything. Removes added basic blocks.
     */
    public static void resetBasicBlocks() {
        callsToBasicBlock = null;
        basicBlockCost = null;
        basicBlockAmount = 0;
        basicBlockCostList = new ArrayList<Long>();
    }

    /**
     * Gets total cost. This is counted by multiplying the amount of calls to a basic block by its cost and adding them
     * together.
     * 
     * @return Total cost of execution.
     */
    public static long getTotalCost() {
        if (callsToBasicBlock == null) {
            return 0;
        }
        long totalCost = 0;

        for (int i = 0; i < callsToBasicBlock.length; i++) {
            long calls = callsToBasicBlock[i];
            long cost = basicBlockCost[i];
            totalCost += calls * cost;
        }

        return totalCost;
    }

    public static void printBasicBlocksCost() {
        if (callsToBasicBlock == null) {
            System.out.println("ProfileData hasn't been initialized (no classes loaded).");
            return;
        }
        for (int i = 0; i < callsToBasicBlock.length; i++) {
            System.out.println(i + ": Calls: " + callsToBasicBlock[i] + " Cost: " + basicBlockCost[i] + " Total: "
                    + callsToBasicBlock[i] * basicBlockCost[i]);
        }
    }
}
