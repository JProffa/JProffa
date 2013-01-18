package net.jproffa.profiler;

import java.util.List;

/*
 * Interface for benchmarking implementations
 */
public interface Benchmarkable<T> {
    /**
     * Creates a <T> type input from a integer.
     *
     * @param size Size of the input
     * @return <T> type input. Integer and Long implementations return a new variable of the same value.
     */
    public T getInput(int size);

    /**
     * Returns the size of <T> type input.
     *
     * @param input Input <T> type input whose size to calculate
     * @return For Integer and Long returns the value of input, for String returns the length of input.
     */
    public int getSize(T input);

    /**
     * Generates a new Output object from a list of inputs.
     *
     * @param list The <T> type list of inputs
     * @return Generic type of output containing data of the runtime
     * @throws Exception
     */
    public Output<T> runMethod(List<T> list) throws Exception;
}
