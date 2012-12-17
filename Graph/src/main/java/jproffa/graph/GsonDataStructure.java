/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jproffa.graph;

import java.util.List;

class GsonDataStructure {

    public List<Long> time;
    public List<Integer> input;
    String name;

    public GsonDataStructure(List<Long> time, List<Integer> input, String name) {
        this.time = time;
        this.input = input;
        this.name = name;
    }
}
