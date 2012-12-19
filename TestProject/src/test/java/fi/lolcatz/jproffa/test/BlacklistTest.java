package fi.lolcatz.jproffa.test;

import fi.lolcatz.jproffa.testproject.IterativeExample;
import fi.lolcatz.profiledata.ProfileData;
import fi.lolcatz.profiler.ClassBlacklist;
import fi.lolcatz.profiler.Util;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


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
        blacklist.add("fi/lolcatz/jproffa/testproject/");
        String basicBlockCostsString = Util.getBasicBlockCostsString(false, blacklist);
        assertFalse(basicBlockCostsString.contains("com/mycompany/testproject/"));
    }
    
    @Test
    public void testProfileDataClassBlacklist() {
        IterativeExample.iterativeFunction(5);
        List<String> blacklist = new ArrayList<String>();
        blacklist.add("fi/lolcatz/jproffa/testproject/IterativeExample");
        String basicBlockCostsString = Util.getBasicBlockCostsString(false, blacklist);
        assertFalse(basicBlockCostsString.contains("fi/lolcatz/jproffa/testproject/IterativeExample"));
    }
    
    @Test
    public void testProfileDataMethodBlacklist() {
        IterativeExample.iterativeFunction(5);
        List<String> blacklist = new ArrayList<String>();
        blacklist.add("fi/lolcatz/jproffa/testproject/IterativeExample.iterativeFunction");
        String basicBlockCostsString = Util.getBasicBlockCostsString(false, blacklist);
        assertFalse(basicBlockCostsString.contains("fi/lolcatz/jproffa/testproject/IterativeExample.iterativeFunction"));
        IterativeExample.factorialWhileFunction(6);
        basicBlockCostsString = Util.getBasicBlockCostsString(false, blacklist);
        assertTrue(basicBlockCostsString.contains("fi/lolcatz/jproffa/testproject/IterativeExample.factorialWhileFunction"));
    }

}
