package jproffa.graph;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.swing.JPanel;
import org.junit.*;
import static org.junit.Assert.*;

public class GraphTestWithRule {

    List<Long> time;
    List<Long> time2;
    List<Integer> input;
    List<Integer> input2;
    
    GraphReader reader = new GraphReader("GraphDataFolder");
    
    @Rule
    public GraphWriter ui = new GraphWriter();

    @Before
    public void setUp() {
        initOutputs();
    }

    @Test
    public void testGraphDrawing() throws Exception {
        ui.save(time, input);
        ui.save(time2, input2);
        List<Line> list = reader.get("GraphTest", "testGraphDrawing");
        assertTrue(list.size() > 0);
        GraphRenderer renderer = new GraphRenderer("testGraph", list);
        JPanel p = renderer.getJpanel();
        assertNotNull(p);
        assertNotNull(renderer.getChart());
    }
    
    @Test
    public void testGraphDrawingAgain() throws Exception {
        ui.save(time, input);
        ui.save(time2, input2);
        List<Line> list = reader.get("GraphTest", "testGraphDrawingAgain");
        assertTrue(list.size() > 0);
        GraphRenderer renderer = new GraphRenderer("testGraph", list);
        JPanel p = renderer.getJpanel();
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
