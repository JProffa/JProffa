/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jproffa.graph;

import com.google.gson.Gson;
import com.google.gson.JsonStreamParser;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Random;
import org.jfree.chart.ChartUtilities;

public class GraphUI {

    /**
     * Draws a runtime chart of the outputs size and time lists and saves the chart to a new .png file.
     *
     * @param actual The tested output
     * @param projected The example output
     */
//    private static void drawGraph(List<Long> time, List<Integer> input) {
//        //generate example outputs for one param
//        //linear, quadric ,nlogn       
//        if (System.getenv("PROFILER_VISUALIZATIONS_TO_FILE") != null) {
//            Graph g = new Graph("Test", time, input);
//            Random r = new Random();
//            File f = new File(System.getenv("PROFILER_VISUALIZATIONS_TO_FILE"));
//            try {
//                ChartUtilities.saveChartAsPNG(f, g.getChart(), 500, 270);
//            } catch (IOException ex) {
//            }
//        } else if (System.getenv("PROFILER_VISUALIZATIONS_SHOW") != null) {
//            Graph g = new Graph("Test", time, input);
//            g.init();
//        }
//    }

    /**
     * Saves the data to a file. This data can be made into a graph by either calling
     * showGraphFromFile or saveGrahFromFile.
     * @param time List of times
     * @param input List of inputs
     * @param graphName Name the graph gets
     * @param fileName File the data is saved to. May exist.
     */
    public static void saveDataToFile(List<Long> time, List<Integer> input, String graphName, String fileName) throws Exception {
        try {
            File f = new File(fileName);
            FileWriter fstream = new FileWriter(f, true);
            BufferedWriter fbw = new BufferedWriter(fstream);
            Gson gson = new Gson();
            String data = gson.toJson(new DataType(time, input, graphName));
            fbw.write(data);
            fbw.newLine();
            fbw.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    /** 
     * Reads and initializes the graph from a file.
     * 
     * @param graphName Name of the graph to be initialized
     * @param fileName File the graph is loaded from
     * @throws Exception 
     */
    public static void showGraphFromFile(String graphName, String fileName) throws Exception {
        try {
            Gson gson = new Gson();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            DataType t = null;
            while (bufferedReader.ready()) {
                String line = bufferedReader.readLine();
                t = gson.fromJson(line, DataType.class);
                if (t.name.equals(graphName)) {
                    Graph g = new Graph(graphName, t.time, t.input);
                    g.init();
                    return;
                }
            }

        } catch (Exception e) {
            System.err.println(e);
        }
    }
    /**
     * Reads a graph from a file and saves it to a new .png file.
     * @param graphName Name of the graph to be saved
     * @param fileName Name of the file the data is in
     * @param destination Name of the .png file
     * @throws Exception 
     */
    public static void saveGraphFromFile(String graphName, String fileName, String destination) throws Exception {
        try {
            Gson gson = new Gson();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            DataType t = null;
            while (bufferedReader.ready()) {
                String line = bufferedReader.readLine();
                t = gson.fromJson(line, DataType.class);
                if (t.name.equals(graphName)) {
                    Graph g = new Graph(graphName, t.time, t.input);
                    File f = new File(destination);
                    try {
                        ChartUtilities.saveChartAsPNG(f, g.getChart(), 500, 270);
                    } catch (IOException ex) {
                        System.err.println(ex);
                    }
                    return;
                }
            }

        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private static class DataType {

        public List<Long> time;
        public List<Integer> input;
        String name;

        public DataType(List<Long> time, List<Integer> input, String name) {
            this.time = time;
            this.input = input;
            this.name = name;
        }
    }
}
