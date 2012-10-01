package fi.lolcatz.profiler;

import java.util.ArrayList;

/**
 * Class that holds counter information from profiling. Use this class by first
 * calling addBasicBlock command for each basic block that is profiled. After
 * that call initialize() and the public arrays become usable.
 * 
 */
public class ProfileData {
	/**
	 * Holds information how many times basic block has been called. Indexes are
	 * the ones returned by addBasicBlock().
	 */
	public static long[] callsToBasicBlock;

	private static ArrayList<Long> callsToBasicBlockList = new ArrayList<Long>();
	
	/**
	 * How costly a basic block is. Naive way to count it is by assuming every
	 * bytecodes cost is 1 and multiply by amount of bytecodes in the basic
	 * block.
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
	 * @param cost
	 *            Cost of the basic block.
	 * @return Index of the basic block in the arrays.
	 */
	public static int addBasicBlock(long cost) {
		callsToBasicBlockList.add(0L);
		basicBlockCostList.add(cost);
		return callsToBasicBlockList.size() - 1;
	}

	/**
	 * Reset callsToBasicBlock arrays elements to 0.
	 */
	public static void resetCounters() {
		callsToBasicBlock = new long[callsToBasicBlock.length];
	}

	/**
	 * Initialize arrays from added basic blocks. Needs to be called before
	 * using the arrays.
	 */
	public static void initialize() {
		callsToBasicBlock = new long[callsToBasicBlockList.size()];
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
		callsToBasicBlockList = new ArrayList<Long>();
		basicBlockCostList = new ArrayList<Long>();
	}

	/**
	 * Gets total cost. This is counted by multiplying the amount of calls to a
	 * basic block by its cost and adding them together.
	 * 
	 * @return Total cost of execution.
	 */
	public static long getTotalCost() {
		long totalCost = 0;

		for (int i = 0; i < callsToBasicBlock.length; i++) {
			long calls = callsToBasicBlock[i];
			long cost = basicBlockCost[i];
			totalCost += calls * cost;
		}

		return totalCost;
	}
}
