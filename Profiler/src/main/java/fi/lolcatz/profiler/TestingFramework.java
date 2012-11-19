/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.lolcatz.profiler;

import java.lang.Math.*;

public class TestingFramework {

    Output<?> out;

    public TestingFramework(){        
    }
    
    public TestingFramework(Output<?> out) {
        this.out = out;
    }
    
    /*
     * Calculates the linearity of the class variable out
     */  
    public boolean isLinear(){
        if (out.getSize().size() < 2) return false;
        Integer x0 = out.getSize().get(0);
        Long y0 = out.getTime().get(0);    
        Integer xn = out.getSize().get(out.getSize().size()-1);
        Long yn = out.getTime().get(out.getTime().size()-1);
        double a = Math.abs(y0-yn)/Math.abs(x0-xn);
        double b = y0 - a*x0;     
        for (int i = 0; i < out.getTime().size(); i++){          
            double time = out.getTime().get(i);
            double function = a*out.getSize().get(i)+b;   
            System.out.println("Time: " + time);
            System.out.println("Function: " + function);
            boolean linearity = (time*1.002 >= function && time-(time*0.002) <= function) ? true : false;
            if (!linearity) return false;
        }
        return true;
    }
    
    /*
     * Calculates the linearity of the parameter variable out
     */  
    public boolean isLinear(Output<?> output){
        if (output.getSize().size() < 2) return false;
        Integer x0 = output.getSize().get(0);
        Long y0 = output.getTime().get(0);    
        Integer xn = output.getSize().get(output.getSize().size()-1);
        Long yn = output.getTime().get(output.getTime().size()-1);
        double a = Math.abs(y0-yn)/Math.abs(x0-xn);
        double b = y0 - a*x0;     
        for (int i = 0; i < output.getTime().size(); i++){          
            double time = output.getTime().get(i);
            double function = a*output.getSize().get(i)+b;   
            boolean linearity = time*1.05 >= function ? true : false;
            if (!linearity) return false;
        }
        return true;
    }
    
    public boolean isExponential(long[][] array){
        return true;
    }
    
    
    
}
