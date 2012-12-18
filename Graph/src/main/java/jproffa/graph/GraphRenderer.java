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

public class GraphRenderer extends ApplicationFrame {

    final JFreeChart chart;
    private int length;

    public GraphRenderer(final String title, List<Line> lines) {
        super(title);
        final XYDataset dataset = createDataset(lines, null, null);
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

    private XYDataset createDataset(List<Line> lines, String actualName, String paramName) {
        length = lines.size();
        String name1 = (actualName == null ? "Actual" : actualName);
        String name2 = (paramName == null ? "Param" : paramName);
        final XYSeriesCollection dataset = new XYSeriesCollection();
        for (int j = 0; j < lines.size(); j++) {
            final XYSeries series1 = new XYSeries(lines.get(j).name);
            for (int i = 0; i < lines.get(j).input.size(); i++) {
                series1.add(lines.get(j).input.get(i), lines.get(j).time.get(i));
            }
            dataset.addSeries(series1);
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
        for (int i = 0; i < length; i++){
            renderer.setSeriesLinesVisible(i, true);
            renderer.setSeriesShapesVisible(i, false);
        }
        plot.setRenderer(renderer);

        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        return chart;

    }
}
