package jproffa.graph.internal;

import jproffa.graph.GraphReader;
import jproffa.graph.GraphRenderer;
import jproffa.graph.GraphWriter;
import jproffa.graph.Line;

import javax.swing.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ManualVisualTest {
    public static void main(String[] args) throws IOException {
        GraphReader reader = new GraphReader("GraphDataFolder");
        GraphWriter writer = new GraphWriter("ManualVisualTest", "main");

        List<Integer> input = Arrays.asList(1, 2, 3, 4, 50);
        List<Long> time = Arrays.asList(1L, 2L, 3L, 4L, 12L);
        List<Integer> input2 = Arrays.asList(1, 2, 3, 4, 50);
        List<Long> time2 = Arrays.asList(1L, 2L, 3L, 4L, 40L);

        writer.save(time, input);
        writer.save(time2, input2);
        final List<Line> list = reader.get(writer.getClassName(), writer.getMethodName());

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GraphRenderer renderer = new GraphRenderer(list);
                JPanel panel = renderer.getJPanel();
                JFrame frame = new JFrame();
                frame.setSize(400, 400);
                frame.add(panel);
                frame.pack();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}
