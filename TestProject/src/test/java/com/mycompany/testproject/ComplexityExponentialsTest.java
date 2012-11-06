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
public class ComplexityExponentialsTest {
    
    public ComplexityExponentialsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        ClassBlacklist.add(ComplexityExponentialsTest.class);
        Util.loadAgent();      
        ProfileData.initialize();
        RootLogger.setLoggingLevel(Level.OFF);
    }
    
    @Before
    public void testSetup() {
        ProfileData.resetCounters();
    }
    
    @Test
    public void testExponential() {
        long[] totalCost = new long[5];
        
        ComplexityExample.exponentialLoop(5);
        ProfileData.getTotalCost();
        System.out.println("cost 5 " + ProfileData.getTotalCost());
        long total5 = ProfileData.getTotalCost();
        ProfileData.resetCounters();
        
        
        ComplexityExample.exponentialLoop(10);
        totalCost[0] = ProfileData.getTotalCost();
        System.out.println("cost 10 " + ProfileData.getTotalCost());
        ProfileData.resetCounters();
        ComplexityExample.exponentialLoop(20);
        totalCost[1] = ProfileData.getTotalCost();
        System.out.println("cost 20 " +  ProfileData.getTotalCost());
        ProfileData.resetCounters();
        ComplexityExample.exponentialLoop(40);
        totalCost[2] = ProfileData.getTotalCost();
        System.out.println("cost 40 " +  ProfileData.getTotalCost());
        ProfileData.resetCounters();
        ComplexityExample.exponentialLoop(80);
        totalCost[3] = ProfileData.getTotalCost();
        System.out.println("cost 80 " +  ProfileData.getTotalCost());
        ProfileData.resetCounters();
        ComplexityExample.exponentialLoop(160);
        System.out.println("cost 160 " +  ProfileData.getTotalCost());
        totalCost[4] = ProfileData.getTotalCost();
        ProfileData.resetCounters();
        System.out.println((total5 - 8 - 25)*4);
        assertTrue((total5 -8 - 25)*4  == (totalCost[0] - 8));
        assertTrue((totalCost[0] - 8 - 50)*4 == totalCost[1] - 8);
        assertTrue((totalCost[1] - 8 - 100)*4 == totalCost[2] - 8);
        assertTrue((totalCost[2] - 8 - 200)*4 == totalCost[3] - 8);
        assertTrue((totalCost[3] - 8 - 400)*4 == totalCost[4] - 8);
    }
    
    @Test
    public void testCoinFlipExponential() {
        long[] totalCost = new long[5];
        
        ComplexityExample.exponentialCoinFlipLoop(5);
        ProfileData.getTotalCost();
        System.out.println("coin flip cost 5 " + ProfileData.getTotalCost());
        long total5 = ProfileData.getTotalCost();
        ProfileData.resetCounters();
        
        ComplexityExample.exponentialCoinFlipLoop(10);
        totalCost[0] = ProfileData.getTotalCost();
        System.out.println("coin flip cost 10 " + ProfileData.getTotalCost());
        ProfileData.resetCounters();
        ComplexityExample.exponentialCoinFlipLoop(20);
        totalCost[1] = ProfileData.getTotalCost();
        System.out.println("coin flip cost 20 " +  ProfileData.getTotalCost());
        ProfileData.resetCounters();
        ComplexityExample.exponentialCoinFlipLoop(40);
        totalCost[2] = ProfileData.getTotalCost();
        System.out.println("coin flip cost 40 " +  ProfileData.getTotalCost());
        ProfileData.resetCounters();
        ComplexityExample.exponentialCoinFlipLoop(80);
        totalCost[3] = ProfileData.getTotalCost();
        System.out.println("coin flip cost 80 " +  ProfileData.getTotalCost());
        ProfileData.resetCounters();
        ComplexityExample.exponentialCoinFlipLoop(160);
        System.out.println("coin flip cost 160 " +  ProfileData.getTotalCost());
        totalCost[4] = ProfileData.getTotalCost();
        ProfileData.resetCounters();
        
        
        assertTrue((total5 -12 - 25)*4  == (totalCost[0] - 12));
        assertTrue((totalCost[0] - 12 - 50)*4 == totalCost[1] - 12);
        assertTrue((totalCost[1] - 12 - 100)*4 == totalCost[2] - 12);
        assertTrue((totalCost[2] - 12 - 200)*4 == totalCost[3] - 12);
        assertTrue((totalCost[3] - 12 - 400)*4 == totalCost[4] - 12);
    }
}
