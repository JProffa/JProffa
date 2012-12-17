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
import fi.lolcatz.profiler.Output;
import java.util.ArrayList;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class GraphWriter implements TestRule {

    private String fileName = "jproffa_data.txt"; // todo: from env
    private String testName = "test";
    private String mainDirectory = "testFolder";

    public GraphWriter() {
//        mainDirectory = System.getenv("DIRECTORY");
        File f = new File(fileName);
        f.delete();
    }

    public GraphWriter(String file) {
        File f = new File(file);
        f.delete();
        this.fileName = file;
    }

    public GraphWriter(String file, String testName) {
        this.fileName = file;
        this.testName = testName;
    }

    public String getFileName() {
        return fileName;
    }

    public String getTestName() {
        return testName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

//    private static void drawGraph(List<Long> time, List<Integer> input) {
//        //generate example outputs for one param
//        //linear, quadric ,nlogn       
//        if (System.getenv("PROFILER_VISUALIZATIONS_TO_FILE") != null) {
//            GraphRenderer g = new GraphRenderer("Test", time, input);
//            Random r = new Random();
//            File f = new File(System.getenv("PROFILER_VISUALIZATIONS_TO_FILE"));
//            try {
//                ChartUtilities.saveChartAsPNG(f, g.getChart(), 500, 270);
//            } catch (IOException ex) {
//            }
//        } else if (System.getenv("PROFILER_VISUALIZATIONS_SHOW") != null) {
//            GraphRenderer g = new GraphRenderer("Test", time, input);
//            g.init();
//        }
//    }
    /**
     * Saves the data to a file. This data can be made into a graph by either
     * calling showGraphFromFile or saveGraphFromFile.
     *
     * @param time List of times
     * @param input List of inputs
     */
    public void save(List<Long> time, List<Integer> input) throws Exception {
        try {
            File parentDir = new File(mainDirectory);
            if (!parentDir.exists()) {
                parentDir.mkdir();
            }
            File f = new File(parentDir, fileName);
            FileWriter fstream = new FileWriter(f, true);
            BufferedWriter fbw = new BufferedWriter(fstream);
            Gson gson = new Gson();
            String data = gson.toJson(new Line(time, input, testName));
            fbw.write(data);
            fbw.newLine();
            fbw.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void save(Output<?> out) throws Exception {
        save(out.getTime(), out.getSize());
    }

    //TODO
    // Map<String, List<Käppyrä>>  tai joku tuon idean wräppäävä luokka
    /**
     * Reads and initializes the graph from a file.
     *
     * @param graphName Name of the graph to be initialized
     * @param fileName File the graph is loaded from
     * @throws Exception
     */
    public void showGraphFromFile(String fileName, String... graphName) throws Exception {
        try {
            Gson gson = new Gson();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            List<Line> dtList = new ArrayList<Line>();
            while (bufferedReader.ready()) {
                String line = bufferedReader.readLine();
                Line t = gson.fromJson(line, Line.class);
                for (int i = 0; i < graphName.length; i++) {
                    String s = graphName[i];
                    if (t.name.equals(s)) {
                        dtList.add(t);
                        break;
                    }
                }
            }
            List<List<Integer>> inputList = new ArrayList<List<Integer>>();
            List<List<Long>> timeList = new ArrayList<List<Long>>();
            List<String> nameList = new ArrayList<String>();
            for (Line dt : dtList) {
                inputList.add(dt.input);
                timeList.add(dt.time);
                nameList.add(dt.name);
            }
            GraphRenderer g = new GraphRenderer(inputList, timeList, nameList);
            g.init();
            return;
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    /**
     * Reads a graph from a file and saves it to a new .png file.
     *
     * @param graphName Name of the graph to be saved
     * @param fileName Name of the file the data is in
     * @param destination Name of the .png file
     * @throws Exception
     */
    public void saveGraphFromFile(String graphName, String fileName, String destination) throws Exception {
        try {
            Gson gson = new Gson();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            Line t = null;
            while (bufferedReader.ready()) {
                String line = bufferedReader.readLine();
                t = gson.fromJson(line, Line.class);
                if (t.name.equals(graphName)) {
                    GraphRenderer g = new GraphRenderer(graphName, t.time, t.input);
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

    @Override
    public Statement apply(final Statement base, final Description description) {
        return new Statement() {

            @Override
            public void evaluate() throws Throwable {
                testName = description.getClassName() + " " + description.getMethodName();
                base.evaluate();
            }
        };
    }
}
