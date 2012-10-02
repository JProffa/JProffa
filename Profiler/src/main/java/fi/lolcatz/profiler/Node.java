package fi.lolcatz.profiler;

import java.util.HashSet;
import java.util.Set;
import org.objectweb.asm.tree.analysis.Frame;

/**
 * A custom made class to represent Frame objects.
 */
public class Node extends Frame {

    /**
     * Set of successors of this node.
     */
    public Set<Node> successors = new HashSet<Node>();
 
    /**
     * The index of the instruction node.
     */
    public int insnIndex;

    public Node(int nLocals, int nStack) {
        super(nLocals, nStack);
    }

    public Node(Frame src) {
        super(src);
    }
}