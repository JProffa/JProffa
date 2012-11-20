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
        String basicBlockCostsString = Util.getBasicBlockCostsString(false, blacklist);
        assertFalse(basicBlockCostsString.contains("com/mycompany/testproject/"));
    }
    
    @Test
    public void testProfileDataClassBlacklist() {
        IterativeExample.iterativeFunction(5);
        List<String> blacklist = new ArrayList<String>();
        blacklist.add("com/mycompany/testproject/iteratives/IterativeExample");
        String basicBlockCostsString = Util.getBasicBlockCostsString(false, blacklist);
        assertFalse(basicBlockCostsString.contains("com/mycompany/testproject/iteratives/IterativeExample"));
    }
    
    @Test
    public void testProfileDataMethodBlacklist() {
        IterativeExample.iterativeFunction(5);
        List<String> blacklist = new ArrayList<String>();
        blacklist.add("com/mycompany/testproject/iteratives/IterativeExample.iterativeFunction");
        String basicBlockCostsString = Util.getBasicBlockCostsString(false, blacklist);
        assertFalse(basicBlockCostsString.contains("com/mycompany/testproject/iteratives/IterativeExample.iterativeFunction"));
        IterativeExample.factorialWhileFunction(6);
        basicBlockCostsString = Util.getBasicBlockCostsString(false, blacklist);
        assertTrue(basicBlockCostsString.contains("com/mycompany/testproject/iteratives/IterativeExample.factorialWhileFunction"));
    }

}
