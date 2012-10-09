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
        
        
        
    }

    @Before
    public void testSetup() {
        ProfileData.initialize();
    }

    @Test
    public void testExample() {
        assertEquals(3, Example.sum(1, 2));
    }

    @Test
    public void testRecursionCost() {
        Example.main(null);
        long totalCost = ProfileData.getTotalCost();
        assertTrue(107 > totalCost && totalCost > 100 );
    }

    @Test
    public void testRecursionCostThree() {
        FunctionExample.recursiveFunction(3);
        long totalCost = ProfileData.getTotalCost();
        assertTrue(95 > totalCost && totalCost > 85);
    }
}
