package com.mycompany.testproject;

import fi.lolcatz.profiledata.ProfileData;
import fi.lolcatz.profiler.ClassBlacklist;
import fi.lolcatz.profiler.Util;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class BlacklistTest {
    
    public BlacklistTest() {
    }
    
    @BeforeClass
    public static void classSetup() {
        ClassBlacklist.add(BlacklistTest.class);
        Util.loadAgent();
        ProfileData.initialize(); 
    }
    
    @Before
    public void testSetup() {
        ProfileData.resetCounters();
    }
    
    @Test
    public void testProfileDataPackageBlacklist() {
        FunctionExample.iterativeFunction(5);
        List<String> blacklist = new ArrayList<String>();
        blacklist.add("com/mycompany/testproject/");
        long totalCost = Util.getTotalCost(blacklist);
        assertEquals(0, totalCost);
    }
    
    @Test
    public void testProfileDataClassBlacklist() {
        FunctionExample.iterativeFunction(5);
        List<String> blacklist = new ArrayList<String>();
        blacklist.add("com/mycompany/testproject/FunctionExample");
        long totalCost = Util.getTotalCost(blacklist);
        assertEquals(0, totalCost);
    }
    
    @Test
    public void testProfileDataMethodBlacklist() {
        FunctionExample.iterativeFunction(5);
        List<String> blacklist = new ArrayList<String>();
        blacklist.add("com/mycompany/testproject/FunctionExample.iterativeFunction");
        long totalCost = Util.getTotalCost(blacklist);
        assertEquals(0, totalCost);
        FunctionExample.recursiveFunction(6);
        totalCost = Util.getTotalCost(blacklist);
        assertEquals(105, totalCost);
    }

}
