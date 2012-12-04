package com.mycompany.testproject.iterativeTests;

import fi.lolcatz.profiler.AbstractImpl;
import fi.lolcatz.profiler.Benchmarkable;
import fi.lolcatz.profiler.Output;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.chart.ChartUtilities;

public class LongImpl extends AbstractImpl implements Benchmarkable<Long> {

    public Long getInput(long l) {
        return new Long(l);
    }

    @Override
    public int getSize(Long input) {
        return input.intValue();
    }

    @Override
    public Long getInput(int size) {
        return new Long(size);
    }

    @Override
    public Output<Long> runMethod(List<Long> list) throws Exception {
        run(new Long(1));
        Output<Long> out = new Output<Long>();
        for (Long l : list) {
            out.addToInput(l);
            out.addToSize(getSize(l));
            out.addToTime(run(l));
        }
        return out;
    }
}
