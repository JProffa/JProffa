package com.mycompany.testproject.iteratives;

import java.util.Random;

public class IterativeComplexityExample {
    
    /**
     * Linear function used to test assertLinear Functions, uses coin flips to
     * nullify optimization
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
     * Approximated squared complexity function for testing approximation on 
     * assertSquared functions
     * @param i input
     */
    public static void approximatedSquaredFunction(int i) {
        int r = 0;
        for (int j = 0; j < i; j++) {
            for (int k = 0; k < i; k++) {
                r ++;
            }
        }
    }
    
    /**
     * Squared complexity function used to test assertSquared functions
     * @param i input
     */
    public static void squaredFunction(int i) {
        int r = 0;
        for (int j = 0; j < i*i; j++) {
            r ++;
        }
    }
    
    /**
     * Approximated squared complexity function used to test assertSquared functions without optimization
     * @param i input
     */
    public static void squaredCoinFlipFunction(int i) {
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
     * @param i 
     */
    public static void cubicFunction(int i) {
        int r = 0;
        for (int j = 0; j < i*i*i; j++) {
            r++;
        }
    }
    
    /**
     * Logarithmic function used to test assertLogarithmic function.
     * @param n 
     */
    public static void logarithmicFunction(long n) {
        while (n >= 1) {
            n = n/2;
        }
    }
}
