/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.testproject.javamethodtests;

import com.mycompany.testproject.iterativeTests.IntegerImpl;
import com.mycompany.testproject.iterativeTests.IntegerImpl;
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
    public int getSize(String input){
        return input.length();
    }
       
    @Override
    public long run(String input) throws Exception {
    ProfileData.resetCounters();
        try {
            Class<?> c = Class.forName(getClassName());
            Method m = c.getMethod(getMethodName(), String.class);
            if (Modifier.isStatic(m.getModifiers())) {
                m.invoke(null, input);
            } else {
                m.invoke(input, null);
            }
        } catch (Exception x) {
            Logger.getLogger(IntegerImpl.class.getName()).log(Level.SEVERE, null, x);
            throw x;
        }
        return Util.getTotalCost();
    }
    
    
    public long run(String input, String input2) throws Exception {
    ProfileData.resetCounters();
        try {
            Class<?> c = Class.forName(getClassName());
            Method m = c.getMethod(getMethodName(), String.class, String.class);
            if (Modifier.isStatic(m.getModifiers())) {
                m.invoke(null, input, input2);
            } else {
                m.invoke(input, null);
            }
        } catch (Exception x) {
            Logger.getLogger(IntegerImpl.class.getName()).log(Level.SEVERE, null, x);
            throw x;
        }
        return Util.getTotalCost();
    }
    
    public long run(String input, String input2, String input3) throws Exception {
    ProfileData.resetCounters();
        try {
            Class<?> c = Class.forName(getClassName());
            Method m = c.getMethod(getMethodName(), String.class, String.class, String.class);
            if (Modifier.isStatic(m.getModifiers())) {
                m.invoke(null, input, input2, input3);
            } else {
                m.invoke(input, null);
            }
        } catch (Exception x) {
            Logger.getLogger(IntegerImpl.class.getName()).log(Level.SEVERE, null, x);
            throw x;
        }
        return Util.getTotalCost();
    }

    @Override
    public Output<String> generateOutput(List<String> list) throws Exception {
        run("");
        Output<String> out = new Output<String>();
        for (String s : list){
            out.addToInput(s);
            out.addToSize(getSize(s));
            out.addToTime(run(s));
        }
        return out;
    }

}
