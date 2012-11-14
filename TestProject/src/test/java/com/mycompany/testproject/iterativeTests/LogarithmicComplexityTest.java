package com.mycompany.testproject.iterativeTests;

import com.mycompany.testproject.iteratives.IterativeComplexityExample;
import fi.lolcatz.profiledata.ProfileData;
import fi.lolcatz.profiler.ClassBlacklist;
import fi.lolcatz.profiler.Util;
import org.junit.*;

import static org.junit.Assert.*;

public class LogarithmicComplexityTest {
    
    LongImpl impl;
    
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
        impl = new LongImpl();
        impl.setClassName("com.mycompany.testproject.iteratives.IterativeComplexityExample");
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
    public void testLogarithmic() {
        
        impl.setMethodName("logarithmicFunction");
        impl.run(impl.getInput(1000L));
        
        long[] totalCost = new long[5];
        long syote = 1000L;
        
        totalCost[0] = impl.run(impl.getInput(syote));
        
        for (int i = 1; i < totalCost.length; i++) {
            syote = (long) (syote * Math.pow(4, i));
            totalCost[i] = impl.run(impl.getInput(syote));    
        }
        printResults("--- Test Logarithmic Huge ---", totalCost);
        
        assertTrue(totalCost[0]*2 >= totalCost[1]);
        assertTrue(totalCost[1]*2 >= totalCost[2]);
        assertTrue(totalCost[2]*2 >= totalCost[3]);
        assertTrue(totalCost[3]*2 >= totalCost[4]);
        
    }
    
    @Test
    public void testLogarithmicLarge() {
        
        impl.setMethodName("logarithmicFunction");
        impl.run(impl.getInput(1000L));
        
        long[] totalCost = new long[5];
        long syote = 1000L;
        
        totalCost[0] = impl.run(impl.getInput(syote));
        
        for (int i = 1; i < totalCost.length; i++) {
            syote = (long) (syote * Math.pow(4, i));
            totalCost[i] = impl.run(impl.getInput(syote));    
        }
        printResults("--- Test Logarithmic Huge ---", totalCost);
        
        assertTrue(totalCost[0]*2 >= totalCost[1]);
        assertTrue(totalCost[1]*2 >= totalCost[2]);
        assertTrue(totalCost[2]*2 >= totalCost[3]);
        assertTrue(totalCost[3]*2 >= totalCost[4]);
        
    }
    
    @Test
    public void testLogarithmicHuge() {
            
        impl.setMethodName("logarithmicFunction");
        impl.run(impl.getInput(100000000000L));
        
        long[] totalCost = new long[5];
        long syote = 100000000000L;
        
        totalCost[0] = impl.run(impl.getInput(syote));
        
        for (int i = 1; i < totalCost.length; i++) {
            syote = (long) (syote * Math.pow(4, i));
            totalCost[i] = impl.run(impl.getInput(syote));    
        }
        printResults("--- Test Logarithmic Huge ---", totalCost);
        assertTrue(totalCost[0]*2 >= totalCost[1]);
        assertTrue(totalCost[1]*2 >= totalCost[2]);
        assertTrue(totalCost[2]*2 >= totalCost[3]);
        assertTrue(totalCost[3]*2 >= totalCost[4]);
        
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
