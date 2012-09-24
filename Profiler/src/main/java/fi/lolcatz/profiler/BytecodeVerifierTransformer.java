/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.lolcatz.profiler;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

/**
 *
 * @author oorissan
 */
public class BytecodeVerifierTransformer implements ClassFileTransformer  {
    
    @Override
    public byte[] transform(
            ClassLoader loader,
            String className,
            Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain,
            byte[] classfileBuffer) throws IllegalClassFormatException{
        System.out.println("Class: " + className);
        ClassReader cr = new ClassReader(classfileBuffer);
        ClassWriter cw =  new ClassWriter(ClassWriter.COMPUTE_MAXS);
        BytecodeVerifierVisitor cv = new BytecodeVerifierVisitor(cw, className);
        cr.accept(cv, 0);
        DataOutputStream dout;
        byte[] modifiedClassFileBuffer = cw.toByteArray();
        try {
            dout = new DataOutputStream(new FileOutputStream(new File(className.substring(className.lastIndexOf("/")+1) + ".class")));
            dout.write(modifiedClassFileBuffer);
        } catch (Exception ex) {
            Logger.getLogger(BytecodeCountTransformer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return modifiedClassFileBuffer;
    }
    
}
