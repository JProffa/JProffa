package com.mycompany.testproject;

import fi.lolcatz.profiler.ClassBlacklist;
import fi.lolcatz.profiler.ProfileData;
import fi.lolcatz.profiler.Util;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ObjectTest {
    
    @BeforeClass
    public static void classSetup() {
        ClassBlacklist.add(ObjectTest.class);
//        Example.main(null);
//        // Used to initialize the method, creating objects for the first time causes problems with profiler
//        ObjectExample.createPersons(1);
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
        System.out.println("Total cost was " + totalCost);
        assertEquals(206, totalCost);
    }

    @Test
    public void testObjectsBehaveDeterministic() {
        ObjectExample.createPersons(10);
        long totalCost = ProfileData.getTotalCost();
        ProfileData.printBasicBlocksCost(false);
        System.out.println("Total cost was " + totalCost);
        assertEquals(206, totalCost);
        ProfileData.resetCounters();
        ObjectExample.createPersons(10);
        totalCost = ProfileData.getTotalCost();
        ProfileData.printBasicBlocksCost(false);
        System.out.println("Total cost was " + totalCost);
        assertEquals(206, totalCost);
    }
}
