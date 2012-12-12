    package fi.lolcatz.jproffa.test;

import fi.lolcatz.jproffa.implementatios.IntegerImpl;
import fi.lolcatz.profiler.ClassBlacklist;
import fi.lolcatz.profiler.ComplexityAnalysis;
import fi.lolcatz.profiler.Output;
import fi.lolcatz.profiler.Util;
import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Logger;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ComplexitySquaredTest {

    public ComplexitySquaredTest() {
    }
    
    IntegerImpl impl;
    ComplexityAnalysis framework;
    
    @BeforeClass
    public static void setUpClass() {
        ClassBlacklist.add(ComplexitySquaredTest.class);
        Util.loadAgent();
        Logger.getRootLogger().setLevel(org.apache.log4j.Level.OFF);
    }

    @Before
    public void testSetup() {
        impl = new IntegerImpl();
        impl.setClassName("fi.lolcatz.jproffa.testproject.IterativeComplexityExample");
    }

    
    @Test
    public void squaredTestNoFramework() throws Exception {
        
        impl.setMethodName("squaredFunction");
        
        long[] totalCost = new long[5];
        int syote = 10;
        
        totalCost[0] = impl.runStatic(impl.getInput(syote));
        
        for (int i = 1; i < totalCost.length; i++) {
            syote = 2*syote;
            totalCost[i] = impl.runStatic(impl.getInput(syote));    
        }
        
        printResults("--- testSquared ---", totalCost);

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
    public void squaredTestWithFramework() throws Exception {
        
        impl.setMethodName("squaredFunction");
        
        List<Integer> list = Arrays.asList(2,4,8,16,32,64,512,1024,2048,4096,8192/*,16384, 32768, 65536, 131072, 262144*/);
        Output<Integer> o = impl.runMethod(list);
        int i = 0;
        for (Long l : o.getTime()){
            assertTrue(l > 0);
        }      
        assertTrue("isSquaredOrFaster()", framework.isSquaredOrFaster(o, 1.1));
        assertFalse("isLinearOrFaster()", framework.isLinearOrFaster(o, 1.0));       
        framework.assertSquared(o);

    }

    @Test
    public void testApproximatedSquared() throws Exception {
        
        impl.setMethodName("approximatedSquaredFunction");
        
        List<Integer> list = Arrays.asList(2,4,8,16,32,64,512,1024,2048,4096,8192/*,16384, 32768, 65536, 131072, 262144*/);
        Output<Integer> o = impl.runMethod(list);
        int i = 0;
        for (Long l : o.getTime()){
            assertTrue(l > 0);
        }   
        assertTrue("isSquaredOrFaster()", framework.isSquaredOrFaster(o, 1.1));
        assertFalse("isLinearOrFaster()", framework.isLinearOrFaster(o, 1.0));        
        framework.assertSquared(o);

    }

    @Test
    public void testCoinFlipSquared() throws Exception {     
        impl.setMethodName("squaredCoinFlipFunction");
        
        List<Integer> list = Arrays.asList(2,4,8,16,32,64,512,1024,2048,4096,8192/*,16384, 32768, 65536, 131072, 262144*/);
        Output<Integer> o = impl.runMethod(list);
        int i = 0;
        for (Long l : o.getTime()){
            assertTrue(l > 0);
        }
        assertTrue("isSquaredOrFaster()", framework.isSquaredOrFaster(o, 1.1));
        assertFalse("isLinearOrFaster()", framework.isLinearOrFaster(o, 1.0));
        framework.assertSquared(o);
    }
    
    @Test
    public void testRecursionSquared() throws Exception {
        
        impl.setClassName("fi.lolcatz.jproffa.testproject.RecursiveComplexityExample");
        impl.setMethodName("squaredRecursive");
        
        List<Integer> list = Arrays.asList(50, 100, 200, 400, 800, 1600, 3200, 6400);
        List<Integer> list2 = Arrays.asList(0,0,0,0,0,0,0,0,0,0);
        List<Integer> list3 = Arrays.asList(0,0,0,0,0,0,0,0,0,0);
        Output<Integer> o = impl.runMethod(list, list2, list3);
        int i = 0;
        for (Long l : o.getTime()){
            assertTrue(l > 0);
        }   
        assertTrue("isSquaredOrFaster()", framework.isSquaredOrFaster(o, 1.1));
        assertFalse("isLinearOrFaster()", framework.isLinearOrFaster(o, 1.0));        
        framework.assertSquared(o);
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
