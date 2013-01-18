package fi.lolcatz.jproffa.test;

import fi.lolcatz.jproffa.implementations.IntegerImpl;
import fi.lolcatz.profiler.WithProfiling;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import org.junit.Rule;

public class ComplexityQuadraticTest {
    
    @Rule public WithProfiling profiling = WithProfiling.rule();

    private IntegerImpl impl;

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

        assertTrue(totalCost[0] * 4 >= totalCost[1]);
        assertTrue(totalCost[1] * 4 >= totalCost[2]);
        assertTrue(totalCost[2] * 4 >= totalCost[3]);
        assertTrue(totalCost[3] * 4 >= totalCost[4]);

        assertTrue(totalCost[1] > totalCost[0]);
        assertTrue(totalCost[2] > totalCost[1]);
        assertTrue(totalCost[3] > totalCost[2]);
        assertTrue(totalCost[4] > totalCost[3]);
    }
    
    //TODO: test many methods with static cost limits
}
