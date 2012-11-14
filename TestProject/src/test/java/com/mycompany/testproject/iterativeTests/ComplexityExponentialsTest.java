package com.mycompany.testproject.iterativeTests;

import com.mycompany.testproject.iteratives.IterativeComplexityExample;
import fi.lolcatz.profiledata.ProfileData;
import fi.lolcatz.profiler.ClassBlacklist;
import fi.lolcatz.profiler.Util;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ComplexityExponentialsTest {

    public ComplexityExponentialsTest() {
    }
    
    IntegerImpl impl;
    @BeforeClass
    public static void setUpClass() {
        ClassBlacklist.add(ComplexityExponentialsTest.class);
        Util.loadAgent();
        ProfileData.initialize();
        Logger.getRootLogger().setLevel(org.apache.log4j.Level.OFF);
    }

    @Before
    public void testSetup() {
        impl = new IntegerImpl();
        impl.setClassName("com.mycompany.testproject.iteratives.IterativeComplexityExample");
    }

    
    @Test
    public void squaredTest() throws Exception {
        long[] totalCost = new long[5];
        int syote = 10;
        
        totalCost[0] = impl.run(impl.getInput(syote));
        
        for (int i = 1; i < totalCost.length; i++) {
            syote = 2*syote;
            totalCost[i] = impl.run(impl.getInput(syote));    
        }
        
        printResults("--- testSquared ---", totalCost);

        assertTrue(totalCost[0] * 4 >= totalCost[1]);
        assertTrue(totalCost[1] * 4 >= totalCost[2]);
        assertTrue(totalCost[2] * 4 >= totalCost[3]);
        assertTrue(totalCost[3] * 4 >= totalCost[4]);
        System.out.println("- - - - -");

    }

    @Test
    public void testApproximatedSquared() throws Exception {
        long[] totalCost = new long[5];

        int syote = 10;
        
        totalCost[0] = impl.run(impl.getInput(syote));
        
        for (int i = 1; i < totalCost.length; i++) {
            syote = 2*syote;
            totalCost[i] = impl.run(impl.getInput(syote));    
        }
        
        printResults("--- testApproximatedSquared ---", totalCost);
        assertTrue(totalCost[0] * 4 >= totalCost[1]);
        assertTrue(totalCost[1] * 4 >= totalCost[2]);
        assertTrue(totalCost[2] * 4 >= totalCost[3]);
        assertTrue(totalCost[3] * 4 >= totalCost[4]);

        

    }

    @Test
    public void testCoinFlipExponential() throws Exception {
        long[] totalCost = new long[5];
        int syote = 10;
        
        totalCost[0] = impl.run(impl.getInput(syote));
        
        for (int i = 1; i < totalCost.length; i++) {
            syote = 2*syote;
            totalCost[i] = impl.run(impl.getInput(syote));    
        }


        printResults("--- testCoinFlipSquared ---", totalCost);
        
        assertTrue(totalCost[0] * 4 >= totalCost[1]);
        assertTrue(totalCost[1] * 4 >= totalCost[2]);
        assertTrue(totalCost[2] * 4 >= totalCost[3]);
        assertTrue(totalCost[3] * 4 >= totalCost[4]);

        System.out.println("- - - - -");
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
