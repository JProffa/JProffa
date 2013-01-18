package fi.lolcatz.jproffa.test;

import fi.lolcatz.jproffa.testproject.NativeExample;
import fi.lolcatz.profiledata.ProfileData;
import fi.lolcatz.profiler.CostlyMethodList;
import fi.lolcatz.profiler.Util;
import fi.lolcatz.profiler.WithProfiling;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;
import org.junit.Rule;

public class NativeMethodCostTest {

    @Rule public WithProfiling profiling = WithProfiling.rule();

    @Test
    public void testNativeExample() throws IOException {
        NativeExample example = new NativeExample();
        example.writeSomething();
        Util.printBasicBlocksCost(false);
    }

    @Test
    public void testCostlyMethods() throws IOException {
        NativeExample example = new NativeExample();
        example.writeSomething();
        long cost1 = Util.getTotalCost();
        CostlyMethodList.setCostOfCostlyMethods(0);
        ProfileData.resetCounters();
        
        example.writeSomething();
        long cost2 = Util.getTotalCost();
        assertTrue(cost1 > cost2);
    }
}
