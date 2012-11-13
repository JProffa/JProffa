/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testproject.iterativeTests;

import fi.lolcatz.profiledata.ProfileData;
import fi.lolcatz.profiler.Benchmarkable;
import fi.lolcatz.profiler.Util;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IntegerImpl implements Benchmarkable<Integer> {

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
    public void setMethodName(String name) {
        this.methodName = name;
    }

    @Override
    public void setClassName(String name) {
        this.className = name;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }
    public long getMarginOfError(long cost) {
        long len = Long.toString(cost).length();
        len = (len/3)*(len/3) + (len%3);
        if(len < 2) return 10;
        
        return len*50;
    }

    @Override
    public long run(Integer input) {
        ProfileData.resetCounters();
        try {
            Class<?> c = Class.forName(className);
            Method m = c.getDeclaredMethod(methodName, Integer.TYPE);
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
            Class<?> c = Class.forName(className);
            Method m = c.getMethod(methodName, Integer.TYPE, Integer.TYPE);
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
            Class<?> c = Class.forName(className);
            Method m = c.getMethod(methodName, Integer.TYPE, Integer.TYPE, Integer.TYPE);
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
