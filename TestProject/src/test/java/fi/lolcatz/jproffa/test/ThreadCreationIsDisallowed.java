package fi.lolcatz.jproffa.test;

import fi.lolcatz.jproffa.testproject.ThreadCreation;
import fi.lolcatz.profiledata.ProfileData;
import fi.lolcatz.profiler.ClassBlacklist;
import fi.lolcatz.profiler.Util;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.fail;

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
