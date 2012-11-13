package com.mycompany.testproject.iterativeTests;

import com.mycompany.testproject.Example;
import com.mycompany.testproject.iteratives.IterativeExample;
import fi.lolcatz.profiledata.ProfileData;
import fi.lolcatz.profiler.ClassBlacklist;
import fi.lolcatz.profiler.Util;
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
     * Test to determine if the function is deterministic. Allows for 25 bytecode
     * offset.
     */
    @Test
    public void testIterativeDeterministic() {
        
        impl.setMethodName("iterativeFunction");
        
        impl.run(impl.getInput(500));
        
        long first = impl.run(500);
        long second = impl.run(500);
        
        assertTrue("Suorituskerrat eivät olleet 50 sisällä toisistaan", first > second-50 && first < second+50);
        
    }
    
    @Test
    public void testForLoopsDeterministic() {
        
        impl.setMethodName("factorialForFunction");
        
        impl.run(impl.getInput(500));
        
        long first = impl.run(impl.getInput(500));
        long second = impl.run(impl.getInput(500));
        
        assertTrue("Suorituskerrat eivät olleet 50 sisällä toisistaan", first > second-50 && first < second+50);
    }
    
    @Test
    public void testWhileLoopsDeterministic() {
        
        impl.setMethodName("factorialWhileFunction");
        
        impl.run(impl.getInput(500));
        
        long first = impl.run(impl.getInput(500));
        long second = impl.run(impl.getInput(500));
        
        assertTrue("Suorituskerrat eivät olleet 50 sisällä toisistaan", first > second-50 && first < second+50);
    }
    
    @Test
    public void testForEachLoopsDeterministic() {
        
        impl.setMethodName("factorialForEachFunction");
        
        impl.run(impl.getInput(500));
        
        long first = impl.run(impl.getInput(500));
        long second = impl.run(impl.getInput(500));
        
        assertTrue("Suorituskerrat eivät olleet 50 sisällä toisistaan", first > second-50 && first < second+50);
    }
    
    
    

}
