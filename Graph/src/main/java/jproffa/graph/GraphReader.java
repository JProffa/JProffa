package jproffa.graph;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GraphReader {

    private String fileLocation;

    public GraphReader(String file) {
        fileLocation = file;
    }

    public List<Line> get(String className, String methodName) throws IOException {
        Gson gson = new Gson();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileLocation + "/" + className + "/" + methodName));
        List<Line> dtList = new ArrayList<Line>();
        while (bufferedReader.ready()) {
            String line = bufferedReader.readLine();
            Line t = gson.fromJson(line, Line.class);
            dtList.add(t);
        }
        return dtList;
    }
}
