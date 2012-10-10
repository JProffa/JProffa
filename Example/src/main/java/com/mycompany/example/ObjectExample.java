/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.example;

/**
 *
 * @author ajmajand
 */
public class ObjectExample {
    
    /**
     * Simplifying things.
     * 
     * @param amount
     * @return 
     */
    public static void createPersons(int amount) {
        for (int i = 0; i < amount; i++) {
            Person p = new Person("tester", i);
        }
    }
    
}
