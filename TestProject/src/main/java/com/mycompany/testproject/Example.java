package com.mycompany.testproject;

import com.mycompany.testproject.recursives.RecursiveComplexityExample;


public class Example {

    public static void main(String[] args) {
//        sum(1, 2);
        System.out.println(RecursiveComplexityExample.linearRecursive(10, 0));
        System.out.println(RecursiveComplexityExample.squaredRecursive(10, 0, 10));
    }
    
    public static int sum(int a, int b) {
        return a + b;
    }
}
