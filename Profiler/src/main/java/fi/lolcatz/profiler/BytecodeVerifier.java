/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.lolcatz.profiler;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.MethodNode;
import static org.objectweb.asm.Opcodes.*;
import org.objectweb.asm.tree.analysis.Analyzer;
import org.objectweb.asm.tree.analysis.AnalyzerException;
import org.objectweb.asm.tree.analysis.BasicInterpreter;
import org.objectweb.asm.tree.analysis.BasicValue;
import org.objectweb.asm.tree.analysis.Frame;
import org.objectweb.asm.tree.analysis.Value;

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
        //Attempt to count and print the cyclomatic complexity of the current method.
        try {
            System.out.println(cc.getCyclomaticComplexity(owner, mn));
        } catch (AnalyzerException ex) {
            System.err.println("Problem counting cyclomatic complexity");
        }
        mn.accept(next);
    }

    /*
     * A custom made class to represent Frame objects.
     */
    public class Node extends Frame {

        Set<Node> successors = new HashSet<Node>();

        public Node(int nLocals, int nStack) {
            super(nLocals, nStack);
        }

        public Node(Frame src) {
            super(src);
        }
    }

    /*
     * A class for counting the cyclomatic complexity of a method. ASM pdf 8.2.5.
     */
    public class CyclomaticComplexity {

        public int getCyclomaticComplexity(String owner, MethodNode mn)
                throws AnalyzerException {
            Analyzer a =
                    new Analyzer(new BasicInterpreter()) {
                        /*
                         * Constructs the control flow graph by representing Frames as Nodes.
                         */
                        protected Frame newFrame(int nLocals, int nStack) {
                            return new Node(nLocals, nStack);
                        }

                        protected Frame newFrame(
                                Frame src) {
                            return new Node(src);
                        }
                        /*
                         * Overrides the method to display edges as successors of a node.
                         * @Param src source node
                         * @Param dst destination node
                         */

                        protected void newControlFlowEdge(int src, int dst) {
                            Node s = (Node) getFrames()[src];
                            s.successors.add((Node) getFrames()[dst]);

                        }
                    };
            //Analyze the method, initializing the frame array
            a.analyze(owner, mn);
            Frame[] frames = a.getFrames();
            //The transitions between frames
            int edges = 0;
            //Amount of frames
            int nodes = 0;
            for (int i = 0; i < frames.length; ++i) {
                if (frames[i] != null) {
                    edges += ((Node) frames[i]).successors.size();
                    for (Node n : ((Node) frames[i]).successors) {
                        //for nodes with two successors or the start node, add a counter.
                    }
                    nodes += 1;
                }
            }
            System.out.println("Edges: " + edges);
            System.out.println("Nodes: " + nodes);
            return edges - nodes + 2;
        }
    }
}
