package fi.lolcatz.jproffa.test;

import static org.junit.Assert.*;

import fi.lolcatz.jproffa.implementations.IntegerImpl;
import fi.lolcatz.jproffa.testproject.ThreadCreation;
import fi.lolcatz.profiledata.ProfileData;
import fi.lolcatz.profiler.ClassBlacklist;
import fi.lolcatz.profiler.ComplexityAnalysis;
import fi.lolcatz.profiler.Util;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;

public class ThreadCreationIsDisallowed {

    @BeforeClass
    public static void setUpClass() {
        ClassBlacklist.add(ComplexityQuadraticTest.class);
        Util.loadAgent();
        ProfileData.disallowProfiling();
    }

    @Test
    public void threadCreateTest() {
        try {
            ProfileData.resetCounters();
            ProfileData.allowProfiling();
            ThreadCreation.createThreadAndStart();
        } catch (RuntimeException ex) {
            ProfileData.disallowProfiling();
            return;
        }
        ProfileData.disallowProfiling();
        fail("Thread creation was allowed in user code");
    }
}
