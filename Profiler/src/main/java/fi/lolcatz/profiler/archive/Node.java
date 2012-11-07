package fi.lolcatz.profiler.archive;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.analysis.Frame;

import java.util.HashSet;
import java.util.Set;

/**
 * A custom made class to represent Frame objects.
 */
public class Node extends Frame {

    /**
     * Set of successors of this node.
     */
    public Set<Node> successors = new HashSet<Node>();
 
    /**
     * The instruction node.
     */
    public AbstractInsnNode instruction;
    
    /**
     * Does this node start a new basic block.
     */
    public boolean startsNewBasicBlock = false;

    public Node(int nLocals, int nStack) {
        super(nLocals, nStack);
    }

    public Node(Frame src) {
        super(src);
    }
}
