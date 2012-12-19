package fi.lolcatz.profiler.archive;


import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
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
public class CyclomaticComplexityMethodVisitor extends MethodVisitor implements Opcodes {

    String owner;
    MethodVisitor next;

    public CyclomaticComplexityMethodVisitor(String owner, int access, String name, String desc, MethodVisitor mv) {
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
     * A class for counting the cyclomatic complexity of a method. ASM pdf 8.2.5.
     */
    public class CyclomaticComplexity {

        public int getCyclomaticComplexity(String owner, MethodNode mn)
                throws AnalyzerException {
            NodeAnalyzer a = new NodeAnalyzer(new BasicInterpreter());
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

            Frame[] frames = a.getFrames();

            // The transitions between frames
            int edges = 0;
            // Amount of frames
            int nodes = 0;
            for (Frame frame : frames) {
                if (frame != null) {
                    Node node = ((Node) frame);
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
                                    successor.instruction,
                                    counterIncreaseInsn);
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
