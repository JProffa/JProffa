/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testproject.iterativeTests;

import com.mycompany.testproject.iteratives.IterativeComplexityExample;
import fi.lolcatz.profiler.ClassBlacklist;
import fi.lolcatz.profiler.Output;
import fi.lolcatz.profiler.TestingFramework;
import fi.lolcatz.profiler.Util;
import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class GraphTest {

    IntegerImpl impl;
    TestingFramework framework;

    @BeforeClass
    public static void classSetup() {
        System.out.println("Testing");
        long startTime = System.currentTimeMillis();
        IterativeComplexityExample.linearFunction(200000000);
        long endTime = System.currentTimeMillis();

        ClassBlacklist.add(ComplexityAndTimeLinearTest.class);
        Util.loadAgent();
        Logger.getRootLogger().setLevel(Level.OFF);
    }

    @Before
    public void testSetup() {
        impl = new IntegerImpl();
        impl.setClassName("com.mycompany.testproject.iteratives.IterativeComplexityExample");
    }

    @Test
    public void testGenerateOutput() throws Exception {
        impl.setMethodName("linearFunction");
        List<Integer> list = Arrays.asList(2, 4, 8, 16, 32, 64);
        Output<Integer> actual = impl.generateOutput(list);
        
        List<Integer> list2 = Arrays.asList(16, 32, 64);
        
        Output<Integer> param = impl.generateOutput(list2);
        for (Long l : actual.getTime()) {
            assertTrue(l > 0);
        }
        impl.drawGraph(actual, param);
        }
}
