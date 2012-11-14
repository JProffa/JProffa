package com.mycompany.testproject.recursiontests;

import com.mycompany.testproject.Example;
import com.mycompany.testproject.iterativeTests.IntegerImpl;
import fi.lolcatz.profiledata.ProfileData;
import fi.lolcatz.profiler.ClassBlacklist;
import fi.lolcatz.profiler.Util;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class RecursiveTest {

    IntegerImpl impl;
    
    @BeforeClass
    public static void classSetup() {
        ClassBlacklist.add(RecursiveTest.class);
        Example.main(null);
        Util.loadAgent();
        ProfileData.initialize();
    }
    

    @Before
    public void testSetup() {
        impl = new IntegerImpl();
        impl.setClassName("com.mycompany.testproject.recursives.RecursiveComplexityExample");
    }

    @Test
    public void testRecursionCostIsDeterministic() throws Exception {
        impl.setMethodName("recursiveFunction");
        
        impl.run(impl.getInput(500));
        
        long first = impl.run(500);
        long second = impl.run(500);
        System.out.println("Margin of error: " + impl.getMarginOfError(first));
        assertTrue("Suorituskerrat eivät olleet 50 sisällä toisistaan", first > second-impl.getMarginOfError(first) && first < second+impl.getMarginOfError(first));
        
    }
    
    @Test
    public void testRecursionLinear() throws Exception {
        impl.setMethodName("linearRecursive");
        impl.run(impl.getInput(1000), impl.getInput(0));
        
        long[] totalCost = new long[5];
        int syote = 10;
        
        totalCost[0] = impl.run(impl.getInput(syote), impl.getInput(0));
        
        for (int i = 1; i < totalCost.length; i++) {
            syote = 2*syote;
            totalCost[i] = impl.run(impl.getInput(syote), impl.getInput(0));    
        }
        
        printResults("--- testRecursiveLinear ---", totalCost);
        
        assertTrue(totalCost[0]*2 >= totalCost[1]);
        assertTrue(totalCost[1]*2 >= totalCost[2]);
        assertTrue(totalCost[2]*2 >= totalCost[3]);
        assertTrue(totalCost[3]*2 >= totalCost[4]);
    }
    
    @Test
    public void testRecursionLinearLarge() throws Exception {
        impl.setMethodName("linearRecursive");
        impl.run(impl.getInput(1000), impl.getInput(0));
        
        long[] totalCost = new long[5];
        int syote = 100;
        
        totalCost[0] = impl.run(impl.getInput(syote), impl.getInput(0));
        
        for (int i = 1; i < totalCost.length; i++) {
            syote = 2*syote;
            totalCost[i] = impl.run(impl.getInput(syote), impl.getInput(0));    
        }
        printResults("--- testRecursiveLinearLarge ---", totalCost);
        
        assertTrue(totalCost[0]*2 >= totalCost[1]);
        assertTrue(totalCost[1]*2 >= totalCost[2]);
        assertTrue(totalCost[2]*2 >= totalCost[3]);
        assertTrue(totalCost[3]*2 >= totalCost[4]);
    }
    
    @Test
    public void testRecursionLinearHUGE() throws Exception {
        impl.setMethodName("linearRecursive");
        impl.run(impl.getInput(1000),impl.getInput(0));
        
        long[] totalCost = new long[5];
        int syote = 1000;
        
        totalCost[0] = impl.run(impl.getInput(syote),impl.getInput(0));
        
        for (int i = 1; i < totalCost.length; i++) {
            syote = 2*syote;
            totalCost[i] = impl.run(impl.getInput(syote),impl.getInput(0));    
        }
        
        printResults("--- testRecursiveLinearHuge ---", totalCost);
        
        assertTrue(totalCost[0]*2 >= totalCost[1]);
        assertTrue(totalCost[1]*2 >= totalCost[2]);
        assertTrue(totalCost[2]*2 >= totalCost[3]);
        assertTrue(totalCost[3]*2 >= totalCost[4]);
    }
    
    @Test
    public void testRecursionSquared() throws Exception {
        impl.setMethodName("squaredRecursive");
        impl.run(impl.getInput(10),impl.getInput(0),impl.getInput(10));
        
        long[] totalCost = new long[5];
        int syote = 50;
        
        totalCost[0] = impl.run(impl.getInput(syote),impl.getInput(0),impl.getInput(syote));
        
        for (int i = 1; i < totalCost.length; i++) {
            syote = 2*syote;
            totalCost[0] = impl.run(impl.getInput(syote),impl.getInput(0),impl.getInput(syote));   
        }
        
        printResults("--- testRecursiveSquared ---", totalCost);
        
        assertTrue(totalCost[0]*4 >= totalCost[1]);
        assertTrue(totalCost[1]*4 >= totalCost[2]);
        assertTrue(totalCost[2]*4 >= totalCost[3]);
        assertTrue(totalCost[3]*4 >= totalCost[4]);
    }
    
    @Test
    public void testRecursionSquaredLarge() throws Exception {
        impl.setMethodName("squaredRecursive");
        impl.run(impl.getInput(10),impl.getInput(0),impl.getInput(10));
        
        long[] totalCost = new long[5];
        int syote = 200;
        
        totalCost[0] = impl.run(impl.getInput(syote),impl.getInput(0),impl.getInput(syote));
        
        for (int i = 1; i < totalCost.length; i++) {
            syote = 2*syote;
            totalCost[0] = impl.run(impl.getInput(syote),impl.getInput(0),impl.getInput(syote));   
        }
        
        printResults("--- testRecursiveSquaredLarge ---", totalCost);
        
        assertTrue(totalCost[0]*4 >= totalCost[1]);
        assertTrue(totalCost[1]*4 >= totalCost[2]);
        assertTrue(totalCost[2]*4 >= totalCost[3]);
        assertTrue(totalCost[3]*4 >= totalCost[4]);
    }
    
    @Test
    public void testRecursionSquaredHuge() throws Exception {
        impl.setMethodName("squaredRecursive");
        impl.run(impl.getInput(10),impl.getInput(0),impl.getInput(10));
        
        long[] totalCost = new long[5];
        int syote = 500;
        
        totalCost[0] = impl.run(impl.getInput(syote),impl.getInput(0),impl.getInput(syote));
        
        for (int i = 1; i < totalCost.length; i++) {
            syote = 2*syote;
            totalCost[0] = impl.run(impl.getInput(syote),impl.getInput(0),impl.getInput(syote));   
        }
        
        printResults("--- testRecursiveSquaredHuge ---", totalCost);
        
        assertTrue(totalCost[0]*4 >= totalCost[1]);
        assertTrue(totalCost[1]*4 >= totalCost[2]);
        assertTrue(totalCost[2]*4 >= totalCost[3]);
        assertTrue(totalCost[3]*4 >= totalCost[4]);
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
