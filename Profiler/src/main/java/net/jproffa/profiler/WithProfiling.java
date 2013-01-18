package net.jproffa.profiler;

import net.jproffa.profiledata.ProfileData;
import java.util.concurrent.Callable;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Enables profiling for a piece of code or a test.
 * 
 * <p>
 * More specifically it ensures the agent is loaded,
 * resets counters before the block and disables profiling
 * after the block test.
 * 
 * <p>
 * It can be used as a JUnit rule, which causes it to additionally
 * blacklist the test class at hand.
 */
public final class WithProfiling implements TestRule {
    private final boolean shouldEnableProfiling;
    
    private WithProfiling(boolean shouldEnableProfiling) {
        this.shouldEnableProfiling = shouldEnableProfiling;
    }
    
    public static void in(Runnable inner) {
        Util.loadAgent();
        ProfileData.resetCounters();
        ProfileData.enableProfiling();
        try {
            inner.run();
        } finally {
            ProfileData.disableProfiling();
        }
    }
    
    public static void in(RunnableEx inner) throws Exception {
        Util.loadAgent();
        ProfileData.resetCounters();
        ProfileData.enableProfiling();
        try {
            inner.run();
        } finally {
            ProfileData.disableProfiling();
        }
    }
    
    public static <V> V in(Callable<V> inner) throws Exception {
        Util.loadAgent();
        ProfileData.resetCounters();
        ProfileData.enableProfiling();
        try {
            return inner.call();
        } finally {
            ProfileData.disableProfiling();
        }
    }
    
    /**
     * Returns a JUnit rule that sets up and enables profiling before each test.
     */
    public static WithProfiling rule() {
        return new WithProfiling(true);
    }
    
    /**
     * Returns a JUnit rule that sets up and optionally enables profiling before each test.
     * 
     * <p>
     * If {@code enableProfiling} is false, it only ensures the profiler is initialized
     * but disables profiling before each test. The test may use
     * {@code WithProfiling.in(...)} to profile things itself.
     * Note that you should need this rarely as the rule blacklists the test class
     * anyway, so code in it does not count towards the profiling score.
     */
    public static WithProfiling rule(boolean enableProfiling) {
        return new WithProfiling(enableProfiling);
    }

    @Override
    public Statement apply(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                Util.loadAgent();
                ClassBlacklist.add(description.getTestClass());
                ProfileData.resetCounters();
                if (shouldEnableProfiling) {
                    ProfileData.enableProfiling();
                } else {
                    ProfileData.disableProfiling();
                }
                try {
                    base.evaluate();
                } finally {
                    ProfileData.disableProfiling();
                }
            }
        };
    }
}
