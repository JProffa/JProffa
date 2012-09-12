package com.mycompany.example;

public class App 
{
    public static void main( String[] args )
    {
        System.out.println(summaInt(1,2));
        summaVoid(3, 2);
    }
    
    public static int summaInt(int a, int b) {
        return a+b;
        
    }
    public static void summaVoid(int a, int b) {
        System.out.println(a+b);
        
    }
    
}
