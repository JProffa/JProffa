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
        Integer basicSize = out.getSize().get(0);
        Long basicCost = out.getTime().get(0);      
        for (int i = 0; i < out.getTime().size(); i++){          
            double multiplier = out.getSize().get(i)*1.0 / basicSize;
            Long time = out.getTime().get(i);
            boolean linearity = Math.abs((multiplier * basicCost) - time) < time*0.2 ? true : false;
            if (!linearity) return false;     
        }
        return true;
    }
    
    /*
     * Calculates the linearity of the parameter output
     */
    public boolean isLinear(Output<?> output){
        Integer basicSize = output.getSize().get(0);
        Long basicCost = output.getTime().get(0);      
        for (int i = 0; i < output.getTime().size(); i++){          
            double multiplier = output.getSize().get(i) / basicSize;
            Long time = output.getTime().get(i);
            boolean linearity = Math.abs((multiplier * basicCost) - time) < time*0.2 ? true : false;
            if (!linearity) return false;     
        }
        return true;
    }
    
    public boolean isExponential(long[][] array){
        return true;
    }
    
    
    
}
