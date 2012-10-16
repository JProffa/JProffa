/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testproject;

import fi.lolcatz.profiler.ProfileData;
import fi.lolcatz.profiler.Util;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author oorissan
 */
public class ObjectTest {
    
    @BeforeClass
    public static void classSetup() {
        Example.main(null);
        // Used to initialize the method, creating objects for the first time causes problems with profiler
        ObjectExample.createPersons(1);
        Util.loadAgent();      
        ProfileData.initialize(); 
    }

    @Before
    public void testSetup() {
        ProfileData.resetCounters();
    }
    
     @Test
    public void testObjectsTen() {
        ObjectExample.createPersons(10);
        long totalCost = ProfileData.getTotalCost();
        ProfileData.printBasicBlocksCost(false);
        assertTrue("The total cost was: " + totalCost, totalCost == 210);
    }

    @Test
    public void testObjectsBehaveDeterministic() {
        ObjectExample.createPersons(10);
        long totalCost = ProfileData.getTotalCost();
        ProfileData.printBasicBlocksCost(false);
        assertTrue("The total cost was: " + totalCost + ", expected 211", totalCost == 210);
    }
}
