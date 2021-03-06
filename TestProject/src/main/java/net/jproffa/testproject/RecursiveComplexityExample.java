package net.jproffa.testproject;

public class RecursiveComplexityExample {

    public static int linearRecursive(int count, int result) {
        if (count == 0) {
            return result;
        }
        return linearRecursive(count - 1, result + 1);
    }


    public static int quadraticRecursive(int count, int result) {
        if (count == 0) {
            return result;
        }
        result += linearRecursive(count, 0);
        return quadraticRecursive(count - 1, result);
    }

    public static int recursiveFunction(int number) {
        if (number == 1) {
            return number;
        }
        if (number % 2 == 0) {
            return recursiveFunction(number / 2);
        } else {
            return recursiveFunction((number * 3) + 1);
        }
    }
}
