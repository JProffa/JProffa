package com.mycompany.testproject;

import fi.lolcatz.profiledata.ProfileData;
import fi.lolcatz.profiler.ClassBlacklist;
import fi.lolcatz.profiler.Util;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class RecursiveComplexitySquaredTest {
    
    public RecursiveComplexitySquaredTest() {
    }
    
    @BeforeClass
    public static void classSetup() {
        ClassBlacklist.add(RecursiveComplexitySquaredTest.class);
        Util.loadAgent();      
        ProfileData.initialize();
        Logger.getRootLogger().setLevel(Level.OFF);
    }

    @Before
    public void testSetup() {
        ProfileData.resetCounters();
    }
    
    
    @Test 
    public void recursiveLinearTest() {
        long[] totalCost = new long[6];
        
        int i = RecursiveComplexityExample.linearRecursive(100, 0);
        totalCost[0] = Util.getTotalCost();
        System.out.println("cost 100: " + Util.getTotalCost());
        ProfileData.resetCounters();
        
        RecursiveComplexityExample.linearRecursive(200, 0);
        totalCost[0] = Util.getTotalCost();
        System.out.println("cost 200: " + Util.getTotalCost());
        ProfileData.resetCounters();
        
        RecursiveComplexityExample.linearRecursive(300, 0);
        totalCost[0] = Util.getTotalCost();
        System.out.println("cost 300: " + Util.getTotalCost());
        ProfileData.resetCounters();
        
        RecursiveComplexityExample.linearRecursive(400, 0);
        totalCost[0] = Util.getTotalCost();
        System.out.println("cost 400: " + Util.getTotalCost());
        ProfileData.resetCounters();
        
        RecursiveComplexityExample.linearRecursive(500, 0);
        totalCost[0] = Util.getTotalCost();
        System.out.println("cost 500: " + Util.getTotalCost());
        ProfileData.resetCounters();
        
        RecursiveComplexityExample.linearRecursive(600, 0);
        totalCost[0] = Util.getTotalCost();
        System.out.println("cost 600: " + Util.getTotalCost());
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
        totalCost[0] = Util.getTotalCost();
        System.out.println("cost 5: " + Util.getTotalCost());
        ProfileData.resetCounters();
        
        RecursiveComplexityExample.squaredRecursive(10, 0, 10);
        totalCost[1] = Util.getTotalCost();
        System.out.println("cost 10: " + Util.getTotalCost());
        ProfileData.resetCounters();
        
        RecursiveComplexityExample.squaredRecursive(20, 0, 20);
        totalCost[2] = Util.getTotalCost();
        System.out.println("cost 20: " + Util.getTotalCost());
        ProfileData.resetCounters();
        
        RecursiveComplexityExample.squaredRecursive(40, 0, 40);
        totalCost[3] = Util.getTotalCost();
        System.out.println("cost 40: " + Util.getTotalCost());
        ProfileData.resetCounters();
        
        RecursiveComplexityExample.squaredRecursive(80, 0, 80);
        totalCost[4] = Util.getTotalCost();
        System.out.println("cost 80: " + Util.getTotalCost());
        ProfileData.resetCounters();
        
        assertTrue(totalCost[0]*4 >= totalCost[1]);
        assertTrue(totalCost[1]*4 >= totalCost[2]);
        assertTrue(totalCost[2]*4 >= totalCost[3]);
        assertTrue(totalCost[3]*4 >= totalCost[4]);
        assertTrue(totalCost[4]*4 >= totalCost[5]);
        
    }
    
}
