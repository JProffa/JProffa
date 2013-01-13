package fi.lolcatz.jproffa.test;

import fi.lolcatz.jproffa.testproject.TestedClass;
import fi.lolcatz.profiler.WithProfiling;
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
