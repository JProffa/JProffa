/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testproject.iterativeTests;

import fi.lolcatz.profiledata.ProfileData;
import fi.lolcatz.profiler.AbstractImpl;
import fi.lolcatz.profiler.Benchmarkable;
import fi.lolcatz.profiler.Output;
import fi.lolcatz.profiler.Util;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LongImpl extends AbstractImpl implements Benchmarkable<Long> {

    public Long getInput(long l){
        return new Long(l);
    }
    
    @Override
    public long run(Long input) {
        ProfileData.resetCounters();
        try {
            Class<?> c = Class.forName(getClassName());
            Method m = c.getMethod(getMethodName(), Long.class);
            if (Modifier.isStatic(m.getModifiers())) {
                m.invoke(null, input.longValue());
            } else {
                m.invoke(input.longValue(), null);
            }
        } catch (Exception x) {
            Logger.getLogger(IntegerImpl.class.getName()).log(Level.SEVERE, null, x);
        }
        return Util.getTotalCost();
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
        for (Long l : list){
            out.addToInput(l);
            out.addToSize(getSize(l));
            out.addToTime(run(l));
        }
        return out;
    }

}
