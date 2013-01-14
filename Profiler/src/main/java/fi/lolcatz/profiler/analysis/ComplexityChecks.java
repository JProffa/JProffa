package fi.lolcatz.profiler.analysis;

import fi.lolcatz.profiler.Output;

public class ComplexityChecks {
    public static double DEFAULT_TOLERANCE = 1.1;
    
    private ComplexityChecks() {
    }

    public static void assertLinearOrFaster(Output<?> output) throws AssertionError {
        assertLinearOrFaster(output, DEFAULT_TOLERANCE);
    }
    
    public static void assertLinearOrFaster(Output<?> output, double tolerance) throws AssertionError {
        assertLinearOrFaster("Expected algorithm to be no slower than O(n)", output, tolerance);
    }
    
    public static void assertLinearOrFaster(String message, Output<?> output, double tolerance) throws AssertionError {
        if (!isLinearOrFaster(output, tolerance)) {
            throw new AssertionError(message);
        }
    }
    
    public static void assertQuadraticOrFaster(Output<?> output) throws AssertionError {
        assertQuadraticOrFaster(output, DEFAULT_TOLERANCE);
    }
    
    public static void assertQuadraticOrFaster(Output<?> output, double tolerance) throws AssertionError {
        assertQuadraticOrFaster("Expected algorithm to be no slower than O(n^2)", output, tolerance);
    }

    public static void assertQuadraticOrFaster(String message, Output<?> output, double tolerance) throws AssertionError {
        if (!isQuadraticOrFaster(output, tolerance)) {
            throw new AssertionError(message);
        }
    }
    
    public static void assertNlogNOrFaster(Output<?> output) throws AssertionError {
        assertNlogNOrFaster(output, DEFAULT_TOLERANCE);
    }
    
    public static void assertNlogNOrFaster(Output<?> output, double tolerance) throws AssertionError {
        assertNlogNOrFaster("Expected algorithm to be no slower than O(n*log(n))", output, tolerance);
    }

    public static void assertNlogNOrFaster(String message, Output<?> output, double tolerance) throws AssertionError {
        if (!isNlogNOrFaster(output, tolerance)) {
            throw new AssertionError(message);
        }
    }
    

    /**
     * Calculates the runtime speed of the parameter output.
     *
     * @return True if the output is linear or faster, false if the output is
     *         slower
     */
    public static boolean isLinearOrFaster(Output<?> output, double tolerance) {
        if (output.getSize().size() < 2) {
            return false;
        }
        return Complexity.LINEAR.isBelow(output, tolerance);
        /*
        if (output.getSize().size() < 2) {
            return false;
        }
        Integer x0 = output.getSize().get(0);
        Long y0 = output.getTime().get(0);
        Integer xn = output.getSize().get(output.getSize().size() - 1);
        Long yn = output.getTime().get(output.getTime().size() - 1);
        double a = Math.abs(y0 - yn) / Math.abs(x0 - xn);
        double b = y0 - a * x0;
        for (int i = 0; i < output.getTime().size(); i++) {
            long time = output.getTime().get(i);
            double function = a * output.getSize().get(i) + b;
            boolean linearOrFaster = time <= function * tolerance;
            if (!linearOrFaster) {
                return false;
            }
        }
        return true;
        */
    }

    /**
     * Calculates the runtime speed of the parameter output.
     * 
     * <p>
     * a, b and c are from the function an*n+bn+c
     * denom is the common divider for them.
     *
     * @param out size must be >= 3
     * @return True if the output is quadratic or faster, false if the output is
     *         slower
     */
    public static boolean isQuadraticOrFaster(Output<?> out, double tolerance) {
        if (out.getSize().size() < 3) {
            return false;
        }
        return Complexity.QUADRATIC.isBelow(out, tolerance);
        /*
        Integer x1 = out.getSize().get(0);
        Integer x2 = out.getSize().get(1);
        Integer x3 = out.getSize().get(2);

        long y1 = out.getTime().get(0);
        long y2 = out.getTime().get(1);
        long y3 = out.getTime().get(2);

        double denom = (x1 - x2) * (x1 - x3) * (x2 - x3);
        double a = (x3 * (y2 - y1) + x2 * (y1 - y3) + x1 * (y3 - y2)) / denom;
        double b = (x3 * x3 * (y1 - y2) + x2 * x2 * (y3 - y1) + x1 * x1 * (y2 - y3)) / denom;
        double c = (x2 * x3 * (x2 - x3) * y1 + x3 * x1 * (x3 - x1) * y2 + x1 * x2 * (x1 - x2) * y3) / denom;

        for (int i = 0; i < out.getTime().size(); i++) {
            long time = out.getTime().get(i);
            double function = (a * out.getSize().get(i) * out.getSize().get(i)) + (b * out.getSize().get(i)) + c;
            boolean exponentialOrFaster = time <= function * tolerance;
            if (!exponentialOrFaster) {
                return false;
            }
        }
        return true;
        */
    }
    
    /**
     * Calculates whether the class variable output is O(NlogN).
     *
     * @return True if output is O(NlogN), false if not
     */
    public static boolean isNlogNOrFaster(Output<?> out, double tolerance) {
        if (out.getSize().size() < 2) {
            return false;
        }
        
        return Complexity.NLOGN.isBelow(out, tolerance);
        
        /*
        Integer x1 = out.getSize().get(0);
        Integer x2 = out.getSize().get(1);
        Integer x3 = out.getSize().get(2);

        long y1 = out.getTime().get(0);
        long y2 = out.getTime().get(1);
        long y3 = out.getTime().get(2);

        double denom = (x1 - x2) * (x1 - x3) * (x2 - x3);
        double a = (x3 * (y2 - y1) + x2 * (y1 - y3) + x1 * (y3 - y2)) / denom;
        double b = (x3 * (Math.log(x3) / Math.log(2)) * (y1 - y2) + x2 * (Math.log(x2) / Math.log(2)) * (y3 - y1) + x1 * (Math.log(x1) / Math.log(2)) * (y2 - y3)) / denom;
        double c = (x2 * x3 * (x2 - x3) * y1 + x3 * x1 * (x3 - x1) * y2 + x1 * x2 * (x1 - x2) * y3) / denom;

        for (int i = 0; i < out.getTime().size(); i++) {
            long time = out.getTime().get(i);
            double function = (a * out.getSize().get(i) * (Math.log(out.getSize().get(i)) / Math.log(2))) + (b * out.getSize().get(i)) + c;
            //System.out.println("function: " + a + "*" + out.getSize().get(i) * (Math.log(out.getSize().get(i)) / Math.log(2)) + "+" + b + "*" + out.getSize().get(i) + "+" + c + " = " + function);
            boolean nlogn = time * 1.004 >= function && time - (time * 0.004) <= function;
            if (!nlogn) {
                return false;
            }
        }

        return true;
        */
    }
}
