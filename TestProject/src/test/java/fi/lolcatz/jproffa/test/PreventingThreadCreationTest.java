package fi.lolcatz.jproffa.test;

import fi.lolcatz.jproffa.testproject.ThreadCreation;
import fi.lolcatz.profiler.ClassBlacklist;
import fi.lolcatz.profiler.Util;
import fi.lolcatz.profiler.WithProfiling;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.fail;

public class PreventingThreadCreationTest {

    @BeforeClass
    public static void setUpClass() {
        ClassBlacklist.add(ComplexityQuadraticTest.class);
        Util.loadAgent();
    }

    @Test
    public void threadCreateTest() {
        try {
            WithProfiling.in(new Runnable() {
                @Override
                public void run() {
                    ThreadCreation.createThreadAndStart();
                }
            });
        } catch (RuntimeException ex) {
            return;
        }
        fail("Thread creation was allowed in user code");
    }
}
