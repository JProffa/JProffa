package fi.lolcatz.profiler;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.List;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.analysis.BasicInterpreter;
import org.objectweb.asm.tree.analysis.Frame;

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
            if (className.startsWith("java/") || className.startsWith("sun/")
                    || className.startsWith("fi/lolcatz/profiler/")) {
                return null;
            }

            System.out.println("Class: " + className);

            ClassNode classNode = Util.initClassNode(classfileBuffer);

            // Add counter increment codes in the beginning of basic blocks
            for (MethodNode methodNode : (List<MethodNode>) classNode.methods) {
                System.out.println("  Method: " + methodNode.name + methodNode.desc);
                InsnList insns = methodNode.instructions;
                Util.printInsnList(insns);

                NodeAnalyzer analyzer = new NodeAnalyzer(new BasicInterpreter());
                // Analyze the method, initializing the frame array
                analyzer.analyze(className, methodNode);

                // Increase max stack size to allow counter increments
                methodNode.maxStack += 6;

                boolean firstNodeFound = false;
                for (Frame frame : analyzer.getFrames()) {
                    Node node = (Node) frame;
                    if (node == null) continue;
                    if (!firstNodeFound) {
                        node.startsNewBasicBlock = true;
                        firstNodeFound = true;
                    }
                    if (node.successors.size() > 1) {
                        for (Node successor : node.successors) {
                            successor.startsNewBasicBlock = true;
                        }
                    }
                }

                int sizeOfBasicBlock = 0;
                Node beginningOfBasicBlock = null;
                for (Frame frame : analyzer.getFrames()) {
                    Node node = (Node) frame;
                    if (node == null) continue;
                    if (!node.startsNewBasicBlock) {
                        sizeOfBasicBlock++;
                        continue;
                    }
                    if (beginningOfBasicBlock == null) {
                        beginningOfBasicBlock = node;
                        sizeOfBasicBlock++;
                        continue;
                    }

                    // Create new basic block counter and get its index
                    int basicBlockIndex = ProfileData.addBasicBlock(sizeOfBasicBlock);

                    // Insert counter increment codes before beginning of the basic block
                    methodNode.instructions.insertBefore(beginningOfBasicBlock.instruction,
                            createCounterIncrementInsnList(basicBlockIndex));
                    beginningOfBasicBlock = node;
                }

                // Last basic block of the method
                int basicBlockIndex = ProfileData.addBasicBlock(sizeOfBasicBlock);
                methodNode.instructions.insertBefore(beginningOfBasicBlock.instruction,
                        createCounterIncrementInsnList(basicBlockIndex));
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
     * Creates new InsnList containing bytecode instructions to increment counter.
     * 
     * @return Counter increment InsnList
     */
    private InsnList createCounterIncrementInsnList(int basicBlockIndex) {
        InsnList counterIncrementInsnList = new InsnList();
        counterIncrementInsnList.add(new FieldInsnNode(GETSTATIC, "fi/lolcatz/profiler/ProfileData",
                "callsToBasicBlock", "[J"));
        counterIncrementInsnList.add(intPushInsn(basicBlockIndex));
        counterIncrementInsnList.add(new InsnNode(DUP2));
        counterIncrementInsnList.add(new InsnNode(LALOAD));
        counterIncrementInsnList.add(new InsnNode(LCONST_1));
        counterIncrementInsnList.add(new InsnNode(LADD));
        counterIncrementInsnList.add(new InsnNode(LASTORE));
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
