package com.mycompany.testproject;

import fi.lolcatz.profiler.ClassBlacklist;
import fi.lolcatz.profiler.ProfileData;
import fi.lolcatz.profiler.Util;
import java.io.IOException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class NativeTest {
    
    public NativeTest() {
    }
    
    
    @BeforeClass
    public static void classSetup() {
        ClassBlacklist.add(NativeTest.class);
        Example.main(null);
        Util.loadAgent();      
        ProfileData.initialize(); 
    }
    
    @Before
    public void testSetup() {
        ProfileData.resetCounters();
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
     public void hello() throws IOException {
         NativeExample example = new NativeExample();
         example.writeSomething();
         long cost = ProfileData.getTotalCost();
         ProfileData.printBasicBlocksCost(false);
     }
}
