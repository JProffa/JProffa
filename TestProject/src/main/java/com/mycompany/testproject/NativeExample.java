/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testproject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author oorissan
 */
public class NativeExample {

    public NativeExample() {
    }

    public void writeSomething() throws IOException {
        File file = File.createTempFile("MyFile", ".tmp");
        FileOutputStream stream = new FileOutputStream(file);
        stream.write(5);
        stream.write(9);
        stream.close();
        file.delete();
    }
}
