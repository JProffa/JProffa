package net.jproffa.test;

import net.jproffa.implementations.IntegerImpl;
import net.jproffa.profiler.ClassBlacklist;
import net.jproffa.profiler.Util;
import net.jproffa.testproject.ObjectExample;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ObjectTest {

    IntegerImpl impl;

    @BeforeClass
    public static void classSetup() {
        ClassBlacklist.add(ObjectTest.class);
        // Used to initialize the method, creating objects for the first time causes problems with profiler
        ObjectExample.createPersons(1);
        Util.loadAgent();
    }

    @Before
    public void testSetup() {
        impl = new IntegerImpl();
        impl.setClassName("net.jproffa.testproject.ObjectExample");
    }


    @Test
    public void testObjectsLinear() throws Exception {

        impl.setMethodName("createPersons");

        impl.runStatic(impl.getInput(1000));

        long[] totalCost = new long[5];
        int syote = 1000;

        totalCost[0] = impl.runStatic(impl.getInput(syote));

        for (int i = 1; i < totalCost.length; i++) {
            syote = 2 * syote;
            totalCost[i] = impl.runStatic(impl.getInput(syote));
        }
        printResults("--- testObjectsLinear ---", totalCost);

        assertTrue(totalCost[0] * 2 >= totalCost[1]);
        assertTrue(totalCost[1] * 2 >= totalCost[2]);
        assertTrue(totalCost[2] * 2 >= totalCost[3]);
        assertTrue(totalCost[3] * 2 >= totalCost[4]);

        assertTrue(totalCost[1] > totalCost[0]);
        assertTrue(totalCost[2] > totalCost[1]);
        assertTrue(totalCost[3] > totalCost[2]);
        assertTrue(totalCost[4] > totalCost[3]);

    }

    @Test
    public void testObjectsLinearLarge() throws Exception {

        impl.setMethodName("createPersons");

        impl.runStatic(impl.getInput(1000));

        long[] totalCost = new long[5];
        int syote = 100000;

        totalCost[0] = impl.runStatic(impl.getInput(syote));

        for (int i = 1; i < totalCost.length; i++) {
            syote = 2 * syote;
            totalCost[i] = impl.runStatic(impl.getInput(syote));
        }
        printResults("--- testObjectsLinearLarge ---", totalCost);

        assertTrue(totalCost[0] * 2 >= totalCost[1]);
        assertTrue(totalCost[1] * 2 >= totalCost[2]);
        assertTrue(totalCost[2] * 2 >= totalCost[3]);
        assertTrue(totalCost[3] * 2 >= totalCost[4]);

        assertTrue(totalCost[1] > totalCost[0]);
        assertTrue(totalCost[2] > totalCost[1]);
        assertTrue(totalCost[3] > totalCost[2]);
        assertTrue(totalCost[4] > totalCost[3]);

    }

    @Test
    public void testObjectsLinearHuge() throws Exception {

        impl.setMethodName("createPersons");

        impl.runStatic(impl.getInput(1000));

        long[] totalCost = new long[5];
        int syote = 10000000;

        totalCost[0] = impl.runStatic(impl.getInput(syote));

        for (int i = 1; i < totalCost.length; i++) {
            syote = 2 * syote;
            totalCost[i] = impl.runStatic(impl.getInput(syote));
        }

        printResults("--- testObjectsLinearHuge ---", totalCost);

        assertTrue(totalCost[0] * 2 >= totalCost[1]);
        assertTrue(totalCost[1] * 2 >= totalCost[2]);
        assertTrue(totalCost[2] * 2 >= totalCost[3]);
        assertTrue(totalCost[3] * 2 >= totalCost[4]);


        assertTrue(totalCost[1] > totalCost[0]);
        assertTrue(totalCost[2] > totalCost[1]);
        assertTrue(totalCost[3] > totalCost[2]);
        assertTrue(totalCost[4] > totalCost[3]);

    }

    @Test
    public void testObjectsBehaveDeterministic() throws Exception {
        impl.setMethodName("createPersons");

        impl.runStatic(impl.getInput(500));

        long first = impl.runStatic(500);
        long second = impl.runStatic(500);
        System.out.println("Margin of error: " + impl.getMarginOfError(first));
        assertTrue("Suorituskerrat eivät olleet 50 sisällä toisistaan", first > second - impl.getMarginOfError(first) && first < second + impl.getMarginOfError(first));

    }

    public void printResults(String testname, long[] results) {
        System.out.println("---" + testname + "---");
        int i = 0;
        for (long l : results) {
            i++;
            System.out.println(i + ": " + l);
        }
    }
}
