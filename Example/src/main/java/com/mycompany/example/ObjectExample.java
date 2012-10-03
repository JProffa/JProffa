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
    
    public Person[] createPersons(int amount) {
        Person[] returnable = new Person[amount];
        for (int i = 0; i < amount; i++) {
            returnable[i] = new Person("test", i);
        }
        return returnable;
    }
    
}
