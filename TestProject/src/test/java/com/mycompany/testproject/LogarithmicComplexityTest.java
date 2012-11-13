package com.mycompany.testproject;

import com.mycompany.testproject.iteratives.IterativeComplexityExample;
import fi.lolcatz.profiledata.ProfileData;
import fi.lolcatz.profiler.ClassBlacklist;
import fi.lolcatz.profiler.Util;
import org.junit.*;

import static org.junit.Assert.*;

public class LogarithmicComplexityTest {
    
    public LogarithmicComplexityTest() {
    }
    
    @BeforeClass
    public static void classSetup() {
        ClassBlacklist.add(LogarithmicComplexityTest.class);
        Util.loadAgent();      
        ProfileData.initialize();
    }

    @Before
    public void testSetup() {
        ProfileData.resetCounters();
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void LogarithmicTest() {
        long[] totalCost = new long[5];
        
        IterativeComplexityExample.logarithmicFunction(100);
        totalCost[0] = Util.getTotalCost();
        ProfileData.resetCounters();
        
        IterativeComplexityExample.logarithmicFunction(100*4);
        totalCost[1] = Util.getTotalCost();
        ProfileData.resetCounters();
        
        IterativeComplexityExample.logarithmicFunction(100*4*4);
        totalCost[2] = Util.getTotalCost();
        ProfileData.resetCounters();
        
        IterativeComplexityExample.logarithmicFunction(100*4*4*4);
        totalCost[3] = Util.getTotalCost();
        ProfileData.resetCounters();
        
        IterativeComplexityExample.logarithmicFunction(100*4*4*4*4);
        totalCost[4] = Util.getTotalCost();
        ProfileData.resetCounters();
        
        for (long l : totalCost) {
            System.out.println(l);
        }
        
        assertTrue(totalCost[0]*2 >= totalCost[1]);
        assertTrue(totalCost[1]*2 >= totalCost[2]);
        assertTrue(totalCost[2]*2 >= totalCost[3]);
        assertTrue(totalCost[3]*2 >= totalCost[4]);
        
    }
    
        @Test
    public void LogarithmicHugeTest() {
        long[] totalCost = new long[5];
        
        IterativeComplexityExample.logarithmicFunction(100000000000L);
        totalCost[0] = Util.getTotalCost();
        ProfileData.resetCounters();
        
        IterativeComplexityExample.logarithmicFunction(100000000000L*4);
        totalCost[1] = Util.getTotalCost();
        ProfileData.resetCounters();
        
        IterativeComplexityExample.logarithmicFunction(100000000000L*4*4);
        totalCost[2] = Util.getTotalCost();
        ProfileData.resetCounters();
        
        IterativeComplexityExample.logarithmicFunction(100000000000L*4*4*4);
        totalCost[3] = Util.getTotalCost();
        ProfileData.resetCounters();
        
        IterativeComplexityExample.logarithmicFunction(100000000000L*4*4*4*4);
        totalCost[4] = Util.getTotalCost();
        ProfileData.resetCounters();
        
        for (long l : totalCost) {
            System.out.println(l);
        }
        
        assertTrue(totalCost[0]*2 >= totalCost[1]);
        assertTrue(totalCost[1]*2 >= totalCost[2]);
        assertTrue(totalCost[2]*2 >= totalCost[3]);
        assertTrue(totalCost[3]*2 >= totalCost[4]);
        
    }
    
}
