package fi.lolcatz.profiler;

import java.util.List;

/*
 * Interface for benchmarking implementations
 */
public interface Benchmarkable<T> {
    public T getInput(int size);
    public int getSize(T input);
    /**Generates a new Output object from a list of inputs.
     * 
     * @param list The generic type list of inputs
     * @return Generic type of output containing data of the runtime
     * @throws Exception 
     */
    public Output<T> generateOutput(List<T> list) throws Exception;
    /**Draws a runtime chart of the outputs size and time lists and saves the chart to a new .png file.
     * 
     * @param actual The tested output
     * @param projected The example output
     */
    public void drawGraph(Output<?> actual, Output<?> projected);
}
