package net.jproffa.test;

import net.jproffa.testproject.IterativeExample;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import net.jproffa.profiler.ClassBlacklist;
import net.jproffa.profiler.Util;
import net.jproffa.profiler.WithProfiling;

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
        blacklist.add("net/jproffa/testproject/");
        String basicBlockCostsString = Util.getBasicBlockCostsString(false, blacklist);
        assertFalse(basicBlockCostsString.contains("com/mycompany/testproject/"));
    }

    @Test
    public void testProfileDataClassBlacklist() {
        IterativeExample.iterativeFunction(5);
        List<String> blacklist = new ArrayList<String>();
        blacklist.add("net/jproffa/testproject/IterativeExample");
        String basicBlockCostsString = Util.getBasicBlockCostsString(false, blacklist);
        assertFalse(basicBlockCostsString.contains("net/jproffa/testproject/IterativeExample"));
    }

    @Test
    public void testProfileDataMethodBlacklist() {
        IterativeExample.iterativeFunction(5);
        List<String> blacklist = new ArrayList<String>();
        blacklist.add("net/jproffa/testproject/IterativeExample.iterativeFunction");
        String basicBlockCostsString = Util.getBasicBlockCostsString(false, blacklist);
        assertFalse(basicBlockCostsString.contains("net/jproffa/testproject/IterativeExample.iterativeFunction"));
        
        IterativeExample.factorialWhileFunction(6);
        basicBlockCostsString = Util.getBasicBlockCostsString(false, blacklist);
        assertTrue(basicBlockCostsString.contains("net/jproffa/testproject/IterativeExample.factorialWhileFunction"));
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
