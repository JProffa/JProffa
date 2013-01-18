package net.jproffa.test;

import net.jproffa.testproject.NativeExample;
import net.jproffa.profiledata.ProfileData;
import org.junit.Test;

import java.io.IOException;
import net.jproffa.profiler.CostlyMethodList;
import net.jproffa.profiler.Util;
import net.jproffa.profiler.WithProfiling;

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
