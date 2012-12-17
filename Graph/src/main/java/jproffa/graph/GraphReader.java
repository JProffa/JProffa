/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jproffa.graph;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class GraphReader {

    private String fileLocation;

    public GraphReader(String file) {
        fileLocation = file;
    }

    public void showGraphFromFile(String fileName, String... graphName) throws Exception {
        try {
            Gson gson = new Gson();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileLocation + "/" + fileName));
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

    public List<Line> get(String fileName, String... graphName) throws Exception {
        Gson gson = new Gson();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileLocation + "/" + fileName));
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
        return dtList;
    }

    public static List<?> getGraphsByTest(String className, String methodName) {
        return null;
    }

    public static List<?> readGraphsFromFile(String fileName, String methodName) {
        return null;
    }
}
