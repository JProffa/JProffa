package fi.lolcatz.jproffa.test;

import fi.lolcatz.jproffa.implementations.IntegerImpl;
import fi.lolcatz.profiler.ClassBlacklist;
import fi.lolcatz.profiler.ComplexityAnalysis;
import fi.lolcatz.profiler.Output;
import fi.lolcatz.profiler.Util;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ComplexityQuadraticTest {

    public ComplexityQuadraticTest() {
    }

    IntegerImpl impl;

    @BeforeClass
    public static void setUpClass() {
        ClassBlacklist.add(ComplexityQuadraticTest.class);
        Util.loadAgent();
    }

    @Before
    public void testSetup() {
        impl = new IntegerImpl();
        impl.setClassName("fi.lolcatz.jproffa.testproject.IterativeComplexityExample");
    }


    @Test
    public void quadraticTestNoFramework() throws Exception {

        impl.setMethodName("quadraticFunction");

        long[] totalCost = new long[5];
        int syote = 10;

        totalCost[0] = impl.runStatic(impl.getInput(syote));

        for (int i = 1; i < totalCost.length; i++) {
            syote = 2 * syote;
            totalCost[i] = impl.runStatic(impl.getInput(syote));
        }

        printResults("--- testQuadratic ---", totalCost);

        assertTrue(totalCost[0] * 4 >= totalCost[1]);
        assertTrue(totalCost[1] * 4 >= totalCost[2]);
        assertTrue(totalCost[2] * 4 >= totalCost[3]);
        assertTrue(totalCost[3] * 4 >= totalCost[4]);

        assertTrue(totalCost[1] > totalCost[0]);
        assertTrue(totalCost[2] > totalCost[1]);
        assertTrue(totalCost[3] > totalCost[2]);
        assertTrue(totalCost[4] > totalCost[3]);
        System.out.println("- - - - -");

    }

    @Test
    public void quadraticTestWithFramework() throws Exception {

        impl.setMethodName("quadraticFunction");

        List<Integer> list = Arrays.asList(2, 4, 8, 16, 32, 64, 512, 1024, 2048, 4096, 8192, 16000, 32000);
        Output<Integer> o = impl.runMethod(list);
        for (Long l : o.getTime()) {
            assertTrue(l > 0);
        }
        assertTrue("isQuadraticOrFaster()", ComplexityAnalysis.isQuadraticOrFaster(o, 1.1));
//        assertFalse("isLinearOrFaster()", ComplexityAnalysis.isLinearOrFaster(o, 1.1));
        ComplexityAnalysis.assertQuadratic("assertQuadratic", o);

    }

    @Test
    public void testApproximatedQuadratic() throws Exception {

        impl.setMethodName("approximatedQuadraticFunction");

        List<Integer> list = Arrays.asList(2, 4, 8, 16, 32, 64, 512, 1024, 2048, 4096, 8192, 16000, 32000);
        Output<Integer> o = impl.runMethod(list);
        for (Long l : o.getTime()) {
            assertTrue(l > 0);
        }
        assertTrue("isQuadraticOrFaster()", ComplexityAnalysis.isQuadraticOrFaster(o, 1.1));
//        assertFalse("isLinearOrFaster()", ComplexityAnalysis.isLinearOrFaster(o, 1.1));
        ComplexityAnalysis.assertQuadratic(o);

    }

    @Test
    public void testCoinFlipQuadratic() throws Exception {
        impl.setMethodName("quadraticCoinFlipFunction");

        List<Integer> list = Arrays.asList(2, 4, 8, 16, 32, 64, 512, 1024, 2048, 4096, 8192);
        Output<Integer> o = impl.runMethod(list);
        for (Long l : o.getTime()) {
            assertTrue(l > 0);
        }
        assertTrue("isQuadraticOrFaster()", ComplexityAnalysis.isQuadraticOrFaster(o, 1.1));
        assertFalse("isLinearOrFaster()", ComplexityAnalysis.isLinearOrFaster(o, 1.0));
        ComplexityAnalysis.assertQuadratic(o);
    }

    @Test
    public void testRecursionQuadratic() throws Exception {

        impl.setClassName("fi.lolcatz.jproffa.testproject.RecursiveComplexityExample");
        impl.setMethodName("quadraticRecursive");

        List<Integer> list = Arrays.asList(50, 100, 200, 400, 800, 1600, 3200, 6400);
        List<Integer> list2 = Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        List<Integer> list3 = Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        Output<Integer> o = impl.runMethod(list, list2, list3);
        for (Long l : o.getTime()) {
            assertTrue(l > 0);
        }
        assertTrue("isQuadraticOrFaster()", ComplexityAnalysis.isQuadraticOrFaster(o, 1.1));
        assertFalse("isLinearOrFaster()", ComplexityAnalysis.isLinearOrFaster(o, 1.0));
        ComplexityAnalysis.assertQuadratic(o);
    }

    @Test
    public void testFasterThanQuadratic() throws Exception {
        impl.setMethodName("linearFunction");

        List<Integer> list = Arrays.asList(2, 4, 8, 16, 32, 64, 512, 1024, 2048, 4096, 8192);
        Output<Integer> o = impl.runMethod(list);
        for (Long l : o.getTime()) {
            assertTrue(l > 0);
        }
        assertTrue("isFasterThanQuadratic()", ComplexityAnalysis.isFasterThanQuadratic(o));
        ComplexityAnalysis.assertFasterThanQuadratic(o);
    }
    
    @Test
    public void testNlogNisFasterThanQuadratic() throws Exception {
        impl.setMethodName("approximatedNlogNFunction");
        
        List<Integer> list = Arrays.asList(2,4,8,16,32,64,512,1024,2048,4096,8192);
        Output<Integer> o = impl.runMethod(list);
        for (Long l : o.getTime()){
            assertTrue(l > 0);
        }
        assertTrue("isFasterThanQuadratic()", ComplexityAnalysis.isFasterThanQuadratic(o));
        ComplexityAnalysis.assertFasterThanQuadratic(o);
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
