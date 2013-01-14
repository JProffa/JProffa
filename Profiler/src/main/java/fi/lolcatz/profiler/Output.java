package fi.lolcatz.profiler;

import java.util.ArrayList;
import java.util.List;

public class Output<T> {

    private List<T> input;
    private List<Integer> size;
    private List<Long> time;

    public Output() {
        input = new ArrayList<T>();
        size = new ArrayList<Integer>();
        time = new ArrayList<Long>();
    }

    public List<T> getInput() {
        return input;
    }

    public void setInput(List<T> input) {
        this.input = input;
    }

    public List<Integer> getSize() {
        return size;
    }

    public void setSize(List<Integer> size) {
        this.size = size;
    }

    public List<Long> getTime() {
        return time;
    }

    public void setTime(List<Long> time) {
        this.time = time;
    }

    public void addToInput(T object) {
        input.add(object);
    }

    public void addToTime(long t) {
        time.add(t);
    }

    public void addToSize(int i) {
        size.add(i);
    }
    
    public int entryCount() {
        return size.size();
    }

    @Override
    public String toString() {
        if (input.isEmpty()) {
            return "<empty Output>";
        }
        if (input.size() != size.size() || size.size() != time.size()) {
            return "<invalid Output>";
        }
        
        String[][] rows = new String[][] {
            new String[input.size()],
            new String[size.size()],
            new String[time.size()]
        };
        int n = entryCount();
        for (int col = 0; col < n; ++col) {
            rows[0][col] = input.get(col).toString();
            rows[1][col] = size.get(col).toString();
            rows[2][col] = time.get(col).toString();
        }
        
        return formatTable(rows);
    }

    private String formatTable(String[][] rows) {
        StringBuilder sb = new StringBuilder();
        int[] colWidths = getColWidths(rows);
        
        for (int i = 0; i < rows.length; ++i) {
            String[] row = rows[i];
            
            sb.append("| ");
            for (int j = 0; j < row.length; ++j) {
                if (j > 0) {
                    sb.append(" | ");
                }
                
                int colWidth = colWidths[j];
                int padding = colWidth - row[j].length();
                for (int p = 0; p < padding; ++p) {
                    sb.append(' ');
                }
                sb.append(row[j]);
            }
            sb.append(" |").append("\n");
        }
        return sb.toString();
    }

    private int[] getColWidths(String[][] rows) {
        int[] colWidths = new int[entryCount()];
        for (int j = 0; j < entryCount(); ++j) {
            int w = 0;
            for (int i = 0; i < rows.length; ++i) {
                w = Math.max(w, rows[i][j].length());
            }
            colWidths[j] = w;
        }
        return colWidths;
    }

}
