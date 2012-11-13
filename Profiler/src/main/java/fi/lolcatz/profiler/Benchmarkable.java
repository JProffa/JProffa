package fi.lolcatz.profiler;

/*
 * Interface for benchmarking implementations
 */
public interface Benchmarkable<T> {
    T getInput(int size);
    long run (T input);
    int getMaxTime(T input, int size);
    void setMethodName(String name);
    void setClassName(String name);
}
