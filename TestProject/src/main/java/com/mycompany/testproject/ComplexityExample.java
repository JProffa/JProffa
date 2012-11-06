/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testproject;

import java.util.Random;

/**
 *
 * @author ajmajand
 */
public class ComplexityExample {
    
    public static void linearLoop(int i) {
        int r = 0;
        Random ra = new Random();
        for (int j = 0; j < i; j++) {
            r += ra.nextInt(2);
        }
    }
    
    public static void exponentialLoop(int i) {
        int r = 0;
        for (int j = 0; j < i; j++) {
            for (int k = 0; k < i; k++) {
                r ++;
            }
        }
    }
    
    public static void exponentialCoinFlipLoop(int i) {
        int r = 0;
        Random ra = new Random();
        for (int j = 0; j < i; j++) {
            for (int k = 0; k < i; k++) {
                r += ra.nextInt(2);
            }
        }
    }
    
    public static void tripleLoop(int i) {
        int r = 0;
        for (int j = 0; j < i; j++) {
            for (int k = 0; k < i; k++) {
                for (int l = 0; l < i; l++) {
                    r ++;
                }
            }
        }
    }
}
