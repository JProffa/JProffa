package com.mycompany.testproject.iterativeTests;

import com.mycompany.testproject.iteratives.IterativeComplexityExample;
import fi.lolcatz.profiledata.ProfileData;
import fi.lolcatz.profiler.ClassBlacklist;
import fi.lolcatz.profiler.Util;
import org.apache.log4j.Logger;
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
        Logger.getRootLogger().setLevel(org.apache.log4j.Level.OFF);
    }

    @Before
    public void testSetup() {
        ProfileData.resetCounters();
    }

    
    @Test
    public void squaredTest() {
        long[] totalCost = new long[6];

        IterativeComplexityExample.squaredFunction(5);
        totalCost[0] = Util.getTotalCost();
        ProfileData.resetCounters();

        IterativeComplexityExample.squaredFunction(10);
        totalCost[1] = Util.getTotalCost();
        ProfileData.resetCounters();

        IterativeComplexityExample.squaredFunction(20);
        totalCost[2] = Util.getTotalCost();
        ProfileData.resetCounters();

        IterativeComplexityExample.squaredFunction(40);
        totalCost[3] = Util.getTotalCost();
        ProfileData.resetCounters();

        IterativeComplexityExample.squaredFunction(80);
        totalCost[4] = Util.getTotalCost();
        ProfileData.resetCounters();

        IterativeComplexityExample.squaredFunction(160);
        totalCost[5] = Util.getTotalCost();
        ProfileData.resetCounters();
        
        printResults("--- testSquared ---", totalCost);

        assertTrue(totalCost[0] * 4 >= totalCost[1]);
        assertTrue(totalCost[1] * 4 >= totalCost[2]);
        assertTrue(totalCost[2] * 4 >= totalCost[3]);
        assertTrue(totalCost[3] * 4 >= totalCost[4]);
        assertTrue(totalCost[4] * 4 >= totalCost[5]);
        System.out.println("- - - - -");

    }

    @Test
    public void testApproximatedSquared() {
        long[] totalCost = new long[5];

        IterativeComplexityExample.approximatedSquaredFunction(5);
        System.out.println("cost 5 " + Util.getTotalCost());
        long total5 = Util.getTotalCost();
        ProfileData.resetCounters();


        IterativeComplexityExample.approximatedSquaredFunction(10);
        totalCost[0] = Util.getTotalCost();
        ProfileData.resetCounters();
        IterativeComplexityExample.approximatedSquaredFunction(20);
        totalCost[1] = Util.getTotalCost();
        ProfileData.resetCounters();
        IterativeComplexityExample.approximatedSquaredFunction(40);
        totalCost[2] = Util.getTotalCost();
        ProfileData.resetCounters();
        IterativeComplexityExample.approximatedSquaredFunction(80);
        totalCost[3] = Util.getTotalCost();
        ProfileData.resetCounters();
        IterativeComplexityExample.approximatedSquaredFunction(160);
        totalCost[4] = Util.getTotalCost();
        ProfileData.resetCounters();
        
        printResults("--- testApproximatedSquared ---", totalCost);
        assertTrue(totalCost[0] * 4 >= totalCost[1]);
        assertTrue(totalCost[1] * 4 >= totalCost[2]);
        assertTrue(totalCost[2] * 4 >= totalCost[3]);
        assertTrue(totalCost[3] * 4 >= totalCost[4]);

        

    }

    @Test
    public void testCoinFlipExponential() {
        long[] totalCost = new long[5];

        IterativeComplexityExample.squaredCoinFlipFunction(5);
        Util.getTotalCost();
        long total5 = Util.getTotalCost();
        ProfileData.resetCounters();

        IterativeComplexityExample.squaredCoinFlipFunction(10);
        totalCost[0] = Util.getTotalCost();
        ProfileData.resetCounters();
        IterativeComplexityExample.squaredCoinFlipFunction(20);
        totalCost[1] = Util.getTotalCost();
        ProfileData.resetCounters();
        IterativeComplexityExample.squaredCoinFlipFunction(40);
        totalCost[2] = Util.getTotalCost();
        ProfileData.resetCounters();
        IterativeComplexityExample.squaredCoinFlipFunction(80);
        totalCost[3] = Util.getTotalCost();
        ProfileData.resetCounters();
        IterativeComplexityExample.squaredCoinFlipFunction(160);
        totalCost[4] = Util.getTotalCost();
        ProfileData.resetCounters();


        printResults("--- testCoinFlipSquared ---", totalCost);
        
        assertTrue(totalCost[0] * 4 >= totalCost[1]);
        assertTrue(totalCost[1] * 4 >= totalCost[2]);
        assertTrue(totalCost[2] * 4 >= totalCost[3]);
        assertTrue(totalCost[3] * 4 >= totalCost[4]);

        System.out.println("- - - - -");
    }
    public void printResults(String testname, long[] results) {
        System.out.println("---" + testname + "---");
        int i = 0;
        for (long l : results) {
            i++;
            System.out.println(i + ": " + l);
        }
    }
}
