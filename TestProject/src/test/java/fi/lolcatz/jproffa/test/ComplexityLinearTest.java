package fi.lolcatz.jproffa.test;

import fi.lolcatz.jproffa.implementations.IntegerImpl;
import fi.lolcatz.jproffa.testproject.IterativeComplexityExample;
import fi.lolcatz.profiledata.ProfileData;
import fi.lolcatz.profiler.analysis.ComplexityChecks;
import fi.lolcatz.profiler.Output;
import fi.lolcatz.profiler.WithProfiling;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import org.junit.Rule;

public class ComplexityLinearTest {
    
    @Rule public WithProfiling profiling = WithProfiling.rule();

    private IntegerImpl impl;

    @Before
    public void testSetup() {
        impl = new IntegerImpl();
        impl.setClassName("fi.lolcatz.jproffa.testproject.IterativeComplexityExample");
    }

    private void assertSpeed(Output<?> o) {
        for (long l : o.getTime()) {
            assertTrue(l > 0);
        }
        ComplexityChecks.assertLinearOrFaster(o);
        ComplexityChecks.assertNlogNOrFaster(o);
        ComplexityChecks.assertQuadraticOrFaster(o);
    }

    @Test
    public void testLinearNoFramework() throws Exception {
        impl.setMethodName("linearFunction");
        impl.runStatic(impl.getInput(1000));

        long[] totalCost = new long[5];
        int input = 1000;

        totalCost[0] = impl.runStatic(impl.getInput(input));

        for (int i = 1; i < totalCost.length; i++) {
            input = 2 * input;
            totalCost[i] = impl.runStatic(impl.getInput(input));
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
    public void testLinearSmall() throws Exception {
        impl.setMethodName("linearFunction");

        List<Integer> list = Arrays.asList(1000, 3000, 2000, 4000, 6000, 5000, 7000);
        Output<Integer> o = impl.runMethod(list);
        assertSpeed(o);
    }
    
    @Test
    public void testLinearLarge() throws Exception {
        impl.setMethodName("linearFunction");

        List<Integer> list = Arrays.asList(100000, 300000, 200000, 400000, 600000, 500000, 700000, 800000, 900000);
        Output<Integer> o = impl.runMethod(list);
        
        assertSpeed(o);
    }

    @Test
    public void testExponentialInputGrowth() throws Exception {
        impl.setMethodName("linearFunction");
        List<Integer> list = Arrays.asList(2, 4, 8, 16, 32, 64, 512, 1024, 2048, 4096, 8192, 16384, 10000000, 20000000, 3000000);
        Output<Integer> o = impl.runMethod(list);
        //FIXME: nlogn fails
        assertSpeed(o);
    }

    @Test
    public void testGenerateOutputWithHugeLeap() throws Exception {
        impl.setMethodName("linearFunction");
        List<Integer> list = Arrays.asList(2, 20, 500, 2000, 10000000, 20000000, 20000000);
        Output<Integer> o = impl.runMethod(list);
        assertSpeed(o);
    }

    @Test
    public void testLinearityOnQuadraticFunction() throws Exception {
        impl.setMethodName("approximatedQuadraticFunction");
        List<Integer> list = Arrays.asList(2, 4, 8, 16, 32, 64);
        Output<Integer> o = impl.runMethod(list);
        assertFalse(ComplexityChecks.isLinearOrFaster(o, ComplexityChecks.DEFAULT_TOLERANCE));
    }


    @Test
    public void testLinearHUUGE() throws Exception {
        impl.setMethodName("linearFunction");

        List<Integer> list = Arrays.asList(10000000, 30000000, 20000000, 40000000, 60000000, 50000000, 70000000);
        Output<Integer> o = impl.runMethod(list);
        assertSpeed(o);
    }

    /*
     * Note that profiling nullifies many automatic optimizations,
     * but if code is not optimized to begin with (like in the example),
     * its about as fast whether or not you profile it.
     */

    @Test
    public void testProfilingIsFastEnough() {
        ProfileData.disableProfiling();
        long startTime = System.currentTimeMillis();
        IterativeComplexityExample.linearFunction(20000000);
        long endTime = System.currentTimeMillis();
        long unprofiledTime = endTime - startTime;
        
        startTime = System.currentTimeMillis();
        IterativeComplexityExample.linearFunction(20000000);
        endTime = System.currentTimeMillis();
        long profiledTime = endTime - startTime;

        System.out.println("Runtime without profiler was " + unprofiledTime + "ms");
        System.out.println("Runtime with profiler was " + profiledTime + "ms");

        assertTrue(profiledTime < 10 * unprofiledTime);
    }

    @Test
    public void testRecursionLinear() throws Exception {
        impl.setClassName("fi.lolcatz.jproffa.testproject.RecursiveComplexityExample");
        impl.setMethodName("linearRecursive");

        List<Integer> list = Arrays.asList(1000, 2000, 3000, 4000, 6000, 5000, 7000);
        List<Integer> list2 = Arrays.asList(0, 0, 0, 0, 0, 0, 0);
        Output<Integer> o = impl.runMethod(list, list2);
        assertSpeed(o);
    }

}
