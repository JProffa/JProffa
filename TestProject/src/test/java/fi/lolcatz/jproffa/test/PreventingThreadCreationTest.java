package fi.lolcatz.jproffa.test;

import fi.lolcatz.jproffa.testproject.ThreadCreation;
import fi.lolcatz.profiler.WithProfiling;
import org.junit.Test;

import static org.junit.Assert.fail;
import org.junit.Rule;

public class PreventingThreadCreationTest {

    @Rule public WithProfiling profiling = WithProfiling.rule();

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
