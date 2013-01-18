package net.jproffa.test;

import net.jproffa.profiler.WithProfiling;
import net.jproffa.testproject.ThreadCreation;
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
