package com.mycompany.testproject.iterativeTests;

import fi.lolcatz.profiler.AbstractImpl;
import fi.lolcatz.profiler.Benchmarkable;
import fi.lolcatz.profiler.Graph;
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
    public Output<Long> generateOutput(List<Long> list) throws Exception {
        run(new Long(1));
        Output<Long> out = new Output<Long>();
        for (Long l : list) {
            out.addToInput(l);
            out.addToSize(getSize(l));
            out.addToTime(run(l));
        }
        return out;
    }

    @Override
    public void drawGraph(Output<?> actual, Output<?> param) {
        Graph g = new Graph("Test", actual, param);
        Random r = new Random();
        File f = new File("Graphs" + r.nextInt());
        try {
            ChartUtilities.saveChartAsPNG(f, g.getChart(), 500, 270);
        } catch (IOException ex) {
            Logger.getLogger(IntegerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
