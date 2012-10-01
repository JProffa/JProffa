package fi.lolcatz.profiler;

import static org.objectweb.asm.Opcodes.*;

import java.util.HashSet;
import java.util.Set;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.analysis.Analyzer;
import org.objectweb.asm.tree.analysis.AnalyzerException;
import org.objectweb.asm.tree.analysis.BasicInterpreter;
import org.objectweb.asm.tree.analysis.Frame;

/**
 * 
 * @author oorissan
 */
public class BytecodeVerifier extends MethodVisitor {

    String owner;
    MethodVisitor next;

    public BytecodeVerifier(String owner, int access, String name, String desc, MethodVisitor mv) {
        super(ASM4, new MethodNode(access, name, desc, null, null));
        this.owner = owner;
        next = mv;
    }

    /**
     * Creates a new cyclomatic complexity counter for each method {@inheritDoc}
     */
    @Override
    public void visitEnd() {
        System.out.println("BytecodeVerifier:...");
        MethodNode mn = (MethodNode) mv;
        Analyzer a = new Analyzer(new BasicInterpreter());
        CyclomaticComplexity cc = new CyclomaticComplexity();
        // Attempt to count and print the cyclomatic complexity of the current method.
        try {
            System.out.println("CyclomaticComplexity: " + cc.getCyclomaticComplexity(owner, mn));
        } catch (AnalyzerException ex) {
            System.err.println("Problem counting cyclomatic complexity");
        }

        mn.accept(next);
    }

    /**
     * A custom made class to represent Frame objects.
     */
    public class Node extends Frame {

        Set<Node> successors = new HashSet<Node>();

        /**
         * The index of the instruction node.
         */
        int insnIndex;

        public Node(int nLocals, int nStack) {
            super(nLocals, nStack);
        }

        public Node(Frame src) {
            super(src);
        }
    }

    /**
     * A class for counting the cyclomatic complexity of a method. ASM pdf 8.2.5.
     */
    public class CyclomaticComplexity {

        public int getCyclomaticComplexity(String owner, MethodNode mn)
                throws AnalyzerException {
            Analyzer a = new Analyzer(new BasicInterpreter()) {

                /**
                 * Constructs the control flow graph by representing Frames as Nodes.
                 */
                @Override
                protected Frame newFrame(int nLocals, int nStack) {
                    return new Node(nLocals, nStack);
                }

                @Override
                protected Frame newFrame(Frame src) {
                    return new Node(src);
                }

                /*
                 * Overrides the method to display edges as successors of a node and store instruction index.
                 * @Param src source node
                 * @Param dst destination node
                 */
                @Override
                protected void newControlFlowEdge(int src, int dst) {
                    Node s = (Node) getFrames()[src];
                    Node successor = (Node) getFrames()[dst];
                    successor.insnIndex = src;
                    s.successors.add(successor);

                }
            };
            // Analyze the method, initializing the frame array
            a.analyze(owner, mn);
            mn.maxStack += 4;

            // Add counter increment in the beginning of the method
            InsnList counterIncreaseInsn = new InsnList();
            counterIncreaseInsn.add(new FieldInsnNode(GETSTATIC, "com/mycompany/example/Example", "counter", "J"));
            counterIncreaseInsn.add(new InsnNode(LCONST_1));
            counterIncreaseInsn.add(new InsnNode(LADD));
            counterIncreaseInsn.add(new FieldInsnNode(PUTSTATIC, "com/mycompany/example/Example", "counter", "J"));
            mn.instructions.insert(counterIncreaseInsn);
            int numOfInsertedIncrementors = 1;

            Frame[] frames = a.getFrames();

            // The transitions between frames
            int edges = 0;
            // Amount of frames
            int nodes = 0;
            for (int i = 0; i < frames.length; ++i) {
                if (frames[i] != null) {
                    Node node = ((Node) frames[i]);
                    edges += node.successors.size();
                    if (node.successors.size() > 1) {
                        System.out.println("New basic block found with " + node.successors.size() + " successors.");
                        for (Node successor : node.successors) {
                            counterIncreaseInsn = new InsnList();
                            counterIncreaseInsn.add(new FieldInsnNode(GETSTATIC, "com/mycompany/example/Example",
                                    "counter", "J"));
                            counterIncreaseInsn.add(new InsnNode(LCONST_1));
                            counterIncreaseInsn.add(new InsnNode(LADD));
                            counterIncreaseInsn.add(new FieldInsnNode(PUTSTATIC, "com/mycompany/example/Example",
                                    "counter", "J"));
                            mn.instructions.insertBefore(
                                    mn.instructions.get(successor.insnIndex + numOfInsertedIncrementors * 8),
                                    counterIncreaseInsn);
                            numOfInsertedIncrementors++;
                        }
                    }
                    nodes += 1;
                }
            }
            // System.out.println("Edges: " + edges);
            // System.out.println("Nodes: " + nodes);
            return edges - nodes + 2;
        }
    }
}
