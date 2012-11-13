package com.mycompany.testproject.iteratives;


public class IterativeExample {
    
    /**
     * Factorial function used to test for loops
     * @param number
     * @return 
     */
    public static int factorialForFunction(int number){
        int sum = number;
        for (int i = number-1; i > 0; i--){
            sum *= i;
        }
        return sum;
    }
    
    /**
     * Factorial function used to test while loops
     * @param number
     * @return 
     */
    public static int factorialWhileFunction(int number){
        int sum = number;
        while (number > 0) {
            sum *= number;
            number --;
        }
        return sum;
    }
    
    
    
}
