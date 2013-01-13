package fi.lolcatz.jproffa.test;

import fi.lolcatz.jproffa.testproject.IterativeExample;
import fi.lolcatz.profiler.ClassBlacklist;
import fi.lolcatz.profiler.Util;
import fi.lolcatz.profiler.WithProfiling;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import org.junit.Rule;


public class BlacklistTest {
    
    @Rule public WithProfiling profiling = WithProfiling.rule();

    public BlacklistTest() {
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
    
    @Test
    public void testDoesntAddIdenticalEntriesTwice() {
        ClassBlacklist.add(this.getClass(), this.getClass());
        int count = 0;
        for (String entry : ClassBlacklist.getBlacklist()) {
            if (entry.equals(this.getClass().getName().replace(".", "/"))) {
                ++count;
            }
        }
        assertEquals(1, count);
    }

}
