package fi.lolcatz.profiler;

import fi.lolcatz.profiledata.ProfileData;
import fi.lolcatz.profiledata.ThreadBlocker;

public class DefaultThreadBlockerPolicy implements ThreadBlocker.Policy {
    @Override
    public boolean isAllowedToStart(Thread thread) {
        return !ProfileData.isProfilingEnabled() || !Thread.currentThread().getName().equals("main");
    }
}
