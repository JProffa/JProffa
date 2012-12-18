/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jproffa.graph;

import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;

/**
 *
 * @author oorissan
 */
public class GraphTestWithoutRule {
    
    List<Long> time;
    List<Long> time2;
    List<Integer> input;
    List<Integer> input2;
    
    GraphReader reader = new GraphReader("GraphDataFolder");
    
    public GraphWriter ui = new GraphWriter("GraphTest");

    @Before
    public void setUp() {
        initOutputs();
    }

    @Test
    public void testGraphDrawingWithoutRule() throws Exception {
        ui.setMethodName("testGraphDrawingWithoutRule");
        ui.save(time, input);
        ui.save(time2, input2);
        List<Line> list = reader.get("GraphTest", "testGraphDrawingWithoutRule");
        assertTrue(list.size() > 0);
        GraphRenderer renderer = new GraphRenderer("testGraph", list);
        assertNotNull(renderer.getChart());
    }
    
    @Test
    public void testGraphDrawingAgainWithoutRule() throws Exception {
        ui.setMethodName("testGraphDrawingAgainWithoutRule");
        ui.save(time, input);
        ui.save(time2, input2);
        List<Line> list = reader.get("GraphTest", "testGraphDrawingAgainWithoutRule");
        assertTrue(list.size() > 0);
        GraphRenderer renderer = new GraphRenderer("testGraph", list);
        assertNotNull(renderer.getChart());
    }

    private void initOutputs() {
        input = Arrays.asList(1, 2, 3, 4, 50);
        time = Arrays.asList(new Long(1), new Long(2), new Long(3), new Long(4), new Long(12));

        input2 = Arrays.asList(1, 2, 3, 4, 50);
        time2 = Arrays.asList(new Long(1), new Long(2), new Long(3), new Long(4), new Long(40));
    }
}
