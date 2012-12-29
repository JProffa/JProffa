package jproffa.graph;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.data.general.Dataset;

public class GraphRenderer {

    private final JFreeChart chart;

    public GraphRenderer(List<Line> lines) {
        final XYDataset dataset = createDataset(lines);
        chart = createChart(dataset);
    }

    /**
     * Returns the generated chart as JPanel.
     *
     * @return new JPanel containing the chart
     */
    public JPanel getJPanel() {
        JPanel jPanel = new JPanel();
        ChartPanel myChart = new ChartPanel(chart);
        jPanel.setLayout(new java.awt.BorderLayout());
        jPanel.add(myChart, BorderLayout.CENTER);
        jPanel.validate();
        jPanel.setPreferredSize(new Dimension(400, 400));
        return jPanel;
    }

    public JFreeChart getChart() {
        return chart;
    }

    private XYDataset createDataset(List<Line> lines) {
        final XYSeriesCollection dataset = new XYSeriesCollection();
        for (Line line : lines) {
            String label = line.className + " " + line.methodName;
            if (line.annotation != null) {
                label += " - " + line.annotation;
            }
            final XYSeries series1 = new XYSeries(label);
            for (int i = 0; i < line.input.size(); i++) {
                series1.add(line.input.get(i), line.time.get(i));
            }
            dataset.addSeries(series1);
        }
        return dataset;
    }

    public Dataset getDataset() {
        Dataset result = null;
        if (chart != null) {
            Plot plot = chart.getPlot();
            result = ((XYPlot) plot).getDataset();
        }
        return result;
    }

    /*
     * Builds and returns the chart from a custom dataset.
     */
    private JFreeChart createChart(final XYDataset dataset) {
        final JFreeChart result = ChartFactory.createXYLineChart(
                "Runtime chart", // chart title
                "Input", // x axis label
                "Time", // y axis label
                dataset, // data
                PlotOrientation.VERTICAL,
                true, // include legend
                true, // tooltips
                false // urls
                );

        result.setBackgroundPaint(Color.white);

        //        final StandardLegend legend = (StandardLegend) chart.getLegend();
        //      legend.setDisplaySeriesShapes(true);

        final XYPlot plot = result.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
        //    plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        for (int i = 0; i < dataset.getSeriesCount(); i++) {
            renderer.setSeriesLinesVisible(i, true);
            renderer.setSeriesShapesVisible(i, false);
        }
        plot.setRenderer(renderer);

        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        return result;

    }
}
