/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.example;

/**
 *
 * @author oorissan
 */
public class FunctionExample {

    public static int iterativeFunction(int number) {
        while (number != 1) {
            if (number % 2 == 0) {
                number /= 2;
            } else {
                number *= 3;
                number += 1;
            }
        }
        return number;
    }

    public static int recursiveFunction(int number) {
        if (number == 1){
            return number;
        }
        if (number%2==0){
            return recursiveFunction(number/2);
        }
        else{
            return recursiveFunction((number*3)+1);
        }      
    }
}
