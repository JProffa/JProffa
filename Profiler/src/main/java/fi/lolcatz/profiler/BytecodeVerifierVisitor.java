/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.lolcatz.profiler;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import static org.objectweb.asm.Opcodes.*;
/**
 *
 * @author oorissan
 */
public class BytecodeVerifierVisitor extends ClassVisitor {
    
    ClassWriter cw;
    String className;
    
     public BytecodeVerifierVisitor(ClassWriter cw, String className) {
        super(ASM4, cw);
        this.cw = cw;
        this.className = className;
    }

     /**
     * Create BytecodeVerifier object and return it as a MethodVisitor.
     * {@inheritDoc}
     */
    @Override
    public MethodVisitor visitMethod(int access, String methodName, String desc, String signature, String[] exceptions){
         System.out.println(" Method: " + methodName + desc);
        MethodVisitor mv = super.visitMethod(access, methodName, desc, signature, exceptions);
        BytecodeVerifier verifier = new BytecodeVerifier(className, access, methodName, desc, mv);
        return verifier;
        
    }

    
}
