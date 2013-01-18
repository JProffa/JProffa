package net.jproffa.implementations;

import java.util.List;
import net.jproffa.profiler.AbstractImpl;
import net.jproffa.profiler.Benchmarkable;
import net.jproffa.profiler.Output;

public class IntegerImpl extends AbstractImpl implements Benchmarkable<Integer> {

    @Override
    public Integer getInput(int size) {
        return size;
    }

    @Override
    public int getSize(Integer input) {
        return input;
    }

    @Override
    public Output<Integer> runMethod(List<Integer> list) throws Exception {
        runStatic(1);

        Output<Integer> out = new Output<Integer>();
        for (Integer i : list) {
            out.addToInput(i);
            out.addToSize(getSize(i));
            out.addToTime(runStatic(i));
        }

        return out;
    }

    public Output<Integer> runMethod(List<Integer> list, List<Integer> list2) throws Exception {
        runStatic(1, 0);

        Output<Integer> out = new Output<Integer>();

        for (int i = 0; i < list.size(); i++) {
            out.addToInput(list.get(i));
            out.addToSize(list.get(i));
            out.addToTime(runStatic(list.get(i), list2.get(i)));
        }

        return out;
    }

    public Output<Integer> runMethod(List<Integer> list, List<Integer> list2, List<Integer> list3) throws Exception {
        runStatic(1, 0, 0);

        Output<Integer> out = new Output<Integer>();

        for (int i = 0; i < list.size(); i++) {
            out.addToInput(list.get(i));
            out.addToSize(list.get(i));
            out.addToTime(runStatic(list.get(i), list2.get(i), list3.get(i)));
        }

        return out;
    }
}
