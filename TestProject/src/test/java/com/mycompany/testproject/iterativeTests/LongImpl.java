/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testproject.iterativeTests;

import fi.lolcatz.profiledata.ProfileData;
import fi.lolcatz.profiler.Benchmarkable;
import fi.lolcatz.profiler.Util;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LongImpl implements Benchmarkable<Long> {

    private String methodName;
    private String className;

    @Override
    public Long getInput(int size) {
        return new Long(size);
    }

    @Override
    public int getMaxTime(Long input, int size) {
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

    @Override
    public long run(Long input) {
        ProfileData.resetCounters();
        try {
            Class<?> c = Class.forName(className);
            Method m = c.getMethod(methodName, Long.class);
            m.invoke(input, null);
        } catch (Exception x) {
            Logger.getLogger(IntegerImpl.class.getName()).log(Level.SEVERE, null, x);
        }
        return Util.getTotalCost();
    }
}
