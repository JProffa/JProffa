package com.mycompany.testproject.iterativeTests;

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
    public void testLogarithmic() throws Exception {
        
        impl.setMethodName("logarithmicFunction");
        impl.runStatic(impl.getInput(1000L));
        
        long[] totalCost = new long[5];
        long syote = 1000L;
        
        totalCost[0] = impl.runStatic(impl.getInput(syote));
        Util.printSortedClasses();
        
        for (int i = 1; i < totalCost.length; i++) {
            syote = (long) (syote * Math.pow(4, i));
            totalCost[i] = impl.runStatic(impl.getInput(syote));
            System.out.println(i+1 + ": -------");
            Util.printSortedClasses();
        }
        printResults("--- testLogarithmic ---", totalCost);
        assertTrue(totalCost[0]*2 >= totalCost[1]);
        assertTrue(totalCost[1]*2 >= totalCost[2]);
        assertTrue(totalCost[2]*2 >= totalCost[3]);
        assertTrue(totalCost[3]*2 >= totalCost[4]);
        
    }
    
    @Test
    public void testLogarithmicLarge() throws Exception {
        
        impl.setMethodName("logarithmicFunction");
        impl.runStatic(impl.getInput(1000L));
        
        long[] totalCost = new long[5];
        long syote = 1000L;
        
        totalCost[0] = impl.runStatic(impl.getInput(syote));
        System.out.println(1 + ": -------");
        Util.printSortedClasses();
        
        for (int i = 1; i < totalCost.length; i++) {
            syote = (long) (syote * Math.pow(4, i));
            totalCost[i] = impl.runStatic(impl.getInput(syote));
            System.out.println(i+1 + ": -------");
            Util.printSortedClasses();
            
        }
        printResults("--- testLogarithmicLarge ---", totalCost);
        assertTrue(totalCost[0]*2 >= totalCost[1]);
        assertTrue(totalCost[1]*2 >= totalCost[2]);
        assertTrue(totalCost[2]*2 >= totalCost[3]);
        assertTrue(totalCost[3]*2 >= totalCost[4]);
        
    }
    
    @Test
    public void testLogarithmicHuge() throws Exception {
            
        impl.setMethodName("logarithmicFunction");
        impl.runStatic(impl.getInput(100000000000L));
        
        long[] totalCost = new long[5];
        long syote = 100000000000L;
        
        totalCost[0] = impl.runStatic(impl.getInput(syote));
        System.out.println(1 + ": -------");
        Util.printSortedClasses();
            
        for (int i = 1; i < totalCost.length; i++) {
            syote = (long) (syote * Math.pow(4, i));
            totalCost[i] = impl.runStatic(impl.getInput(syote)); 
            System.out.println(i+1 + ": -------");
            Util.printSortedClasses();
        }
        printResults("--- testLogarithmicHuge ---", totalCost);
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
