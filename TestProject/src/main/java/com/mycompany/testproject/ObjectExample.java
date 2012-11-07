package com.mycompany.testproject;

public class ObjectExample {
    
    /**
     * Simplifying things.
     * 
     * @param amount
     * @return 
     */
    public static void createPersons(int amount) {
        Person p;
        for (int i = 0; i < amount; i++) {
            p = new Person("tester", i);
        }
    }
    
}
