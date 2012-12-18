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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class GraphWriter implements TestRule {

    private String methodName = "jproffa_data.txt"; 
    private String testName = "test";
    private String mainDirectory = "GraphDataFolder";
    private String classDirectory = "GraphTest";
    private File parentDir;
    private File classDir;
    private File txtfile;
    boolean requireInit = true;

    public GraphWriter() {
        if (System.getenv("DIRECTORY") != null) {
            mainDirectory = System.getenv("DIRECTORY");
        }
        createFolders();
    }

    public GraphWriter(String className) {
        this.classDirectory = className;
        if (System.getenv("DIRECTORY") != null) {
            mainDirectory = System.getenv("DIRECTORY");
        }
        createFolders();
    }

    public GraphWriter(String className, String methodName) {
        this.methodName = methodName;
        this.classDirectory = className;
        if (System.getenv("DIRECTORY") != null) {
            mainDirectory = System.getenv("DIRECTORY");
        }
        createFolders();

    }

    public String getMethodName() {
        return methodName;
    }

    public String getTestName() {
        return testName;
    }

    public String getClassName() {
        return classDirectory;
    }

    public void setMethodName(String fileName) {
        this.methodName = fileName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public void setClassName(String classDirectory) {
        this.classDirectory = classDirectory;
    }

    private void createFolders() {
        parentDir = new File(mainDirectory);
        if (!parentDir.exists()) {
            parentDir.mkdir();
        }
        classDir = new File(parentDir, classDirectory);
        if (!classDir.exists()) {
            classDir.mkdir();
        }

    }

    private void createFile() {
        txtfile = new File(classDir, methodName);
        if (txtfile.exists()) {
            txtfile.delete();
        }
        try {
            txtfile.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(GraphWriter.class.getName()).log(Level.SEVERE, null, ex);
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
            if (requireInit) {
                createFile();
                requireInit = false;
            }
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
                requireInit = true;
                classDirectory = description.getClassName();
                methodName = description.getMethodName();
                testName = description.getClassName() + " " + description.getMethodName();
                base.evaluate();
            }
        };
    }
}
