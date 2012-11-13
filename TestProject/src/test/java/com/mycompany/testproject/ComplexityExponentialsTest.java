package com.mycompany.testproject;

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
        System.out.println("cost 5 " + Util.getTotalCost());
        totalCost[0] = Util.getTotalCost();
        ProfileData.resetCounters();

        IterativeComplexityExample.squaredFunction(10);
        System.out.println("cost 10 " + Util.getTotalCost());
        totalCost[1] = Util.getTotalCost();
        ProfileData.resetCounters();

        IterativeComplexityExample.squaredFunction(20);
        System.out.println("cost 20 " + Util.getTotalCost());
        totalCost[2] = Util.getTotalCost();
        ProfileData.resetCounters();

        IterativeComplexityExample.squaredFunction(40);
        System.out.println("cost 40 " + Util.getTotalCost());
        totalCost[3] = Util.getTotalCost();
        ProfileData.resetCounters();

        IterativeComplexityExample.squaredFunction(80);
        System.out.println("cost 80 " + Util.getTotalCost());
        totalCost[4] = Util.getTotalCost();
        ProfileData.resetCounters();

        IterativeComplexityExample.squaredFunction(160);
        System.out.println("cost 160 " + Util.getTotalCost());
        totalCost[5] = Util.getTotalCost();
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

        IterativeComplexityExample.approximatedSquaredFunction(5);
        System.out.println("cost 5 " + Util.getTotalCost());
        long total5 = Util.getTotalCost();
        ProfileData.resetCounters();


        IterativeComplexityExample.approximatedSquaredFunction(10);
        totalCost[0] = Util.getTotalCost();
        System.out.println("cost 10 " + Util.getTotalCost());
        ProfileData.resetCounters();
        IterativeComplexityExample.approximatedSquaredFunction(20);
        totalCost[1] = Util.getTotalCost();
        System.out.println("cost 20 " + Util.getTotalCost());
        ProfileData.resetCounters();
        IterativeComplexityExample.approximatedSquaredFunction(40);
        totalCost[2] = Util.getTotalCost();
        System.out.println("cost 40 " + Util.getTotalCost());
        ProfileData.resetCounters();
        IterativeComplexityExample.approximatedSquaredFunction(80);
        totalCost[3] = Util.getTotalCost();
        System.out.println("cost 80 " + Util.getTotalCost());
        ProfileData.resetCounters();
        IterativeComplexityExample.approximatedSquaredFunction(160);
        System.out.println("cost 160 " + Util.getTotalCost());
        totalCost[4] = Util.getTotalCost();
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

        IterativeComplexityExample.squaredCoinFlipFunction(5);
        Util.getTotalCost();
        System.out.println("coin flip cost 5 " + Util.getTotalCost());
        long total5 = Util.getTotalCost();
        ProfileData.resetCounters();

        IterativeComplexityExample.squaredCoinFlipFunction(10);
        totalCost[0] = Util.getTotalCost();
        System.out.println("coin flip cost 10 " + Util.getTotalCost());
        ProfileData.resetCounters();
        IterativeComplexityExample.squaredCoinFlipFunction(20);
        totalCost[1] = Util.getTotalCost();
        System.out.println("coin flip cost 20 " + Util.getTotalCost());
        ProfileData.resetCounters();
        IterativeComplexityExample.squaredCoinFlipFunction(40);
        totalCost[2] = Util.getTotalCost();
        System.out.println("coin flip cost 40 " + Util.getTotalCost());
        ProfileData.resetCounters();
        IterativeComplexityExample.squaredCoinFlipFunction(80);
        totalCost[3] = Util.getTotalCost();
        System.out.println("coin flip cost 80 " + Util.getTotalCost());
        ProfileData.resetCounters();
        IterativeComplexityExample.squaredCoinFlipFunction(160);
        System.out.println("coin flip cost 160 " + Util.getTotalCost());
        totalCost[4] = Util.getTotalCost();
        ProfileData.resetCounters();


        assertTrue(totalCost[0] * 4 >= totalCost[1]);
        assertTrue(totalCost[1] * 4 >= totalCost[2]);
        assertTrue(totalCost[2] * 4 >= totalCost[3]);
        assertTrue(totalCost[3] * 4 >= totalCost[4]);

        System.out.println("- - - - -");
    }
}
