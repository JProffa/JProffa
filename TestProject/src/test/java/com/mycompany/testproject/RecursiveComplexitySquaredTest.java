/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testproject;

import fi.lolcatz.profiler.ClassBlacklist;
import fi.lolcatz.profiler.ProfileData;
import fi.lolcatz.profiler.RootLogger;
import fi.lolcatz.profiler.Util;
import java.util.logging.Level;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ajmajand
 */
public class RecursiveComplexitySquaredTest {
    
    public RecursiveComplexitySquaredTest() {
    }
    @BeforeClass
    public static void classSetup() {
        ClassBlacklist.add(RecursiveComplexitySquaredTest.class);
        Util.loadAgent();      
        ProfileData.initialize();
        RootLogger.setLoggingLevel(Level.OFF);
    }

    @Before
    public void testSetup() {
        ProfileData.resetCounters();
    }
    
    
    @Test 
    public void recursiveLinearTest() {
        long[] totalCost = new long[6];
        
        int i = RecursiveComplexityExample.linearRecursive(100, 0);
        totalCost[0] = ProfileData.getTotalCost();
        System.out.println("cost 100: " + ProfileData.getTotalCost());
        ProfileData.resetCounters();
        
        RecursiveComplexityExample.linearRecursive(200, 0);
        totalCost[0] = ProfileData.getTotalCost();
        System.out.println("cost 200: " + ProfileData.getTotalCost());
        ProfileData.resetCounters();
        
        RecursiveComplexityExample.linearRecursive(300, 0);
        totalCost[0] = ProfileData.getTotalCost();
        System.out.println("cost 300: " + ProfileData.getTotalCost());
        ProfileData.resetCounters();
        
        RecursiveComplexityExample.linearRecursive(400, 0);
        totalCost[0] = ProfileData.getTotalCost();
        System.out.println("cost 400: " + ProfileData.getTotalCost());
        ProfileData.resetCounters();
        
        RecursiveComplexityExample.linearRecursive(500, 0);
        totalCost[0] = ProfileData.getTotalCost();
        System.out.println("cost 500: " + ProfileData.getTotalCost());
        ProfileData.resetCounters();
        
        RecursiveComplexityExample.linearRecursive(600, 0);
        totalCost[0] = ProfileData.getTotalCost();
        System.out.println("cost 600: " + ProfileData.getTotalCost());
        ProfileData.resetCounters();
        
        assertTrue(totalCost[0]*2 >= totalCost[1]);
        assertTrue(totalCost[1]*2 >= totalCost[2]);
        assertTrue(totalCost[2]*2 >= totalCost[3]);
        assertTrue(totalCost[3]*2 >= totalCost[4]);
        assertTrue(totalCost[4]*2 >= totalCost[5]);
    }
    
    @Test
    public void recursiveSquaredTest() {
        long[] totalCost = new long[6];
        
        RecursiveComplexityExample.squaredRecursive(5, 0, 5);
        totalCost[0] = ProfileData.getTotalCost();
        System.out.println("cost 5: " + ProfileData.getTotalCost());
        ProfileData.resetCounters();
        
        RecursiveComplexityExample.squaredRecursive(10, 0, 10);
        totalCost[1] = ProfileData.getTotalCost();
        System.out.println("cost 10: " + ProfileData.getTotalCost());
        ProfileData.resetCounters();
        
        RecursiveComplexityExample.squaredRecursive(20, 0, 20);
        totalCost[2] = ProfileData.getTotalCost();
        System.out.println("cost 20: " + ProfileData.getTotalCost());
        ProfileData.resetCounters();
        
        RecursiveComplexityExample.squaredRecursive(40, 0, 40);
        totalCost[3] = ProfileData.getTotalCost();
        System.out.println("cost 40: " + ProfileData.getTotalCost());
        ProfileData.resetCounters();
        
        RecursiveComplexityExample.squaredRecursive(80, 0, 80);
        totalCost[4] = ProfileData.getTotalCost();
        System.out.println("cost 80: " + ProfileData.getTotalCost());
        ProfileData.resetCounters();
        
        assertTrue(totalCost[0]*4 >= totalCost[1]);
        assertTrue(totalCost[1]*4 >= totalCost[2]);
        assertTrue(totalCost[2]*4 >= totalCost[3]);
        assertTrue(totalCost[3]*4 >= totalCost[4]);
        assertTrue(totalCost[4]*4 >= totalCost[5]);
        
    }
    
}
