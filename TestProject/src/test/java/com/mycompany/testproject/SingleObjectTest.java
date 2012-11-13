package com.mycompany.testproject;

import com.mycompany.testproject.objects.SingleObjectExample;
import com.mycompany.testproject.objects.ObjectExample;
import fi.lolcatz.profiledata.ProfileData;
import fi.lolcatz.profiler.ClassBlacklist;
import fi.lolcatz.profiler.Util;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class SingleObjectTest {
    
    @BeforeClass
    public static void classSetup() {
        ClassBlacklist.add(SingleObjectTest.class);
        Example.main(null);
        // Used to initialize the method, creating objects for the first time causes problems with profiler
        ObjectExample.createPersons(1);
        Util.loadAgent();      
        ProfileData.initialize();
    }

    @Before
    public void testSetup() {
        ProfileData.resetCounters();
    }

    @Test
    public void testSingleObjectBehavesDeterministic() {
        SingleObjectExample.createPerson();
        long totalCost = Util.getTotalCost();
        Util.printBasicBlocksCost(false);
        System.out.println("Total cost was " + totalCost);
        ProfileData.resetCounters();
        SingleObjectExample.createPerson();
        long totalCost2 = Util.getTotalCost();
        Util.printBasicBlocksCost(false);
        System.out.println("Total cost was " + totalCost2);
        assertEquals(totalCost2, totalCost);
    }
}
