package fi.lolcatz.jproffa.testproject;

import java.util.Random;

public class IterativeComplexityExample {

    /**
     * Linear function used to test assertLinear Functions, uses coin flips to nullify optimization
     *
     * @param i input
     */
    public static void linearFunction(int i) {
        int r = 0;
        Random ra = new Random();
        for (int j = 0; j < i; j++) {
            r += ra.nextInt(2);
//          r ++;
        }
    }

    /**
     * Approximated quadratic complexity function for testing approximation on assertQuadratic functions
     *
     * @param i input
     */
    public static void approximatedQuadraticFunction(int i) {
        int r = 0;
        for (int j = 0; j < i; j++) {
            for (int k = 0; k < i; k++) {
                r++;
            }
        }
    }

    /**
     * Quadratic complexity function used to test assertQuadratic functions
     *
     * @param i input
     */
    public static void quadraticFunction(int i) {
        int r = 0;
        for (int j = 0; j < i * i; j++) {
            r++;
        }
    }

    /**
     * Approximated quadratic complexity function used to test assertQuadratic functions without optimization
     *
     * @param i input
     */
    public static void quadraticCoinFlipFunction(int i) {
        int r = 0;
        Random ra = new Random();
        for (int j = 0; j < i; j++) {
            for (int k = 0; k < i; k++) {
                r += ra.nextInt(2);
            }
        }
    }

    /**
     * Cubic function used to test assertCubic functions
     *
     * @param i
     */
    public static void cubicFunction(int i) {
        int r = 0;
        for (int j = 0; j < i * i * i; j++) {
            r++;
        }
    }

    /**
     * Logarithmic function used to test assertLogarithmic function.
     *
     * @param n
     */
    public static void logarithmicFunction(Long n) {
        while (n >= 1) {
            n = n / 2;
        }
    }
     /**
     * Logarithmic function used to test assertLogarithmicApproximated function.
     *
     * @param n
     */
    public static void approximatedNlogNFunction(int i) {
        for (int j = 0; j < i; j++) {
            int n = i;
            while (n >= 1) {
                n = n / 2;
            }
        }

    }
}
