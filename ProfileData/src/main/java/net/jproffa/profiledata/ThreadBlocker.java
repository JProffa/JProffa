package net.jproffa.profiledata;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains a list of policies that decide whether a Thread may be started.
 */
public final class ThreadBlocker {
    // ThreadBlockerTransformer in the main project injects Thread
    // with code that calls {checkStartingThreadAllowed().
    
    private ThreadBlocker() {
    }
    
    public static interface Policy {
        public boolean isAllowedToStart(Thread thread);
    }
    
    private static final List<Policy> policies = new ArrayList<Policy>();

    public static synchronized void addPolicy(Policy cb) {
        policies.add(cb);
    }

    public static synchronized void removePolicy(Policy cb) {
        policies.remove(cb);
    }

    public static synchronized boolean isStartingThreadAllowed(Thread thread) {
        for (Policy cb : policies) {
            if (!cb.isAllowedToStart(thread)) {
                return false;
            }
        }
        return true;
    }

    public static void checkStartingThreadAllowed(Thread thread) {
        if (!isStartingThreadAllowed(thread)) {
            throw new IllegalStateException("Starting thread " + thread.getName() + " denied due to profiling.");
        }
    }
}
