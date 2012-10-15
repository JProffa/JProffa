/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.lolcatz.profiler;

import java.util.LinkedList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnNode;

/**
 *
 * @author oorissan
 */
public class AgentTest implements Opcodes {

    public static LinkedList<AbstractInsnNode> oneInsnList;
    public static LinkedList<AbstractInsnNode> fiveInsnList;
    public static LinkedList<AbstractInsnNode> emptyInsnList;

    @BeforeClass
    public static void setUpClass() {
        //Util.loadAgent("/cs/fs/home/oorissan/Desktop/Profiler/Profiler/target/Profiler-1.0-SNAPSHOT.jar");
        Util.loadAgent("target/Profiler-1.0-SNAPSHOT.jar");
        ProfileData.initialize();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Test
    public void testTest() {
        oneInsnList = new LinkedList<AbstractInsnNode>();
        oneInsnList.add(new InsnNode(ICONST_0));
        fiveInsnList = new LinkedList<AbstractInsnNode>();
        fiveInsnList.add(new InsnNode(ICONST_1));
        fiveInsnList.add(new InsnNode(IINC));
        fiveInsnList.add(new InsnNode(NOP));
        fiveInsnList.add(new InsnNode(LCONST_1));
        fiveInsnList.add(new InsnNode(ACONST_NULL));
        emptyInsnList = new LinkedList<AbstractInsnNode>();
        System.out.println(ProfileData.getTotalCost());
    }

}
