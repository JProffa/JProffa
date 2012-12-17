package fi.lolcatz.jproffa.test;

import fi.lolcatz.jproffa.testproject.Example;
import fi.lolcatz.jproffa.testproject.NativeExample;
import fi.lolcatz.profiledata.ProfileData;
import fi.lolcatz.profiler.ClassBlacklist;
import fi.lolcatz.profiler.CostlyMethodList;
import fi.lolcatz.profiler.Util;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class NativeMethodCostTest {
    
    public NativeMethodCostTest() {
    }
    
    
    @BeforeClass
    public static void classSetup() {
        ClassBlacklist.add(NativeMethodCostTest.class);
        Example.main(null);
        Util.loadAgent();
    }
    
    @Before
    public void testSetup() {
        ProfileData.resetCounters();
    }
    
     @Test
     public void testNativeExample() throws IOException {
         NativeExample example = new NativeExample();
         example.writeSomething();
         long cost = Util.getTotalCost();
         Util.printBasicBlocksCost(false);
     }
     
     @Test
     public void testCostlyMethods() throws IOException {
         NativeExample example = new NativeExample();
         example.writeSomething();
         long cost1 = Util.getTotalCost();
         CostlyMethodList.setCostOfCostlyMethods(0);
         ProfileData.resetCounters();
         example = new NativeExample();
         example.writeSomething();
         long cost2 = Util.getTotalCost();
         assertTrue(cost1 > cost2);
         
         Util.printBasicBlocksCost(false);
         
     }
}