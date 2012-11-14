/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.lolcatz.profiler;

import java.util.List;

public class Output<T> {

    private List<T> input;
    private List<Integer> size;
    private List<Long> time;
    

    public List<T> getInput() {
        return input;
    }

    public void setInput(List<T> input) {
        this.input = input;
    }
    
    public List<Integer> getSize() {
        return size;
    }
    
    public void setSize(List<Integer> size){
        this.size = size;
    }

    public List<Long> getTime() {
        return time;
    }

    public void setTime(List<Long> time) {
        this.time = time;
    }
    
    public void addToInput(T object){
        input.add(object);
    }
    
    public void addToTime(long t){
        time.add(t);
    }
    
    public void addToSize(int i){
        size.add(i);
    }
    
}
