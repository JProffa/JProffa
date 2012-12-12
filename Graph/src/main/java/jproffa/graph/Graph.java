package jproffa.graph;

import java.awt.Color;
import java.util.List;
import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class Graph extends ApplicationFrame {

    final JFreeChart chart;

    public Graph(final String title, List<Long> time, List<Integer> input) {
        super(title);
        final XYDataset dataset = createDataset(time, input, null, null);
        chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);
    }

    public Graph(final String title, List<Long> time, List<Integer> input, String actualName, String paramName) {
        super(title);
        final XYDataset dataset = createDataset(time, input, actualName, paramName);
        chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);
    }

    public Graph(List<List<Integer>> inputs, List<List<Long>> times, List<String> names) {
        super("Graph");
        final XYDataset dataset = createDataset(times, inputs, names);
        chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);
    }

    public void init() {
        this.pack();
        RefineryUtilities.centerFrameOnScreen(this);
        this.setVisible(true);
    }

    public JFreeChart getChart() {
        return chart;
    }

    /*
     * Creates the dataset from two Output objects.
     */
    private XYDataset createDataset(List<Long> time, List<Integer> input, String actualName, String paramName) {
        String name1 = (actualName == null ? "Actual" : actualName);
        String name2 = (paramName == null ? "Param" : paramName);
        final XYSeries series1 = new XYSeries(name1);
        for (int i = 0; i < time.size(); i++) {
            series1.add(input.get(i), time.get(i));
        }
        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        return dataset;
    }

    private XYDataset createDataset(List<List<Long>> times, List<List<Integer>> inputs, List<String> names) {
        final XYSeriesCollection dataset = new XYSeriesCollection();
        for (int i = 0; i < times.size(); i++) {
            System.err.println(times.size());
            final XYSeries series = new XYSeries(i+1);
            for (int j = 0; j < times.get(i).size(); j++) {
                series.add(inputs.get(i).get(j), times.get(i).get(j));
            }
            dataset.addSeries(series);
        }
        return dataset;
    }
    
    /*
     * Builds and returns the chart from a custom dataset.
     */
    private JFreeChart createChart(final XYDataset dataset) {
        final JFreeChart chart = ChartFactory.createXYLineChart(
                "Runtime chart", // chart title
                "Input", // x axis label
                "Time", // y axis label
                dataset, // data
                PlotOrientation.VERTICAL,
                true, // include legend
                true, // tooltips
                false // urls
                );

        chart.setBackgroundPaint(Color.white);

        //        final StandardLegend legend = (StandardLegend) chart.getLegend();
        //      legend.setDisplaySeriesShapes(true);

        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
        //    plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, true);
        renderer.setSeriesShapesVisible(0, false);
        renderer.setSeriesLinesVisible(1, true);
        renderer.setSeriesShapesVisible(1, false);
        plot.setRenderer(renderer);

        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        return chart;

    }
}
