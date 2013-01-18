package net.jproffa.implementations;

import java.util.List;
import net.jproffa.profiler.AbstractImpl;
import net.jproffa.profiler.Benchmarkable;
import net.jproffa.profiler.Output;

public class StringImpl extends AbstractImpl implements Benchmarkable<String> {

    @Override
    public String getInput(int size) {
        String s = "";
        StringBuilder b = new StringBuilder(s);
        if (size > 26) {
            int jj = size % 26;
            int loops = size / 26;
            for (int i = 0; i < loops; i++) {
                for (int j = 0; j < 26; j++) {
                    b.append((char) (j + 97));
                }
            }
            for (int k = 0; k < jj; k++) {
                b.append((char) (k + 97));
            }
        } else {
            for (int i = 0; i < size; i++) {
                b.append((char) (i + 97));
            }
        }
        return b.toString();
    }

    @Override
    public int getSize(String input) {
        return input.length();
    }

    @Override
    public Output<String> runMethod(List<String> list) throws Exception {
        run("");
        Output<String> out = new Output<String>();
        for (String s : list) {
            out.addToInput(s);
            out.addToSize(getSize(s));
            out.addToTime(runStatic(s));
        }
        return out;
    }
}
