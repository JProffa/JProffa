package com.mycompany.testproject;

import com.mycompany.testproject.iterativeTests.ComplexityAndTimeLinearTest;
import com.mycompany.testproject.iterativeTests.ComplexityAndTimeLinearTest;
import com.mycompany.testproject.iterativeTests.IntegerImpl;
import com.mycompany.testproject.iterativeTests.IntegerImpl;
import com.mycompany.testproject.iteratives.IterativeComplexityExample;
import fi.lolcatz.profiledata.ProfileData;
import fi.lolcatz.profiler.ClassBlacklist;
import fi.lolcatz.profiler.ComplexityAnalysis;
import fi.lolcatz.profiler.Output;
import fi.lolcatz.profiler.Util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import jproffa.graph.GraphUI;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class GraphTest {

    IntegerImpl impl;
    ComplexityAnalysis framework;
    GraphUI ui;

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
    public void testCreateGraph() throws Exception {
        impl.setMethodName("linearFunction");
        List<Integer> list = Arrays.asList(2, 4, 8, 16, 32, 64);
        Output<Integer> actual = impl.runMethod(list);

        List<Integer> list2 = Arrays.asList(16, 32, 64);

        Output<Integer> param = impl.runMethod(list2);
        for (Long l : actual.getTime()) {
            assertTrue(l > 0);
        }
        ui.saveDataToFile(actual.getTime(), actual.getInput(), "Graph", "DataFile");
        ui.saveGraphFromFile("Graph", "DataFile", "newGraphFile2");
    }
        
    @Test
    public void testCreateLinearAndSquaredGraph() throws Exception {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i < 64; i++) {
            list.add(i);

        }
        impl.setMethodName("linearFunction");
        
        Output<Integer> actual = impl.runMethod(list);
        
        impl.setMethodName("squaredFunction");
        
        Output<Integer> param = impl.runMethod(list);

        for (Long l : actual.getTime()) {
            assertTrue(l > 0);
        }
        for (Long l : param.getTime()) {
            assertTrue(l > 0);
        }
    }
}
