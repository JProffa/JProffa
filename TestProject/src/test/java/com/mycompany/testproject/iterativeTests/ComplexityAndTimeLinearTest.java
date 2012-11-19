package com.mycompany.testproject.iterativeTests;

import com.mycompany.testproject.iteratives.IterativeComplexityExample;
import fi.lolcatz.profiledata.ProfileData;
import fi.lolcatz.profiler.ClassBlacklist;
import fi.lolcatz.profiler.Output;
import fi.lolcatz.profiler.Util;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ComplexityAndTimeLinearTest {
    
    public ComplexityAndTimeLinearTest() {
    }
    
    public static long freshTime;
    IntegerImpl impl;
        
    @BeforeClass
    public static void classSetup() {
        System.out.println("Testing");
        long startTime = System.currentTimeMillis();
        IterativeComplexityExample.linearFunction(200000000);
        long endTime   = System.currentTimeMillis();
        freshTime = endTime - startTime;
        

        ClassBlacklist.add(ComplexityAndTimeLinearTest.class);
        Util.loadAgent();      
        ProfileData.initialize();
        Logger.getRootLogger().setLevel(Level.OFF);
    }

    @Before
    public void testSetup() {
        impl = new IntegerImpl();
        impl.setClassName("com.mycompany.testproject.iteratives.IterativeComplexityExample");
    }
    
    @Test
    public void testLinear() throws Exception {
        impl.setMethodName("linearFunction");
        impl.run(impl.getInput(1000));
        
        long[] totalCost = new long[5];
        int syote = 1000;
        
        totalCost[0] = impl.run(impl.getInput(syote));
        
        for (int i = 1; i < totalCost.length; i++) {
            syote = 2*syote;
            totalCost[i] = impl.run(impl.getInput(syote));    
        }
        
        assertTrue(totalCost[0]*2 >= totalCost[1]);
        assertTrue(totalCost[1]*2 >= totalCost[2]);
        assertTrue(totalCost[2]*2 >= totalCost[3]);
        assertTrue(totalCost[3]*2 >= totalCost[4]);
    }
    
    @Test
    public void testLinearLarge() throws Exception {
        impl.setMethodName("linearFunction");
        impl.run(impl.getInput(1000));
        
        long[] totalCost = new long[5];
        int syote = 100000;
        
        totalCost[0] = impl.run(impl.getInput(syote));
        
        for (int i = 1; i < totalCost.length; i++) {
            syote = 2*syote;
            totalCost[i] = impl.run(impl.getInput(syote));    
        }
        
        assertTrue(totalCost[0]*2 >= totalCost[1]);
        assertTrue(totalCost[1]*2 >= totalCost[2]);
        assertTrue(totalCost[2]*2 >= totalCost[3]);
        assertTrue(totalCost[3]*2 >= totalCost[4]);
    }
    
       
    @Test
    public void testGenerateOutput() throws Exception {
        impl.setMethodName("linearFunction");
        List<Integer> list = new ArrayList<Integer>();
        list.add(500);
        list.add(1000);
        Output<Integer> o = impl.generateOutput(list);
        for (Long l : o.getTime()){
            assertTrue(l > 0);
        }
       
    }
    
    @Test
    public void testLinearHUUGE() throws Exception {
        impl.setMethodName("linearFunction");
        impl.run(impl.getInput(1000));
        
        long[] totalCost = new long[5];
        int syote = 10000000;
        
        totalCost[0] = impl.run(impl.getInput(syote));
        
        for (int i = 1; i < totalCost.length; i++) {
            syote = 2*syote;
            totalCost[i] = impl.run(impl.getInput(syote));    
        }
        
        assertTrue(totalCost[0]*2 >= totalCost[1]);
        assertTrue(totalCost[1]*2 >= totalCost[2]);
        assertTrue(totalCost[2]*2 >= totalCost[3]);
        assertTrue(totalCost[3]*2 >= totalCost[4]);
    }
    
    /**
     * Noting, that profiling nullifies all system related optimization,
     * but if code is not optimized to begin with (like in the example)
     * its about as fast whether or not you profile it.
     */
    
    @Test
    public void testProfilingIsFastEnough() {
        long startTime = System.currentTimeMillis();
        IterativeComplexityExample.linearFunction(200000000);
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        
        System.out.println("Runtime without profiler was " + freshTime + "ms");
        System.out.println("Runtime with profiler was " + totalTime + "ms");
        
        assertTrue(totalTime < 10*freshTime);
        
    }
    
}
