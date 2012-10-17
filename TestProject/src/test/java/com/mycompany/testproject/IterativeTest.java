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
public class IterativeTest {
    
    public IterativeTest() {
    }
    
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
    public void testIterativeCostFive() {
        FunctionExample.iterativeFunction(5);
        long totalCost = ProfileData.getTotalCost();
        assertTrue("The total cost was: " + totalCost, totalCost == 71);
    }
    
    @Test
    public void testIterativeCostIsDeterministic() {
        FunctionExample.iterativeFunction(5);
        long totalCost = ProfileData.getTotalCost();
        assertTrue("The total cost was: " + totalCost, totalCost == 71);
        ProfileData.resetCounters();
        FunctionExample.iterativeFunction(5);
        totalCost = ProfileData.getTotalCost();
        assertTrue("The total cost was: " + totalCost, totalCost == 71);
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
        assertTrue("The total cost was: " + totalCost, 20 > totalCost && totalCost > 9);
    }

    @Test
    public void testFactorialTwoThousand() {
        FactorialExample.factorialInt(2000);
        long totalCost = ProfileData.getTotalCost();
        assertTrue("The total cost was: " + totalCost, 16100 > totalCost && totalCost > 15000);
    }

}
