package com.mycompany.testproject.iterativeTests;

import com.mycompany.testproject.Example;
import com.mycompany.testproject.iteratives.IterativeExample;
import fi.lolcatz.profiledata.ProfileData;
import fi.lolcatz.profiler.ClassBlacklist;
import fi.lolcatz.profiler.ComplexityCost;
import fi.lolcatz.profiler.Output;
import fi.lolcatz.profiler.Util;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class IterativeTest {

    IntegerImpl impl;

    public IterativeTest() {
    }

    
    @BeforeClass
    public static void classSetup() {
        ClassBlacklist.add(IterativeTest.class);
        Example.main(null);
        Util.loadAgent();
        ProfileData.initialize();
    }

    @Before
    public void testSetup() {
        impl = new IntegerImpl();
        impl.setClassName("com.mycompany.testproject.iteratives.IterativeExample");
    }

    
    /**
     * Test to determine if the function is deterministic. Allows for 25 bytecode offset.
     */
    @Test
    public void testIterativeDeterministic() throws Exception {

        impl.setMethodName("iterativeFunction");

        impl.run(impl.getInput(500));

        long first = impl.run(500);
        long second = impl.run(500);

        long marginError = impl.getMarginOfError(first);
        
        assertTrue(first != 0 && second != 0);
        assertTrue("Suorituskerrat eivät olleet 50 sisällä toisistaan", first > second - marginError && first < second + marginError);

    }

    @Test
    public void testForLoopsDeterministic() throws Exception {

        impl.setMethodName("factorialForFunction");

        impl.run(impl.getInput(500));

        long first = impl.run(impl.getInput(500));
        long second = impl.run(impl.getInput(500));

        long marginError = impl.getMarginOfError(first);

        assertTrue(first != 0 && second != 0);
        assertTrue("Suorituskerrat eivät olleet 50 sisällä toisistaan", first > second - marginError && first < second + marginError);
    }

    @Test
    public void testWhileLoopsDeterministic() throws Exception {

        impl.setMethodName("factorialWhileFunction");

        impl.run(impl.getInput(500));

        long first = impl.run(impl.getInput(500));
        long second = impl.run(impl.getInput(500));

        long marginError = impl.getMarginOfError(first);

        assertTrue(first != 0 && second != 0);
        assertTrue("Suorituskerrat eivät olleet 50 sisällä toisistaan", first > second - marginError && first < second + marginError);
    }

    @Test
    public void testForEachLoopsDeterministic() throws Exception {

        impl.setMethodName("factorialForEachFunction");

        impl.run(impl.getInput(500));

        long first = impl.run(impl.getInput(500));
        long second = impl.run(impl.getInput(500));

        long marginError = impl.getMarginOfError(first);        
        
        assertTrue(first != 0 && second != 0);
        assertTrue("Suorituskerrat eivät olleet 50 sisällä toisistaan", first > second - marginError && first < second + marginError);
    }
}
