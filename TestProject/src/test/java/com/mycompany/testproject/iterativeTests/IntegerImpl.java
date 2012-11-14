/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testproject.iterativeTests;

import fi.lolcatz.profiledata.ProfileData;
import fi.lolcatz.profiler.Benchmarkable;
import fi.lolcatz.profiler.AbstractImpl;
import fi.lolcatz.profiler.Util;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IntegerImpl  extends AbstractImpl implements Benchmarkable<Integer>{

    private String className;
    private String methodName;

    @Override
    public Integer getInput(int size) {
        return new Integer(size);
    }

    @Override
    public int getMaxTime(Integer input, int size) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public long run(Integer input) {
        ProfileData.resetCounters();
        try {
            Class<?> c = Class.forName(getClassName());
            Method m = c.getDeclaredMethod(getMethodName(), Integer.TYPE);
            m.setAccessible(true);
            if (Modifier.isStatic(m.getModifiers())) {
                m.invoke(null, input.intValue());
            } else {
                m.invoke(input.intValue(), null);
            }
        } catch (Exception x) {
            Logger.getLogger(IntegerImpl.class.getName()).log(Level.SEVERE, null, x);
        }
        return Util.getTotalCost();
    }
    
    public long run(int input, int input2) {
    ProfileData.resetCounters();
        try {
            Class<?> c = Class.forName(getClassName());
            Method m = c.getMethod(getMethodName(), Integer.TYPE, Integer.TYPE);
            if (Modifier.isStatic(m.getModifiers())) {
                m.invoke(null, input, input2);
            } else {
                m.invoke(input, null);
            }
        } catch (Exception x) {
            Logger.getLogger(IntegerImpl.class.getName()).log(Level.SEVERE, null, x);
        }
        return Util.getTotalCost();
    }
    
    public long run(int input, int input2, int input3) {
    ProfileData.resetCounters();
        try {
            Class<?> c = Class.forName(getClassName());
            Method m = c.getMethod(getMethodName(), Integer.TYPE, Integer.TYPE, Integer.TYPE);
            if (Modifier.isStatic(m.getModifiers())) {
                m.invoke(null, input, input2, input3);
            } else {
                m.invoke(input, null);
            }
        } catch (Exception x) {
            Logger.getLogger(IntegerImpl.class.getName()).log(Level.SEVERE, null, x);
        }
        return Util.getTotalCost();
    }
}
