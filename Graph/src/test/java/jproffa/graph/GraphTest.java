package jproffa.graph;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

public class GraphTest {


    List<Long> time;
    List<Integer> input;
    GraphUI ui;
    
    @Before
    public void setUp() {
        initOutputs();
    }

     @Test
     public void testGraphDrawing() throws Exception{
         ui.saveDataToFile(time, input, "Graph155", "testFile");  
         ui.saveGraphFromFile("Graph155", "testFile", "GraphFile4");
         System.out.println("Saving graph..");
     }

    private void initOutputs() {
        input = Arrays.asList(1, 2, 3, 4, 50);
        time = Arrays.asList(new Long(1), new Long(2), new Long(3), new Long(4), new Long(5));
    }
}
