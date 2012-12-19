package jproffa.graph;

import java.util.List;

public class Line {

    public List<Long> time;
    public List<Integer> input;
    public String className;
    public String methodName;
    
    /**
     * Additional description optionally specified by the test writer.
     * 
     * The annotation may be null. There may be multiple lines with the same annotation.
     */
    public String annotation;

    public Line(List<Long> time, List<Integer> input, String className, String methodName, String annotation) {
        this.time = time;
        this.input = input;
        this.className = className;
        this.methodName = methodName;
        this.annotation = annotation;
    }
}
