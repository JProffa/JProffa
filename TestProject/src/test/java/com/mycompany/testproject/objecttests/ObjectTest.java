package com.mycompany.testproject.objecttests;

import com.mycompany.testproject.Example;
import com.mycompany.testproject.iterativeTests.IntegerImpl;
import com.mycompany.testproject.objects.ObjectExample;
import fi.lolcatz.profiledata.ProfileData;
import fi.lolcatz.profiler.ClassBlacklist;
import fi.lolcatz.profiler.Util;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ObjectTest {
    
    IntegerImpl impl;
    
    @BeforeClass
    public static void classSetup() {
        ClassBlacklist.add(ObjectTest.class);
        Example.main(null);
        // Used to initialize the method, creating objects for the first time causes problems with profiler
        ObjectExample.createPersons(1);
        Util.loadAgent();      
        ProfileData.initialize();
    }

    @Before
    public void testSetup() {
        impl = new IntegerImpl();
        impl.setClassName("com.mycompany.testproject.objects.ObjectExample");
    }
    
    
    @Test
    public void testObjectsLinear() {
        
         impl.setMethodName("createPersons");
         
         impl.run(impl.getInput(1000));
        
        long[] totalCost = new long[5];
        int syote = 1000;
        
        totalCost[0] = impl.run(impl.getInput(syote));
        
        for (int i = 1; i < totalCost.length; i++) {
            syote = 2*syote;
            totalCost[i] = impl.run(impl.getInput(syote));    
        }
        
        assertTrue(totalCost[0]*2 >= totalCost[1]);
        assertTrue(totalCost[1]*2 >= totalCost[2]);
        assertTrue(totalCost[2]*2 >= totalCost[3]);
        assertTrue(totalCost[3]*2 >= totalCost[4]);
         
    }
     
    @Test
    public void testObjectsLinearLarge() {
        
         impl.setMethodName("createPersons");
         
         impl.run(impl.getInput(1000));
        
        long[] totalCost = new long[5];
        int syote = 100000;
        
        totalCost[0] = impl.run(impl.getInput(syote));
        
        for (int i = 1; i < totalCost.length; i++) {
            syote = 2*syote;
            totalCost[i] = impl.run(impl.getInput(syote));    
        }
        
        assertTrue(totalCost[0]*2 >= totalCost[1]);
        assertTrue(totalCost[1]*2 >= totalCost[2]);
        assertTrue(totalCost[2]*2 >= totalCost[3]);
        assertTrue(totalCost[3]*2 >= totalCost[4]);
         
    }
      
    @Test
    public void testObjectsLinearHuge() {
        
         impl.setMethodName("createPersons");
         
         impl.run(impl.getInput(1000));
        
        long[] totalCost = new long[5];
        int syote = 10000000;
        
        totalCost[0] = impl.run(impl.getInput(syote));
        
        for (int i = 1; i < totalCost.length; i++) {
            syote = 2*syote;
            totalCost[i] = impl.run(impl.getInput(syote));    
        }
        
        assertTrue(totalCost[0]*2 >= totalCost[1]);
        assertTrue(totalCost[1]*2 >= totalCost[2]);
        assertTrue(totalCost[2]*2 >= totalCost[3]);
        assertTrue(totalCost[3]*2 >= totalCost[4]);
         
    }

    @Test
    public void testObjectsBehaveDeterministic() {
        impl.setMethodName("createPersons");
        
        impl.run(impl.getInput(500));
        
        long first = impl.run(500);
        long second = impl.run(500);
        System.out.println("Margin of error: " + impl.getMarginOfError(first));
        assertTrue("Suorituskerrat eivät olleet 50 sisällä toisistaan", first > second-impl.getMarginOfError(first) && first < second+impl.getMarginOfError(first));
        
    }
}