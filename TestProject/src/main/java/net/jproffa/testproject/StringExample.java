package net.jproffa.testproject;

public class StringExample {

    public static String stringReplace(String input, String replace, String newString) {
        input = input.replaceAll(replace, newString);
        return input;
    }
}
