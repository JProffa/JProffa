package com.mycompany.testproject;

import fi.lolcatz.profiler.ClassBlacklist;
import fi.lolcatz.profiler.ProfileData;
import fi.lolcatz.profiler.Util;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class IterativeTest {
    
    public IterativeTest() {
    }
    
    
    @BeforeClass
    public static void classSetup() {
        ClassBlacklist.add(IterativeTest.class);
        Example.main(null);
        Util.loadAgent();      
        ProfileData.initialize(); 
    } 
    
    @Before
    public void testSetup() {
        ProfileData.resetCounters();
    }
    
    @Test
    public void testIterativeCostFive() {
        FunctionExample.iterativeFunction(5);
        long totalCost = ProfileData.getTotalCost();
        assertEquals(66, totalCost);
    }
    
    @Test
    public void testIterativeCostIsDeterministic() {
        FunctionExample.iterativeFunction(5);
        long totalCost = ProfileData.getTotalCost();
        assertEquals(66, totalCost);
        ProfileData.resetCounters();
        FunctionExample.iterativeFunction(5);
        totalCost = ProfileData.getTotalCost();
        assertEquals(66, totalCost);
    }
    
    @Test
    public void testIterativeHundred() {
        FunctionExample.iterativeFunction(100);
        long totalCost = ProfileData.getTotalCost();
        assertTrue("The total cost was: " + totalCost, 400 > totalCost && totalCost > 300);
    }

    @Test
    public void testIterativeThreeThousandOne() {
        FunctionExample.iterativeFunction(3001);
        long totalCost = ProfileData.getTotalCost();
        assertTrue("The total cost was: " + totalCost, 600 > totalCost && totalCost > 300);
    }
    
    @Test
    public void testSumOnePlusTwo() {
        Example.sum(1, 2);
        long totalCost = ProfileData.getTotalCost();
        assertTrue("The total cost was: " + totalCost, 20 > totalCost && totalCost > 3);
    }

    @Test
    public void testFactorialTwoThousand() {
        FactorialExample.factorialInt(2000);
        long totalCost = ProfileData.getTotalCost();
        assertTrue("The total cost was: " + totalCost, 16100 > totalCost && totalCost > 15000);
    }

}
