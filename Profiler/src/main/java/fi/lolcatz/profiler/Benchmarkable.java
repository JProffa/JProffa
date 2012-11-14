package fi.lolcatz.profiler;

import java.util.List;

/*
 * Interface for benchmarking implementations
 */
public interface Benchmarkable<T> {
    T getInput(int size);
    int getSize(T input);
    long run (T input) throws Exception;
    //int getMaxTime(T input, int size);
}
