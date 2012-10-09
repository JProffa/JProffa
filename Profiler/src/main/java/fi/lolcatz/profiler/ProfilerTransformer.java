package fi.lolcatz.profiler;

import static org.objectweb.asm.tree.AbstractInsnNode.*;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

/**
 * Transformer that inserts counter increment code in the beginning of basic blocks.
 */
public class ProfilerTransformer implements ClassFileTransformer, Opcodes {

    /**
     * Transform class using tree API. asm4-guide.pdf pg. 96 {@inheritDoc}
     */
    @Override
    public byte[] transform(
            ClassLoader loader,
            String className,
            Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain,
            byte[] classfileBuffer) throws IllegalClassFormatException {

        try {
            // Don't touch internal classes for now.
            if (className.startsWith("java/") || className.startsWith("sun/") || className.startsWith("com/sun/")
                    || className.startsWith("fi/lolcatz/profiler/")) {
                return null;
            }

            System.out.println("Class: " + className);

            ClassNode classNode = Util.initClassNode(classfileBuffer);

            // Add counter increment codes in the beginning of basic blocks
            for (MethodNode methodNode : (List<MethodNode>) classNode.methods) {
                //System.out.println("  Method: " + methodNode.name + methodNode.desc);
                InsnList insns = methodNode.instructions;
                //Util.printInsnList(insns);

                // Increase max stack size to allow counter increments
                methodNode.maxStack += 6;

                ArrayList<LinkedList<AbstractInsnNode>> basicBlocks = findBasicBlockBeginnings(insns);

                findBasicBlockInsns(basicBlocks);

                for (LinkedList<AbstractInsnNode> basicBlockInsns : basicBlocks) {
                    long cost = calculateCost(basicBlockInsns);
                    int index = ProfileData.addBasicBlock(cost, className + "." + methodNode.name + methodNode.desc);
                    insns.insert(basicBlockInsns.getFirst(), createCounterIncrementInsnList(index));
                }
            }

            byte[] bytecode = Util.generateBytecode(classNode);
            String filename = className.substring(className.lastIndexOf('/') + 1);
            Util.writeByteArrayToFile(filename + ".class", bytecode);

            // Initialize counter arrays
            ProfileData.initialize();

            return bytecode;
        } catch (Exception e) { // Catch all exceptions because they are silenced otherwise.
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Calculate total cost of bytecode instructions in given list.
     *
     * @param basicBlockInsns List of AbstractInsnNode to calculate cost from.
     * @return Total cost of instructions.
     */
    public long calculateCost(LinkedList<AbstractInsnNode> basicBlockInsns) {
        long cost = 0;
        for (AbstractInsnNode node : basicBlockInsns) {
            int type = node.getType();
            switch (type) {
                case LABEL:
                case LINE:
                case FRAME:
                    break;
                default:
                    cost += 1;
            }
        }
        return cost;
    }

    /**
     * Add instruction nodes in a basic block to the list that corresponds to that basic block.
     *
     * @param basicBlocks List of basic block instruction lists that have been initializes with the instruction node
     * that starts a new basic block.
     */
    public void findBasicBlockInsns(ArrayList<LinkedList<AbstractInsnNode>> basicBlocks) {
        for (LinkedList<AbstractInsnNode> basicBlockInsns : basicBlocks) {
            AbstractInsnNode start = basicBlockInsns.getFirst();
            while (start.getNext() != null && start.getNext().getType() != LABEL) {
                AbstractInsnNode next = start.getNext();
                basicBlockInsns.add(next);
                start = next;
            }
        }
    }

    /**
     * Find instruction nodes that start a new basic block. Every LabelNode is assumed to start a new basic block.
     *
     * @param insns List of every instruction node in a method.
     * @return List of basic block instruction lists. Each list corresponds to one basic block. These lists have the
     * starting LabelNode as the first and only element.
     */
    public ArrayList<LinkedList<AbstractInsnNode>> findBasicBlockBeginnings(InsnList insns) {
        ArrayList<LinkedList<AbstractInsnNode>> basicBlocks = new ArrayList<LinkedList<AbstractInsnNode>>();

        for (Iterator<AbstractInsnNode> iter = insns.iterator(); iter.hasNext();) {
            AbstractInsnNode insn = iter.next();
            if (insn.getType() == LABEL && insn.getNext() != null) {
                LinkedList<AbstractInsnNode> blockInsns = new LinkedList<AbstractInsnNode>();
                basicBlocks.add(blockInsns);
                blockInsns.add(insn);
            }
        }
        return basicBlocks;
    }

    /**
     * Creates new InsnList containing bytecode instructions to increment counter.
     *
     * @return Counter increment InsnList
     */
    private InsnList createCounterIncrementInsnList(int basicBlockIndex) {
        InsnList counterIncrementInsnList = new InsnList();
        counterIncrementInsnList.add(intPushInsn(basicBlockIndex));
        counterIncrementInsnList.add(new MethodInsnNode(INVOKESTATIC, "fi/lolcatz/profiler/ProfileData",
                "incrementCallsToBasicBlock", "(I)V"));
        return counterIncrementInsnList;
    }

    /**
     * Creates an instruction that can be used to push any Int value to stack.
     *
     * @param i Int to push to stack.
     * @return Instruction to push <code>i</code> to stack.
     */
    private AbstractInsnNode intPushInsn(int i) {
        if (i < 128) {
            return new IntInsnNode(BIPUSH, i);
        } else if (i < 32768) {
            return new IntInsnNode(SIPUSH, i);
        } else {
            return new LdcInsnNode(i);
        }
    }
}
