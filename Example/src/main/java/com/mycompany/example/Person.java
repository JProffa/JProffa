/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.example;

/**
 *
 * @author ajmajand
 */
public class Person {
    
    private String name;
    private int age;
    
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    
    
}
