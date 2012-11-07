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
    public void squaredTest() {
        long[] totalCost = new long[6];

        IterativeComplexityExample.squaredLoop(5);
        System.out.println("cost 5 " + ProfileData.getTotalCost());
        totalCost[0] = ProfileData.getTotalCost();
        ProfileData.resetCounters();

        IterativeComplexityExample.squaredLoop(10);
        System.out.println("cost 10 " + ProfileData.getTotalCost());
        totalCost[1] = ProfileData.getTotalCost();
        ProfileData.resetCounters();

        IterativeComplexityExample.squaredLoop(20);
        System.out.println("cost 20 " + ProfileData.getTotalCost());
        totalCost[2] = ProfileData.getTotalCost();
        ProfileData.resetCounters();

        IterativeComplexityExample.squaredLoop(40);
        System.out.println("cost 40 " + ProfileData.getTotalCost());
        totalCost[3] = ProfileData.getTotalCost();
        ProfileData.resetCounters();

        IterativeComplexityExample.squaredLoop(80);
        System.out.println("cost 80 " + ProfileData.getTotalCost());
        totalCost[4] = ProfileData.getTotalCost();
        ProfileData.resetCounters();

        IterativeComplexityExample.squaredLoop(160);
        System.out.println("cost 160 " + ProfileData.getTotalCost());
        totalCost[5] = ProfileData.getTotalCost();
        ProfileData.resetCounters();

        assertTrue(totalCost[0] * 4 >= totalCost[1]);
        assertTrue(totalCost[1] * 4 >= totalCost[2]);
        assertTrue(totalCost[2] * 4 >= totalCost[3]);
        assertTrue(totalCost[3] * 4 >= totalCost[4]);
        assertTrue(totalCost[4] * 4 >= totalCost[5]);
        System.out.println("- - - - -");

    }

    @Test
    public void testExponential() {
        long[] totalCost = new long[5];

        IterativeComplexityExample.exponentialLoop(5);
        System.out.println("cost 5 " + ProfileData.getTotalCost());
        long total5 = ProfileData.getTotalCost();
        ProfileData.resetCounters();


        IterativeComplexityExample.exponentialLoop(10);
        totalCost[0] = ProfileData.getTotalCost();
        System.out.println("cost 10 " + ProfileData.getTotalCost());
        ProfileData.resetCounters();
        IterativeComplexityExample.exponentialLoop(20);
        totalCost[1] = ProfileData.getTotalCost();
        System.out.println("cost 20 " + ProfileData.getTotalCost());
        ProfileData.resetCounters();
        IterativeComplexityExample.exponentialLoop(40);
        totalCost[2] = ProfileData.getTotalCost();
        System.out.println("cost 40 " + ProfileData.getTotalCost());
        ProfileData.resetCounters();
        IterativeComplexityExample.exponentialLoop(80);
        totalCost[3] = ProfileData.getTotalCost();
        System.out.println("cost 80 " + ProfileData.getTotalCost());
        ProfileData.resetCounters();
        IterativeComplexityExample.exponentialLoop(160);
        System.out.println("cost 160 " + ProfileData.getTotalCost());
        totalCost[4] = ProfileData.getTotalCost();
        ProfileData.resetCounters();
        System.out.println((total5 - 8 - 25) * 4);
        assertTrue(totalCost[0] * 4 >= totalCost[1]);
        assertTrue(totalCost[1] * 4 >= totalCost[2]);
        assertTrue(totalCost[2] * 4 >= totalCost[3]);
        assertTrue(totalCost[3] * 4 >= totalCost[4]);

        System.out.println("- - - - -");
    }

    @Test
    public void testCoinFlipExponential() {
        long[] totalCost = new long[5];

        IterativeComplexityExample.exponentialCoinFlipLoop(5);
        ProfileData.getTotalCost();
        System.out.println("coin flip cost 5 " + ProfileData.getTotalCost());
        long total5 = ProfileData.getTotalCost();
        ProfileData.resetCounters();

        IterativeComplexityExample.exponentialCoinFlipLoop(10);
        totalCost[0] = ProfileData.getTotalCost();
        System.out.println("coin flip cost 10 " + ProfileData.getTotalCost());
        ProfileData.resetCounters();
        IterativeComplexityExample.exponentialCoinFlipLoop(20);
        totalCost[1] = ProfileData.getTotalCost();
        System.out.println("coin flip cost 20 " + ProfileData.getTotalCost());
        ProfileData.resetCounters();
        IterativeComplexityExample.exponentialCoinFlipLoop(40);
        totalCost[2] = ProfileData.getTotalCost();
        System.out.println("coin flip cost 40 " + ProfileData.getTotalCost());
        ProfileData.resetCounters();
        IterativeComplexityExample.exponentialCoinFlipLoop(80);
        totalCost[3] = ProfileData.getTotalCost();
        System.out.println("coin flip cost 80 " + ProfileData.getTotalCost());
        ProfileData.resetCounters();
        IterativeComplexityExample.exponentialCoinFlipLoop(160);
        System.out.println("coin flip cost 160 " + ProfileData.getTotalCost());
        totalCost[4] = ProfileData.getTotalCost();
        ProfileData.resetCounters();


        assertTrue(totalCost[0] * 4 >= totalCost[1]);
        assertTrue(totalCost[1] * 4 >= totalCost[2]);
        assertTrue(totalCost[2] * 4 >= totalCost[3]);
        assertTrue(totalCost[3] * 4 >= totalCost[4]);

        System.out.println("- - - - -");
    }
}
