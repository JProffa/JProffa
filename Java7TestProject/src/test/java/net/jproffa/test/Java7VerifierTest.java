package net.jproffa.test;

import net.jproffa.profiler.WithProfiling;
import net.jproffa.testproject.TestedClass;
import org.junit.Test;

public class Java7VerifierTest {
    @Test
    public void testProfilingReallySimpleCodeUnderJava7() {
        WithProfiling.in(new Runnable() {
            @Override
            public void run() {
                TestedClass.factorial(15);
            }
        });
    }
}
