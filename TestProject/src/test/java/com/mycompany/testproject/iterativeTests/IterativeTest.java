package com.mycompany.testproject.iterativeTests;

import com.mycompany.testproject.Example;
import com.mycompany.testproject.iteratives.IterativeExample;
import fi.lolcatz.profiledata.ProfileData;
import fi.lolcatz.profiler.ClassBlacklist;
import fi.lolcatz.profiler.Util;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class IterativeTest {
    
    IntegerImpl impl;
    
    public IterativeTest() {
    }
    
    
    @BeforeClass
    public static void classSetup() {
        ClassBlacklist.add(IterativeTest.class);
        Example.main(null);
        Util.loadAgent();      
        ProfileData.initialize(); 
    } 
    
    @Before
    public void testSetup() {
        impl = new IntegerImpl();
    }
    
    @Test
    public void testIterativeCostIsDeterministic() {
        impl.setClassName("IterativeExample");
        impl.setMethodName("iterativeFunction");
        long first = impl.run(impl.getInput(5));
        long second = impl.run(impl.getInput(5));
        assertEquals(second, first);
    }

}
