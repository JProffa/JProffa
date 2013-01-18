package net.jproffa.testproject;

import java.util.Random;

public class IterativeComplexityExample {
    
    // Random is used occasionally to prevent optimization by the JVM

    public static void linearFunction(int i) {
        int r = 0;
        Random ra = new Random();
        for (int j = 0; j < i; j++) {
            r += ra.nextInt(2);
        }
    }


    public static void approximatedQuadraticFunction(int i) {
        int r = 0;
        for (int j = 0; j < i; j++) {
            for (int k = 0; k < i; k++) {
                r++;
            }
        }
    }

    public static void quadraticFunction(int i) {
        int r = 0;
        for (int j = 0; j < i * i; j++) {
            r++;
        }
    }

    public static void quadraticCoinFlipFunction(int i) {
        int r = 0;
        Random ra = new Random();
        for (int j = 0; j < i; j++) {
            for (int k = 0; k < i; k++) {
                r += ra.nextInt(2);
            }
        }
    }

    public static void cubicFunction(int i) {
        int r = 0;
        for (int j = 0; j < i * i * i; j++) {
            r++;
        }
    }

    public static void logarithmicFunction(long n) {
        while (n >= 1) {
            n = n / 2;
        }
    }
    
    public static int approximatedNlogNFunction(int i) {
        int result = 0;
        for (int j = 0; j < i; j++) {
            int n = i;
            while (n >= 1) {
                n = n / 2;
                result += System.currentTimeMillis();
            }
        }
        return result;
    }
}
