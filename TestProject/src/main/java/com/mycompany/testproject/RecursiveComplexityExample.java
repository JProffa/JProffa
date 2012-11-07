package com.mycompany.testproject;

public class RecursiveComplexityExample {
    
    
    public static int linearRecursive(int count, int result) {
        if (count == 0) {
            return result;
        }
        return linearRecursive(count-1, result+1);
    }
    
    
    public static int squaredRecursive(int count, int result, int org) {
        if (count == 0) {
            return result;
        }
        result += linearRecursive(org, 0);
        return squaredRecursive(count-1, result, org);
    }
}
