package com.mycompany.testproject;

import com.mycompany.testproject.iteratives.IterativeExample;
import fi.lolcatz.profiledata.ProfileData;
import fi.lolcatz.profiler.ClassBlacklist;
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
        IterativeExample.iterativeFunction(5);
        long totalCost = Util.getTotalCost();
        assertEquals(66, totalCost);
    }
    
    @Test
    public void testIterativeCostIsDeterministic() {
        IterativeExample.iterativeFunction(5);
        long totalCost = Util.getTotalCost();
        assertEquals(66, totalCost);
        ProfileData.resetCounters();
        IterativeExample.iterativeFunction(5);
        totalCost = Util.getTotalCost();
        assertEquals(66, totalCost);
    }
    
    @Test
    public void testIterativeHundred() {
        IterativeExample.iterativeFunction(100);
        long totalCost = Util.getTotalCost();
        assertTrue("The total cost was: " + totalCost, 400 > totalCost && totalCost > 300);
    }

    @Test
    public void testIterativeThreeThousandOne() {
        IterativeExample.iterativeFunction(3001);
        long totalCost = Util.getTotalCost();
        assertTrue("The total cost was: " + totalCost, 600 > totalCost && totalCost > 300);
    }
    
    @Test
    public void testSumOnePlusTwo() {
        Example.sum(1, 2);
        long totalCost = Util.getTotalCost();
        assertTrue("The total cost was: " + totalCost, 20 > totalCost && totalCost > 3);
    }

    @Test
    public void testFactorialTwoThousand() {
        IterativeExample.factorialForFunction(2000);
        long totalCost = Util.getTotalCost();
        assertTrue("The total cost was: " + totalCost, 16100 > totalCost && totalCost > 15000);
    }

}
