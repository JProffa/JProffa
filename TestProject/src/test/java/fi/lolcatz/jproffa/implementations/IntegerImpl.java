package fi.lolcatz.jproffa.implementatios;

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
        Output<Integer> out2 = new Output<Integer>();
        Output<Integer> out3 = new Output<Integer>();
        for (Integer i : list){          
            out.addToInput(i);
            out.addToSize(getSize(i));
            out2.addToTime(runStatic(i));
            out3.addToTime(runStatic(i));
        }
        for (int i = 0;  i < out2.getTime().size(); i++) {
            if(out2.getTime().get(i) > out3.getTime().get(i) && out3.getTime().get(i) != 0)
                out.addToTime(out3.getTime().get(i));
            else
                out.addToTime(out2.getTime().get(i));
        }
        
        return out;
    }
    
    public Output<Integer> runMethod(List<Integer> list, List<Integer> list2) throws Exception {
        runStatic(1, 0);
        
        Output<Integer> out = new Output<Integer>();
        Output<Integer> out2 = new Output<Integer>();
        Output<Integer> out3 = new Output<Integer>();
        
        for (int i = 0; i < list.size(); i++) {
            out.addToInput(list.get(i));
            out.addToSize(list.get(i));
            out2.addToTime(runStatic(list.get(i), list2.get(i)));
            out3.addToTime(runStatic(list.get(i), list2.get(i)));
        }
        
        for (int i = 0;  i < out2.getTime().size(); i++) {
            if(out2.getTime().get(i) > out3.getTime().get(i) && out3.getTime().get(i) != 0)
                out.addToTime(out3.getTime().get(i));
            else
                out.addToTime(out2.getTime().get(i));
        }
        
        return out;
    }
    
    public Output<Integer> runMethod(List<Integer> list, List<Integer> list2, List<Integer> list3) throws Exception {
        runStatic(1, 0, 0);
        
        Output<Integer> out = new Output<Integer>();
        Output<Integer> out2 = new Output<Integer>();
        Output<Integer> out3 = new Output<Integer>();
        
        for (int i = 0; i < list.size(); i++) {
            out.addToInput(list.get(i));
            out.addToSize(list.get(i));
            out2.addToTime(runStatic(list.get(i), list2.get(i), list3.get(i)));
            out3.addToTime(runStatic(list.get(i), list2.get(i), list3.get(i)));
        }
        
        for (int i = 0;  i < out2.getTime().size(); i++) {
            if(out2.getTime().get(i) > out3.getTime().get(i) && out3.getTime().get(i) != 0)
                out.addToTime(out3.getTime().get(i));
            else
                out.addToTime(out2.getTime().get(i));
        }
        
        return out;
    }
}
