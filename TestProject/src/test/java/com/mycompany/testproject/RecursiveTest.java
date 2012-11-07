package com.mycompany.testproject;

import fi.lolcatz.profiler.ClassBlacklist;
import fi.lolcatz.profiler.ProfileData;
import fi.lolcatz.profiler.Util;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class RecursiveTest {

    @BeforeClass
    public static void classSetup() {
        ClassBlacklist.add(RecursiveTest.class);
        Example.main(null);
        Util.loadAgent();
        ProfileData.initialize();
    }
    

    @Before
    public void testSetup() {
        ProfileData.resetCounters();
    }

    @Test
    public void testRecursionCostSix() {
        FunctionExample.recursiveFunction(6);
        long totalCost = ProfileData.getTotalCost();
        assertEquals(105, totalCost);
    }

    @Test
    public void testRecursionCostIsDeterministic() {
        FunctionExample.recursiveFunction(6);
        long totalCost = ProfileData.getTotalCost();
        assertEquals(105, totalCost);
        ProfileData.resetCounters();
        FunctionExample.recursiveFunction(6);
        totalCost = ProfileData.getTotalCost();
        assertEquals(105, totalCost);
    }

    @Test
    public void testRecursionCostThree() {
        FunctionExample.recursiveFunction(3);
        long totalCost = ProfileData.getTotalCost();
        assertTrue("The total cost was: " + totalCost, 170 > totalCost && totalCost > 85);
    }

    @Test
    public void testRecursionCostThreeThousandTwo() {
        FunctionExample.recursiveFunction(3002);
        long totalCost = ProfileData.getTotalCost();
        assertTrue("The total cost was: " + totalCost, 700 > totalCost && totalCost > 500);
    }
}
