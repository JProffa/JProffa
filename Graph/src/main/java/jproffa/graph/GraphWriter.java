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
    private File parentDir;
    private File txtfile;

    public GraphWriter() {
        if (System.getenv("DIRECTORY") != null) {
            mainDirectory = System.getenv("DIRECTORY");
        }
        createFiles();
    }

    public GraphWriter(String file) {
        this.fileName = file;
        if (System.getenv("DIRECTORY") != null) {
            mainDirectory = System.getenv("DIRECTORY");
        }
        createFiles();
    }

    public GraphWriter(String file, String testName) {
        this.fileName = file;
        this.testName = testName;
        if (System.getenv("DIRECTORY") != null) {
            mainDirectory = System.getenv("DIRECTORY");
        }
        createFiles();

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

    private void createFiles() {
        parentDir = new File(mainDirectory);
        if (!parentDir.exists()) {
            parentDir.mkdir();
        }
        txtfile = new File(parentDir, fileName);
        if (txtfile.exists()) {
            txtfile.delete();
            txtfile = new File(parentDir, fileName);
        }
    }

    /**
     * Saves the data to a file. This data can be made into a graph by either calling showGraphFromFile or
     * saveGraphFromFile.
     *
     * @param time List of times
     * @param input List of inputs
     */
    public void save(List<Long> time, List<Integer> input) throws Exception {
        try {
            FileWriter fstream = new FileWriter(txtfile, true);
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
