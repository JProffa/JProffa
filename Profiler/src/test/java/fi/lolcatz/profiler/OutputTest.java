package fi.lolcatz.profiler;

import org.junit.Test;
import static org.junit.Assert.*;

public class OutputTest {
    @Test
    public void testEmptyToString() {
        Output<String> o = new Output<String>();
        assertEquals(o.toString(), "<empty Output>");
    }
    
    @Test
    public void testSingleColToString() {
        Output<String> o = new Output<String>();
        o.addToInput("foo");
        o.addToSize(3);
        o.addToTime(32);
        
        assertEquals(o.toString(), lines(
                "| foo |",
                "|   3 |",
                "|  32 |"
                ));
    }
    
    @Test
    public void testNonemptyToString() {
        Output<String> o = new Output<String>();
        
        o.addToInput("asd");
        o.addToSize(3);
        o.addToTime(123);
        
        o.addToInput("foobar");
        o.addToSize(12);
        o.addToTime(4567890);
        
        o.addToInput("x");
        o.addToSize(123);
        o.addToTime(22);
        
        assertEquals(o.toString(), lines(
                "| asd |  foobar |   x |",
                "|   3 |      12 | 123 |",
                "| 123 | 4567890 |  22 |"
                ));
    }
    
    private static String lines(String... lines) {
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            sb.append(line).append('\n');
        }
        return sb.toString();
    }
}
