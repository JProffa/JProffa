package fi.lolcatz.profiler;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnNode;

import java.util.LinkedList;

import static org.junit.Assert.assertTrue;

public class ProfilerTransformerTest implements Opcodes {

    private static LinkedList<AbstractInsnNode> oneInsnList;
    private static LinkedList<AbstractInsnNode> fiveInsnList;
    private static LinkedList<AbstractInsnNode> emptyInsnList;

    private ProfilerTransformer transformer;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        oneInsnList = new LinkedList<AbstractInsnNode>();
        oneInsnList.add(new InsnNode(ICONST_0));
        fiveInsnList = new LinkedList<AbstractInsnNode>();
        fiveInsnList.add(new InsnNode(ICONST_1));
        fiveInsnList.add(new InsnNode(IINC));
        fiveInsnList.add(new InsnNode(NOP));
        fiveInsnList.add(new InsnNode(LCONST_1));
        fiveInsnList.add(new InsnNode(ACONST_NULL));
        emptyInsnList = new LinkedList<AbstractInsnNode>();
    }

    @Before
    public void setUp() throws Exception {
        transformer = new ProfilerTransformer();
    }

    @Test
    public void testCalculateCostEmptyList() {
        long cost = transformer.calculateCost(emptyInsnList);
        assertTrue("Cost of empty instruction list was not 0", cost == 0);
    }

    @Test
    public void testCalculateCostOneInsn() {
        long cost = transformer.calculateCost(oneInsnList);
        assertTrue("Cost of one instruction was not one.", cost == 1);
    }

    @Test
    public void testCalculateCostFiveInsn() {
        long cost = transformer.calculateCost(fiveInsnList);
        assertTrue("Cost of five instruction was not five.", cost == 5);
    }

    // @Test
    // public void testFindBasicBlockBeginnings() {
    // InsnList insns = new InsnList();
    // insns.add(new LabelNode());
    // insns.add(oneInsnList.getFirst());
    // insns.add(new LabelNode());
    // addListToInsnList(insns, fiveInsnList);
    // insns.add(new LabelNode());
    // addListToInsnList(insns, fiveInsnList);
    // insns.add(new LabelNode());
    //
    // insns.resetLabels();
    //
    // assertEquals(3, transformer.findBasicBlockBeginnings(insns).size());
    // }
    //
    // private static void addListToInsnList(InsnList insnList, List<AbstractInsnNode> list) {
    // for (AbstractInsnNode abstractInsnNode : list) {
    // insnList.add(abstractInsnNode);
    // }
    // }

}
