package com.mycompany.testproject;

import com.mycompany.testproject.iteratives.IterativeComplexityExample;
import fi.lolcatz.profiledata.ProfileData;
import fi.lolcatz.profiler.ClassBlacklist;
import fi.lolcatz.profiler.Util;
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
        ProfileData.resetCounters();
    }
    
    @Test
    public void testLinear() {
        long[] totalCost = new long[5];
        
        IterativeComplexityExample.linearFunction(100);
        totalCost[0] = Util.getTotalCost();
        System.out.println("cost 100 " + Util.getTotalCost());
        ProfileData.resetCounters();
        IterativeComplexityExample.linearFunction(200);
        totalCost[1] = Util.getTotalCost();
        System.out.println("cost 200 " +  Util.getTotalCost());
        ProfileData.resetCounters();
        IterativeComplexityExample.linearFunction(300);
        totalCost[2] = Util.getTotalCost();
        ProfileData.resetCounters();
        IterativeComplexityExample.linearFunction(400);
        totalCost[3] = Util.getTotalCost();
        ProfileData.resetCounters();
        IterativeComplexityExample.linearFunction(500);
        totalCost[4] = Util.getTotalCost();
        ProfileData.resetCounters();
        assertTrue(totalCost[0]+1100 == totalCost[1]);
        assertTrue(totalCost[1]+1100 == totalCost[2]);
        assertTrue(totalCost[2]+1100 == totalCost[3]);
        assertTrue(totalCost[3]+1100 == totalCost[4]);
    }
    
    @Test
    public void testLinearLarge() {
        long[] totalCost = new long[5];
        
        IterativeComplexityExample.linearFunction(10000);
        totalCost[0] = Util.getTotalCost();
        ProfileData.resetCounters();
        IterativeComplexityExample.linearFunction(20000);
        totalCost[1] = Util.getTotalCost();
        ProfileData.resetCounters();
        IterativeComplexityExample.linearFunction(30000);
        totalCost[2] = Util.getTotalCost();
        ProfileData.resetCounters();
        IterativeComplexityExample.linearFunction(40000);
        totalCost[3] = Util.getTotalCost();
        ProfileData.resetCounters();
        IterativeComplexityExample.linearFunction(50000);
        totalCost[4] = Util.getTotalCost();
        ProfileData.resetCounters();
        assertTrue(totalCost[0]+110000 == totalCost[1]);
        assertTrue(totalCost[1]+110000 == totalCost[2]);
        assertTrue(totalCost[2]+110000 == totalCost[3]);
        assertTrue(totalCost[3]+110000 == totalCost[4]);
    }
    
    @Test
    public void testLinearHUUGE() {
        long[] totalCost = new long[5];
        
        IterativeComplexityExample.linearFunction(10000000);
        totalCost[0] = Util.getTotalCost();
        ProfileData.resetCounters();
        IterativeComplexityExample.linearFunction(20000000);
        totalCost[1] = Util.getTotalCost();
        ProfileData.resetCounters();
        IterativeComplexityExample.linearFunction(30000000);
        totalCost[2] = Util.getTotalCost();
        ProfileData.resetCounters();
        IterativeComplexityExample.linearFunction(40000000);
        totalCost[3] = Util.getTotalCost();
        ProfileData.resetCounters();
        IterativeComplexityExample.linearFunction(50000000);
        totalCost[4] = Util.getTotalCost();
        ProfileData.resetCounters();
        assertTrue(totalCost[0]+110000000 == totalCost[1]);
        assertTrue(totalCost[1]+110000000 == totalCost[2]);
        assertTrue(totalCost[2]+110000000 == totalCost[3]);
        assertTrue(totalCost[3]+110000000 == totalCost[4]);
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
