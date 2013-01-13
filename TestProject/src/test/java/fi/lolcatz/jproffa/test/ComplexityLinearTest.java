package fi.lolcatz.jproffa.test;

import fi.lolcatz.jproffa.implementations.IntegerImpl;
import fi.lolcatz.jproffa.testproject.IterativeComplexityExample;
import fi.lolcatz.profiledata.ProfileData;
import fi.lolcatz.profiler.ComplexityAnalysis;
import fi.lolcatz.profiler.Output;
import fi.lolcatz.profiler.WithProfiling;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import org.junit.Rule;

public class ComplexityLinearTest {
    
    @Rule public WithProfiling profiling = WithProfiling.rule();

    private static long unprofiledTime;
    private IntegerImpl impl;

    @BeforeClass
    public static void classSetup() {
        ProfileData.disableProfiling();
        System.out.println("Running unprofiled benchmark");
        long startTime = System.currentTimeMillis();
        IterativeComplexityExample.linearFunction(200000000);
        long endTime = System.currentTimeMillis();
        unprofiledTime = endTime - startTime;
    }

    @Before
    public void testSetup() {
        impl = new IntegerImpl();
        impl.setClassName("fi.lolcatz.jproffa.testproject.IterativeComplexityExample");
    }

    @Test
    public void testLinearNoFramework() throws Exception {
        impl.setMethodName("linearFunction");
        impl.runStatic(impl.getInput(1000));

        long[] totalCost = new long[5];
        int syote = 1000;

        totalCost[0] = impl.runStatic(impl.getInput(syote));

        for (int i = 1; i < totalCost.length; i++) {
            syote = 2 * syote;
            totalCost[i] = impl.runStatic(impl.getInput(syote));
        }

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
    public void testLinearLarge() throws Exception {
        impl.setMethodName("linearFunction");

        List<Integer> list = Arrays.asList(100000, 300000, 200000, 400000, 600000, 500000, 700000, 800000, 900000);
        Output<Integer> o = impl.runMethod(list);
        for (Long l : o.getTime()) {
            assertTrue(l > 0);
        }
        /*assertTrue("isLinearOrFaster()", ComplexityAnalysis.isLinearOrFaster(o, 1.0));
        assertTrue("isQuadraticOrFaster()", ComplexityAnalysis.isQuadraticOrFaster(o, 1.0));
        ComplexityAnalysis.assertLinear("assertLinear", o);*/
System.out.println(o);
        ComplexityAnalysis.assertLinearOrFaster("assertLinear", o, 1.0);
        ComplexityAnalysis.assertQuadraticOrFaster("assertQuadraticOrFaster", o, 1.0);
    }
    
    @Test
    public void testLinearOrFaster() throws Exception {
        impl.setMethodName("linearFunction");

        List<Integer> list = Arrays.asList(1000, 3000, 2000, 4000, 6000, 5000, 7000);
        Output<Integer> o = impl.runMethod(list);
        for (Long l : o.getTime()) {
            assertTrue(l > 0);
        }
        assertTrue("isLinearOrFaster()", ComplexityAnalysis.isLinearOrFaster(o, 1.0));
        assertTrue("isQuadraticOrFaster()", ComplexityAnalysis.isQuadraticOrFaster(o, 1.0));
        ComplexityAnalysis.assertLinear(o);
        ComplexityAnalysis.assertNotQuadratic(o);
    }

    @Test
    public void testGenerateOutput() throws Exception {
        impl.setMethodName("linearFunction");
        List<Integer> list = Arrays.asList(2, 4, 8, 16, 32, 64, 512, 1024, 2048, 4096, 8192, 16384, 10000000, 20000000, 3000000);
        Output<Integer> o = impl.runMethod(list);
        for (Long l : o.getTime()) {
            assertTrue(l > 0);
        }
        assertTrue("isQuadraticOrFaster()", ComplexityAnalysis.isQuadraticOrFaster(o, 1.0));
        assertTrue("isLinearOrFaster()", ComplexityAnalysis.isLinearOrFaster(o, 1.0));
        ComplexityAnalysis.assertLinear("AssertLinear", o);
    }

    @Test
    public void testGenerateOutputWithHugeLeap() throws Exception {
        impl.setMethodName("linearFunction");
        List<Integer> list = Arrays.asList(2, 20, 500, 2000, 10000000, 20000000, 20000000);
        Output<Integer> o = impl.runMethod(list);
        assertTrue("isLinearOrFaster()", ComplexityAnalysis.isLinearOrFaster(o, 1.1));
        ComplexityAnalysis.assertLinear("assertLinear", o);
    }

    @Test
    public void testLinearityOnQuadraticFunction() throws Exception {
        impl.setMethodName("approximatedQuadraticFunction");
        List<Integer> list = Arrays.asList(2, 4, 8, 16, 32, 64);
        Output<Integer> o = impl.runMethod(list);
        for (Long l : o.getTime()) {
            assertTrue(l > 0);
        }
        ComplexityAnalysis.assertNotLinear(o);
        ComplexityAnalysis.assertQuadratic(o);
    }


    @Test
    public void testLinearHUUGE() throws Exception {
        impl.setMethodName("linearFunction");

        List<Integer> list = Arrays.asList(10000000, 30000000, 20000000, 40000000, 60000000, 50000000, 70000000);
        Output<Integer> o = impl.runMethod(list);
        for (Long l : o.getTime()) {
            assertTrue(l > 0);
        }
        assertTrue("isLinearOrFaster()", ComplexityAnalysis.isLinearOrFaster(o, 1.0));
        assertTrue("isQuadraticOrFaster()", ComplexityAnalysis.isQuadraticOrFaster(o, 1.0));
        ComplexityAnalysis.assertLinear(o);
        ComplexityAnalysis.assertNotQuadratic(o);
    }

    /*
     * Note that profiling nullifies many automatic optimizations,
     * but if code is not optimized to begin with (like in the example),
     * its about as fast whether or not you profile it.
     */

    @Test
    public void testProfilingIsFastEnough() {
        long startTime = System.currentTimeMillis();
        IterativeComplexityExample.linearFunction(200000000);
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        System.out.println("Runtime without profiler was " + unprofiledTime + "ms");
        System.out.println("Runtime with profiler was " + totalTime + "ms");

        assertTrue(totalTime < 10 * unprofiledTime);

    }

    @Test
    public void testRecursionLinear() throws Exception {
        impl.setClassName("fi.lolcatz.jproffa.testproject.RecursiveComplexityExample");
        impl.setMethodName("linearRecursive");

        List<Integer> list = Arrays.asList(1000, 2000, 3000, 4000, 6000, 5000, 7000);
        List<Integer> list2 = Arrays.asList(0, 0, 0, 0, 0, 0, 0);
        Output<Integer> o = impl.runMethod(list, list2);
        for (Long l : o.getTime()) {
            assertTrue(l > 0);
        }
        assertTrue("isLinearOrFaster()", ComplexityAnalysis.isLinearOrFaster(o, 1.0));
        assertTrue("isQuadraticOrFaster()", ComplexityAnalysis.isQuadraticOrFaster(o, 1.0));
        ComplexityAnalysis.assertLinear(o);
        ComplexityAnalysis.assertNotQuadratic(o);
    }

}
