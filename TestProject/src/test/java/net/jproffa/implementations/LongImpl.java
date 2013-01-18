package net.jproffa.implementations;

import java.util.List;
import net.jproffa.profiler.AbstractImpl;
import net.jproffa.profiler.Benchmarkable;
import net.jproffa.profiler.Output;

public class LongImpl extends AbstractImpl implements Benchmarkable<Long> {

    public Long getInput(long l) {
        return l;
    }

    @Override
    public int getSize(Long input) {
        return input.intValue();
    }

    @Override
    public Long getInput(int size) {
        return (long) size;
    }

    @Override
    public Output<Long> runMethod(List<Long> list) throws Exception {
        run(1L);
        Output<Long> out = new Output<Long>();
        for (Long l : list) {
            out.addToInput(l);
            out.addToSize(getSize(l));
            out.addToTime(runStatic(l));
        }
        return out;
    }
}
