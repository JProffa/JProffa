/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.example;

/**
 *
 * @author oorissan
 */
public class FactorialExample {
    
    public static int factorialInt(int number){
        int sum = number;
        for (int i = number-1; i > 0; i--){
            sum *= i;
        }
        return sum;
    }
}
