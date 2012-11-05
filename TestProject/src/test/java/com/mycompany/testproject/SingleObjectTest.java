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
 * @author oorissan
 */
public class SingleObjectTest {
    
    @BeforeClass
    public static void classSetup() {
        ClassBlacklist.add(SingleObjectTest.class);
//        Example.main(null);
//        // Used to initialize the method, creating objects for the first time causes problems with profiler
//        ObjectExample.createPersons(1);
        Util.loadAgent();      
        ProfileData.initialize();
        RootLogger.setLoggingLevel(Level.OFF);
    }

    @Before
    public void testSetup() {
        ProfileData.resetCounters();
    }
    

    @Test
    public void testSingleObjectBehavesDeterministic() {
        SingleObjectExample.createPerson();
        long totalCost = ProfileData.getTotalCost();
        ProfileData.printBasicBlocksCost(false);
        System.out.println("Total cost was " + totalCost);
        ProfileData.resetCounters();
        SingleObjectExample.createPerson();
        long totalCost2 = ProfileData.getTotalCost();
        ProfileData.printBasicBlocksCost(false);
        System.out.println("Total cost was " + totalCost2);
        assertEquals(totalCost2, totalCost);
    }
}