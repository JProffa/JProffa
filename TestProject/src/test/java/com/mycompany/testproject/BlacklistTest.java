package com.mycompany.testproject;

import com.mycompany.testproject.iteratives.IterativeExample;
import com.mycompany.testproject.recursives.RecursiveComplexityExample;
import fi.lolcatz.profiledata.ProfileData;
import fi.lolcatz.profiler.ClassBlacklist;
import fi.lolcatz.profiler.Util;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class BlacklistTest {
    
    public BlacklistTest() {
    }
    
    @BeforeClass
    public static void classSetup() {
        ClassBlacklist.add(BlacklistTest.class);
        Util.loadAgent();
    }
    
    @Before
    public void testSetup() {
        ProfileData.resetCounters();
    }
    
    @Test
    public void testProfileDataPackageBlacklist() {
        IterativeExample.iterativeFunction(5);
        List<String> blacklist = new ArrayList<String>();
        blacklist.add("com/mycompany/testproject/");
        long totalCost = Util.getTotalCost(blacklist);
        assertEquals(0, totalCost);
    }
    
    @Test
    public void testProfileDataClassBlacklist() {
        IterativeExample.iterativeFunction(5);
        List<String> blacklist = new ArrayList<String>();
        blacklist.add("com/mycompany/testproject/FunctionExample");
        long totalCost = Util.getTotalCost(blacklist);
        assertEquals(0, totalCost);
    }
    
    @Test
    public void testProfileDataMethodBlacklist() {
        IterativeExample.iterativeFunction(5);
        List<String> blacklist = new ArrayList<String>();
        blacklist.add("com/mycompany/testproject/FunctionExample.iterativeFunction");
        long totalCost = Util.getTotalCost(blacklist);
        assertEquals(0, totalCost);
        RecursiveComplexityExample.recursiveFunction(6);
        totalCost = Util.getTotalCost(blacklist);
        assertEquals(105, totalCost);
    }

}
