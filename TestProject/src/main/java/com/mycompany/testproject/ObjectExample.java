/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testproject;

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
        Person p;
        for (int i = 0; i < amount; i++) {
            p = new Person("tester", i);
        }
    }
    
}
