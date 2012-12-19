package jproffa.graph;

import com.google.gson.Gson;
import fi.lolcatz.profiler.Output;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class GraphWriter implements TestRule {

    private static final String DEFAULT_MAIN_DIRECTORY = "GraphDataFolder";
    private static final String MAIN_DIR_ENV_VAR_NAME = "JPROFFA_GRAPH_DIR";

    private File mainDir;
    private String className;
    private String methodName;
    private String annotation = null;
    private boolean clearOnNextWrite = true;

    public GraphWriter() {
        this("UnknownClass", "unknownTest");
    }

    public GraphWriter(String className) {
        this(className, "unknownTest");
    }

    public GraphWriter(String className, String methodName) {
        this.className = className;
        this.methodName = methodName;
        if (System.getenv(MAIN_DIR_ENV_VAR_NAME) != null) {
            mainDir = new File(System.getenv(MAIN_DIR_ENV_VAR_NAME));
        } else {
            mainDir = new File(DEFAULT_MAIN_DIRECTORY);
        }
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setMethodName(String fileName) {
        this.methodName = fileName;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    private File getFile() {
        File classDir = new File(mainDir, className);
        File txtfile = new File(classDir, methodName);
        if (clearOnNextWrite) {
            classDir.mkdirs();
            txtfile.delete();
            clearOnNextWrite = false;
        }
        return txtfile;
    }

    /**
     * Saves the data to a file. This data can be made into a graph by either calling showGraphFromFile or
     * saveGraphFromFile.
     *
     * @param time List of times
     * @param input List of inputs
     */
    public void save(List<Long> time, List<Integer> input) throws IOException {
        File file = getFile();
        FileWriter fstream = new FileWriter(file, true);
        BufferedWriter fbw = new BufferedWriter(fstream);
        Gson gson = new Gson();
        String data = gson.toJson(new Line(time, input, className, methodName, annotation));
        fbw.write(data);
        fbw.newLine();
        fbw.close();
    }

    public void save(Output<?> out) throws IOException {
        save(out.getTime(), out.getSize());
    }

    @Override
    public Statement apply(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                clearOnNextWrite = true;
                className = description.getClassName();
                methodName = description.getMethodName();
                base.evaluate();
            }
        };
    }
}
