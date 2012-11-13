package com.mycompany.testproject;

public class FunctionExample {

    

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
