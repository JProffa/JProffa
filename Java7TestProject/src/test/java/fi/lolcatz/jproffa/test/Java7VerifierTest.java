package fi.lolcatz.jproffa.test;

import fi.lolcatz.jproffa.testproject.TestedClass;
import fi.lolcatz.profiledata.ProfileData;
import fi.lolcatz.profiler.ClassBlacklist;
import fi.lolcatz.profiler.Util;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class Java7VerifierTest {
    @BeforeClass
    public static void classSetup() {
        ClassBlacklist.add(Java7VerifierTest.class);
        Util.loadAgent();
    }
    
    @AfterClass
    public static void classTeardown() {
        ProfileData.disallowProfiling(); // FIXME: hax to allow surefire to shut down cleanly
    }
    
    @Test
    public void testProfilingReallySimpleCodeUnderJava7() {
        TestedClass.factorial(15);
    }
}
