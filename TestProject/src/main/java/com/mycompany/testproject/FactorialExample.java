package com.mycompany.testproject;


public class FactorialExample {
    
    public static int factorialInt(int number){
        int sum = number;
        for (int i = number-1; i > 0; i--){
            sum *= i;
        }
        return sum;
    }
}
