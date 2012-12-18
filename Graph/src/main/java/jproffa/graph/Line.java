/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jproffa.graph;

import java.util.List;

public class Line {

    public List<Long> time;
    public List<Integer> input;
    String name;

    public Line(List<Long> time, List<Integer> input, String name) {
        this.time = time;
        this.input = input;
        this.name = name;
    }
}
