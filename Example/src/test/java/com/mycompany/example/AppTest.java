package com.mycompany.example;

import fi.lolcatz.profiler.*;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @BeforeClass
    public static void classSetup() {
        Example.main(null);
        Util.loadAgent();
        ProfileData.initialize();
    }

    @Before
    public void testSetup() {
        ProfileData.resetCounters();
    }

    @Test
    public void testExample() {
        assertEquals(3, Example.sum(1, 2));
    }

    @Test
    public void testRecursionCostSix() {
        FunctionExample.recursiveFunction(6);
        long totalCost = ProfileData.getTotalCost();
        assertTrue(200 > totalCost && totalCost > 100 );
    }

    @Test
    public void testRecursionCostThree() {
        FunctionExample.recursiveFunction(3);
        long totalCost = ProfileData.getTotalCost();
        assertTrue(170 > totalCost && totalCost > 85);
    }
}
