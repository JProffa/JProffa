package fi.lolcatz.profiler;

import java.util.List;

/*
 * Interface for benchmarking implementations
 */
public interface Benchmarkable<T> {
    public T getInput(int size);
    public int getSize(T input);
    //int getMaxTime(T input, int size);
    public Output<T> generateOutput(List<T> list) throws Exception;
    public void drawGraph(Output<?> actual, Output<?> projected);
}
