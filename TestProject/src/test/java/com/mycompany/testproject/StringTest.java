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
public class StringTest {
    
    public StringTest() {
    }
    
    @BeforeClass
    public static void classSetup() {
        Example.main(null);
        Util.loadAgent();      
        ProfileData.initialize(); 
        // Method needs to be initialized before using, the object call problem.
        StringExample.stringReplace("aaaaaaaaas", "s", "a");
    }
    
    @Before
    public void testSetup() {
        ProfileData.resetCounters();
    }
    
      @Test
    public void testStringReplaceCostTen() {
        StringExample.stringReplace("aaaaaaaaas", "s", "a");
        long totalCost = ProfileData.getTotalCost();
        assertTrue("The total cost was: " + totalCost, totalCost == 14);
    }
    
    @Test
    public void testStringReplaceIsDeterministic() {
        StringExample.stringReplace("aaaaaaaaas", "s", "a");
        long totalCost = ProfileData.getTotalCost();
        assertTrue("The total cost was: " + totalCost, totalCost == 14);
    }
    
    @Test
    /**
     * should be about linear compared to to testStringReplaceCostTen()
     */
    public void testStringReplaceCostHundred() {
        StringExample.stringReplace("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaas", "s", "a");
        long totalCost = ProfileData.getTotalCost();
        assertTrue("The total cost was: " + totalCost, totalCost == 14);
    }
}
