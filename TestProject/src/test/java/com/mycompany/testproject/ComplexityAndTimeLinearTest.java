package com.mycompany.testproject;

import fi.lolcatz.profiler.ClassBlacklist;
import fi.lolcatz.profiler.ProfileData;
import fi.lolcatz.profiler.RootLogger;
import fi.lolcatz.profiler.Util;
import java.util.logging.Level;
import org.junit.After;
import org.junit.AfterClass;
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
        IterativeComplexityExample.linearLoop(200000000);
        long endTime   = System.currentTimeMillis();
        freshTime = endTime - startTime;
        
        
        
        ClassBlacklist.add(ComplexityAndTimeLinearTest.class);
        Util.loadAgent();      
        ProfileData.initialize();
        RootLogger.setLoggingLevel(Level.OFF);
    }

    @Before
    public void testSetup() {
        ProfileData.resetCounters();
    }
    
    @Test
    public void testLinear() {
        long[] totalCost = new long[5];
        
        IterativeComplexityExample.linearLoop(100);
        totalCost[0] = ProfileData.getTotalCost();
        System.out.println("cost 100 " + ProfileData.getTotalCost());
        ProfileData.resetCounters();
        IterativeComplexityExample.linearLoop(200);
        totalCost[1] = ProfileData.getTotalCost();
        System.out.println("cost 200 " +  ProfileData.getTotalCost());
        ProfileData.resetCounters();
        IterativeComplexityExample.linearLoop(300);
        totalCost[2] = ProfileData.getTotalCost();
        ProfileData.resetCounters();
        IterativeComplexityExample.linearLoop(400);
        totalCost[3] = ProfileData.getTotalCost();
        ProfileData.resetCounters();
        IterativeComplexityExample.linearLoop(500);
        totalCost[4] = ProfileData.getTotalCost();
        ProfileData.resetCounters();
        assertTrue(totalCost[0]+1100 == totalCost[1]);
        assertTrue(totalCost[1]+1100 == totalCost[2]);
        assertTrue(totalCost[2]+1100 == totalCost[3]);
        assertTrue(totalCost[3]+1100 == totalCost[4]);
    }
    
    @Test
    public void testLinearLarge() {
        long[] totalCost = new long[5];
        
        IterativeComplexityExample.linearLoop(10000);
        totalCost[0] = ProfileData.getTotalCost();
        ProfileData.resetCounters();
        IterativeComplexityExample.linearLoop(20000);
        totalCost[1] = ProfileData.getTotalCost();
        ProfileData.resetCounters();
        IterativeComplexityExample.linearLoop(30000);
        totalCost[2] = ProfileData.getTotalCost();
        ProfileData.resetCounters();
        IterativeComplexityExample.linearLoop(40000);
        totalCost[3] = ProfileData.getTotalCost();
        ProfileData.resetCounters();
        IterativeComplexityExample.linearLoop(50000);
        totalCost[4] = ProfileData.getTotalCost();
        ProfileData.resetCounters();
        assertTrue(totalCost[0]+110000 == totalCost[1]);
        assertTrue(totalCost[1]+110000 == totalCost[2]);
        assertTrue(totalCost[2]+110000 == totalCost[3]);
        assertTrue(totalCost[3]+110000 == totalCost[4]);
    }
    
    @Test
    public void testLinearHUUGE() {
        long[] totalCost = new long[5];
        
        IterativeComplexityExample.linearLoop(10000000);
        totalCost[0] = ProfileData.getTotalCost();
        ProfileData.resetCounters();
        IterativeComplexityExample.linearLoop(20000000);
        totalCost[1] = ProfileData.getTotalCost();
        ProfileData.resetCounters();
        IterativeComplexityExample.linearLoop(30000000);
        totalCost[2] = ProfileData.getTotalCost();
        ProfileData.resetCounters();
        IterativeComplexityExample.linearLoop(40000000);
        totalCost[3] = ProfileData.getTotalCost();
        ProfileData.resetCounters();
        IterativeComplexityExample.linearLoop(50000000);
        totalCost[4] = ProfileData.getTotalCost();
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
        IterativeComplexityExample.linearLoop(200000000);
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        
        System.out.println("Runtime without profiler was " + freshTime + "ms");
        System.out.println("Runtime with profiler was " + totalTime + "ms");
        
        assertTrue(totalTime < 10*freshTime);
        
    }
    
}
