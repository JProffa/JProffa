package net.jproffa.profiler;

import net.jproffa.profiledata.ProfileData;
import net.jproffa.profiledata.ThreadBlocker;

public class DefaultThreadBlockerPolicy implements ThreadBlocker.Policy {
    @Override
    public boolean isAllowedToStart(Thread thread) {
        return !ProfileData.isProfilingEnabled() || !Thread.currentThread().getName().equals("main");
    }
}
