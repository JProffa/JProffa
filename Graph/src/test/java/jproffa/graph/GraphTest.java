package jproffa.graph;

import fi.lolcatz.profiler.Output;
import java.util.Arrays;
import java.util.List;
import jproffa.graph.Graph;
import org.junit.*;
import static org.junit.Assert.*;

public class GraphTest {

    Output<Integer> a;
    Output<Integer> b;

    @Before
    public void setUp() {
        initOutputs();
    }

     @Test
     public void testGraphDrawing(){
         Graph g = new Graph("Test", a, b);
         assertNotNull(g.getChart());
         assertEquals(g.getChart().getTitle().getText(), "Runtime chart");
        
     }

    private void initOutputs() {
        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5);
        List<Long> list2 = Arrays.asList(new Long(1), new Long(2), new Long(3), new Long(4), new Long(5));
        a = new Output<Integer>();
        b = new Output<Integer>();
        a.setInput(list1);
        a.setSize(list1);
        a.setTime(list2);
        b.setInput(list1);
        b.setSize(list1);
        b.setTime(list2);
    }
}
