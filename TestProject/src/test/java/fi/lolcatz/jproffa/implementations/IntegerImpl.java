package fi.lolcatz.jproffa.implementations;

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
    public Output<Integer> runMethod(List<Integer> list) throws Exception {
        runStatic(1);
        
        Output<Integer> out = new Output<Integer>();
        for (Integer i : list){          
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
