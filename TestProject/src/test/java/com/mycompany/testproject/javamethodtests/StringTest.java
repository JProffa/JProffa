package com.mycompany.testproject.javamethodtests;

import com.mycompany.testproject.Example;
import com.mycompany.testproject.javamethodtests.StringImpl;
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
    }
    
    
    @Before
    public void testSetup() {
        impl = new StringImpl();
        impl.setClassName("com.mycompany.testproject.javamethods.StringExample");
        ProfileData.resetCounters();
    }
    
    @Test
    public void testStringReplaceLinear() {
        impl.setMethodName("stringReplace");
        impl.run(impl.getInput(25), "a", "b");
        
        long[] totalCost = new long[5];
        int syote = 100;
        
        totalCost[0] = impl.run(impl.getInput(syote), "a", "b");
        
        for (int i = 1; i < totalCost.length; i++) {
            syote = 2*syote;
            totalCost[i] = impl.run(impl.getInput(syote), "a", "b");    
        }
        
        printResults("testStringReplaceLinear", totalCost);
        
        assertTrue(totalCost[0]*2 >= totalCost[1]);
        assertTrue(totalCost[1]*2 >= totalCost[2]);
        assertTrue(totalCost[2]*2 >= totalCost[3]);
        assertTrue(totalCost[3]*2 >= totalCost[4]);
    }
    
    
    @Test
    public void testStringReplaceLinearLarge() {
        impl.setMethodName("stringReplace");
        impl.run(impl.getInput(25), "a", "b");
        
        long[] totalCost = new long[5];
        int syote = 10000;
        
        totalCost[0] = impl.run(impl.getInput(syote), "a", "b");
        
        for (int i = 1; i < totalCost.length; i++) {
            syote = 2*syote;
            totalCost[i] = impl.run(impl.getInput(syote), "a", "b");    
        }
        
        printResults("testStringReplaceLinearLarge", totalCost);
        
        assertTrue(totalCost[0]*2 >= totalCost[1]);
        assertTrue(totalCost[1]*2 >= totalCost[2]);
        assertTrue(totalCost[2]*2 >= totalCost[3]);
        assertTrue(totalCost[3]*2 >= totalCost[4]);
    }
    
    @Test
    public void testStringReplaceLinearHUGE() {
        impl.setMethodName("stringReplace");
        impl.run(impl.getInput(25), "a", "b");
        
        long[] totalCost = new long[5];
        int syote = 10000000;
        
        totalCost[0] = impl.run(impl.getInput(syote), "a", "b");
        
        for (int i = 1; i < totalCost.length; i++) {
            syote = 2*syote;
            totalCost[i] = impl.run(impl.getInput(syote), "a", "b");    
        }
        
        printResults("testStringReplaceLinearHUGE", totalCost);
        
        assertTrue(totalCost[0]*2 >= totalCost[1]);
        assertTrue(totalCost[1]*2 >= totalCost[2]);
        assertTrue(totalCost[2]*2 >= totalCost[3]);
        assertTrue(totalCost[3]*2 >= totalCost[4]);
    }
    
    @Test
    public void testStringReplaceIsDeterministic() {
        impl.setMethodName("stringReplace");
        impl.run("aaaaaaaaas", "s", "a");
        
        long total1 = impl.run("aaaaaaaaas", "s", "a");
        long total2 = impl.run("aaaaaaaaas", "s", "a");
        
        assertTrue(total1 > total2 -100 && total1 < total2 + 100);
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
