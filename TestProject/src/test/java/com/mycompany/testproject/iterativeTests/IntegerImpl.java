package com.mycompany.testproject.iterativeTests;

import fi.lolcatz.profiler.AbstractImpl;
import fi.lolcatz.profiler.Benchmarkable;
import fi.lolcatz.profiler.Output;
import java.util.List;

public class IntegerImpl extends AbstractImpl implements Benchmarkable<Integer> {

    @Override
    public Integer getInput(int size) {
        return new Integer(size);
    }

    @Override
    public int getSize(Integer input) {
        return input.intValue();
    }

    @Override
    public Output<Integer> generateOutput(List<Integer> list) throws Exception {
        runStatic(1);
        Output<Integer> out = new Output<Integer>();
        for (Integer i : list){          
            out.addToInput(i);
            out.addToSize(getSize(i));
            out.addToTime(runStatic(i));
        }
        return out;
    }
}
