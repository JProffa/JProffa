/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jproffa.graph;

import fi.lolcatz.profiler.Output;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import org.jfree.chart.ChartUtilities;

public class GraphUI {
    
    /**
     * Draws a runtime chart of the outputs size and time lists and saves the
     * chart to a new .png file.
     *
     * @param actual The tested output
     * @param projected The example output
     */
    public static void drawGraph(Output<?> actual, Output<?> projected) {
        //generate example outputs for one param
        //linear, quadric ,nlogn       
        if (System.getenv("PROFILER_VISUALIZATIONS_TO_FILE") != null) {
            Graph g = new Graph("Test", actual, projected);
            Random r = new Random();
            File f = new File(System.getenv("PROFILER_VISUALIZATIONS_TO_FILE"));
            try {
                ChartUtilities.saveChartAsPNG(f, g.getChart(), 500, 270);
            } catch (IOException ex) {
                
            }
        } else if (System.getenv("PROFILER_VISUALIZATIONS_SHOW") != null) {
            Graph g = new Graph("Test", actual, projected);
            g.init();
        }
    }
    
    public static void drawGraphWithOutEnv(Output<?> actual, Output<?> projected, String name) {
                //generate example outputs for one param
        //linear, quadric ,nlogn
        Graph g = new Graph("Test", actual, projected);
        Random r = new Random();
        File f = new File(name + ".png");
        try {
            ChartUtilities.saveChartAsPNG(f, g.getChart(), 500, 270);
        } catch (IOException ex) {
            
        }
    
    }
    
    public static void showGraphWithOutEnv(Output<?> actual, Output<?> projected, String name){
        Graph g = new Graph(name, actual, projected);
        g.init();
    }

}
