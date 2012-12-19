package jproffa.graph;

import java.util.Arrays;
import java.util.List;
import javax.swing.JPanel;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class GraphTestWithoutRule {
    
    List<Long> time;
    List<Long> time2;
    List<Integer> input;
    List<Integer> input2;
    
    GraphReader reader = new GraphReader("GraphDataFolder");
    
    public GraphWriter writer = new GraphWriter("GraphTest");

    @Before
    public void setUp() {
        initOutputs();
    }

    @Test
    public void testGraphDrawingWithoutRule() throws Exception {
        writer.setMethodName("testGraphDrawingWithoutRule");
        writer.save(time, input);
        writer.save(time2, input2);
        List<Line> list = reader.get("GraphTest", "testGraphDrawingWithoutRule");
        assertTrue(list.size() > 0);
        GraphRenderer renderer = new GraphRenderer(list);
        JPanel p = renderer.getJPanel();
        assertNotNull(p);
        assertNotNull(renderer.getChart());
    }
    
    @Test
    public void testGraphDrawingAgainWithoutRule() throws Exception {
        writer.setMethodName("testGraphDrawingAgainWithoutRule");
        writer.save(time, input);
        writer.save(time2, input2);
        List<Line> list = reader.get("GraphTest", "testGraphDrawingAgainWithoutRule");
        assertTrue(list.size() > 0);
        GraphRenderer renderer = new GraphRenderer(list);
        JPanel p = renderer.getJPanel();
        assertNotNull(p);
        assertNotNull(renderer.getChart());
    }

    private void initOutputs() {
        input = Arrays.asList(1, 2, 3, 4, 50);
        time = Arrays.asList(1L, 2L, 3L, 4L, 12L);

        input2 = Arrays.asList(1, 2, 3, 4, 50);
        time2 = Arrays.asList(1L, 2L, 3L, 4L, 40L);
    }
}
