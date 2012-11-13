package com.mycompany.testproject;

import com.mycompany.testproject.iterativeTests.StringImpl;
import com.mycompany.testproject.javamethods.StringExample;
import fi.lolcatz.profiledata.ProfileData;
import fi.lolcatz.profiler.ClassBlacklist;
import fi.lolcatz.profiler.Util;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class StringTest {
    
    StringImpl impl;
    
    public StringTest() {
    }
    
    @BeforeClass
    public static void classSetup() {
        ClassBlacklist.add(StringTest.class);
        Example.main(null);
        Util.loadAgent();      
        ProfileData.initialize(); 
        // Method needs to be initialized before using, the object call problem.
        StringExample.stringReplace("aaaaaaaaas", "s", "a");
    }
    
    
    @Before
    public void testSetup() {
        impl = new StringImpl();
        impl.setClassName("com.mycompany.testproject.javamethods.StringExample");
        ProfileData.resetCounters();
    }
    
    @Test
    public void testStringReplace() {
        impl.setMethodName("stringReplace");
        impl.run(impl.getInput(25), "a", "b");
        long totalCost = Util.getTotalCost();
        assertEquals(7, totalCost);
    }
    
    @Test
    public void testStringReplaceIsDeterministic() {
        impl.setMethodName("stringReplace");
        impl.run("aaaaaaaaas", "s", "a");
        long totalCost = Util.getTotalCost();
        assertEquals(7, totalCost);
        ProfileData.resetCounters();
        impl.run("aaaaaaaaas", "s", "a");
        totalCost = Util.getTotalCost();
        assertEquals(7, totalCost);
    }
    
    @Test
    /**
     * should be about linear compared to to testStringReplaceCostTen()
     */
    public void testStringReplaceCostHundred() {
        impl.setMethodName("stringReplace");
        impl.run("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaas", "s", "a");
        long totalCost = Util.getTotalCost();
        assertEquals(7, totalCost);
    }
}
