package fi.lolcatz.jproffa.test;

import fi.lolcatz.jproffa.implementations.IntegerImpl;
import fi.lolcatz.profiler.Output;
import fi.lolcatz.profiler.WithProfiling;
import fi.lolcatz.profiler.analysis.Complexity;
import fi.lolcatz.profiler.analysis.ComplexityChecks;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import jproffa.graph.GraphRenderer;

import static org.junit.Assert.assertFalse;
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

    private void assertSpeed(Output<?> o) {
        for (long l : o.getTime()) {
            assertTrue(l > 0);
        }
        assertFalse("Quadratic function was mistaken for linear", ComplexityChecks.isLinearOrFaster(o, ComplexityChecks.DEFAULT_TOLERANCE));
        ComplexityChecks.assertQuadraticOrFaster(o);
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

    @Test
    public void quadraticTestWithFramework() throws Exception {
        impl.setMethodName("quadraticFunction");

        List<Integer> list = Arrays.asList(2, 4, 8, 16, 32, 64, 512, 1024, 2048, 4096, 8192, 16000, 32000);
        Output<Integer> o = impl.runMethod(list);
        assertSpeed(o);
    }

    @Test
    public void testApproximatedQuadratic() throws Exception {
        impl.setMethodName("approximatedQuadraticFunction");

        List<Integer> list = Arrays.asList(2, 4, 8, 16, 32, 64, 512, 1024, 2048, 4096, 8192, 16000, 32000);
        Output<Integer> o = impl.runMethod(list);
        assertSpeed(o);
    }

    @Test
    public void testCoinFlipQuadratic() throws Exception {
        impl.setMethodName("quadraticCoinFlipFunction");

        List<Integer> list = Arrays.asList(2, 4, 8, 16, 32, 64, 512, 1024, 2048, 4096, 8192);
        Output<Integer> o = impl.runMethod(list);
        assertSpeed(o);
    }

    @Test
    public void testRecursionQuadratic() throws Exception {
        impl.setClassName("fi.lolcatz.jproffa.testproject.RecursiveComplexityExample");
        impl.setMethodName("quadraticRecursive");

        List<Integer> list = Arrays.asList(50, 100, 200, 400, 800, 1600, 3200, 6400);
        List<Integer> list2 = Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        Output<Integer> o = impl.runMethod(list, list2);
        assertSpeed(o);
    }
    
    @Test
    public void testNlogNisFasterThanQuadratic() throws Exception {
        impl.setMethodName("approximatedNlogNFunction");
        
        List<Integer> list = Arrays.asList(2,4,8,16,32,64,512,1024,2048,4096,8192);
        Output<Integer> o = impl.runMethod(list);
        // FIXME: well it infers an essentially linear curve.
        
GraphRenderer.showAndWait("a", "b", o, Complexity.QUADRATIC.fitCurve(o), ComplexityChecks.DEFAULT_TOLERANCE);
        ComplexityChecks.assertQuadraticOrFaster(o);
    }
}
