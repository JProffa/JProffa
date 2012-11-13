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

public class StringImpl implements Benchmarkable<String> {

    private String className;
    private String methodName;
    
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
    public int getMaxTime(String input, int size) {
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
    
    public String getClassName(){
        return className;
    }
    
    public String getMethodName(){
        return methodName;
    }
    
    @Override
    public long run(String input) {
    ProfileData.resetCounters();
        try {
            Class<?> c = Class.forName(className);
            Method m = c.getMethod(methodName, String.class);
            if (Modifier.isStatic(m.getModifiers())) {
                m.invoke(null, input);
            } else {
                m.invoke(input, null);
            }
        } catch (Exception x) {
            Logger.getLogger(IntegerImpl.class.getName()).log(Level.SEVERE, null, x);
        }
        return Util.getTotalCost();
    }
    
    
    public long run(String input, String input2) {
    ProfileData.resetCounters();
        try {
            Class<?> c = Class.forName(className);
            Method m = c.getMethod(methodName, String.class, String.class);
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
    
    public long run(String input, String input2, String input3) {
    ProfileData.resetCounters();
        try {
            Class<?> c = Class.forName(className);
            Method m = c.getMethod(methodName, String.class, String.class, String.class);
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
