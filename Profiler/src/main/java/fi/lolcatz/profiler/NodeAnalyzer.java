package fi.lolcatz.profiler;

import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.analysis.Analyzer;
import org.objectweb.asm.tree.analysis.AnalyzerException;
import org.objectweb.asm.tree.analysis.Frame;
import org.objectweb.asm.tree.analysis.Interpreter;

public class NodeAnalyzer extends Analyzer {
    
    private MethodNode methodNode;

    public NodeAnalyzer(final Interpreter interpreter) {
        super(interpreter);
    }
    
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
     * Overrides the method to display edges as successors of a node and store instruction.
     * @Param src source node
     * @Param dst destination node
     */
    @Override
    protected void newControlFlowEdge(int src, int dst) {
        Node s = (Node) getFrames()[src];
        s.instruction = methodNode.instructions.get(src);
        Node successor = (Node) getFrames()[dst];
        successor.instruction = methodNode.instructions.get(dst);
        s.successors.add(successor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Frame[] analyze(String owner, MethodNode methodNode) throws AnalyzerException {
        this.methodNode = methodNode;
        return super.analyze(owner, methodNode);
    }
    
    
}
