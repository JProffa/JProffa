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
        assertEquals(106, ProfileData.getTotalCost());
    }

    @Test
    public void testRecursionCostThree() {
        FunctionExample.recursiveFunction(3);
        assertEquals(93, ProfileData.getTotalCost());
    }
}
