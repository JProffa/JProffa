package fi.lolcatz.profiler;

/**
 * Like {@link Runnable} but with {@code throws Exception}.
 */
public interface RunnableEx {
    public void run() throws Exception;
}
