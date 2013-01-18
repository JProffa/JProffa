package net.jproffa.test;

import net.jproffa.implementations.IntegerImpl;
import net.jproffa.testproject.IterativeComplexityExample;
import net.jproffa.profiledata.ProfileData;
import net.jproffa.profiler.WithProfiling;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;
import org.junit.Rule;

public class ComplexityLinearTest {
    
    @Rule public WithProfiling profiling = WithProfiling.rule();

    private IntegerImpl impl;

    @Before
    public void testSetup() {
        impl = new IntegerImpl();
        impl.setClassName("net.jproffa.testproject.IterativeComplexityExample");
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
    
    //TODO: test many methods with static cost limits

}
