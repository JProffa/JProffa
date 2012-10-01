package com.mycompany.example;

public class Example {
    
    public static long counter = 0;

    public static void main(String[] args) {
        sum(1, 2);
        System.out.println("count: " + counter);
    }
    
    
    public static int sum(int a, int b) {
        int x = 0;
        for (int i = 0; i < 10; i++) {
            x++;
        }
        return a + b;
    }
}
