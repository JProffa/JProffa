/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.lolcatz.profiler;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.MethodNode;
import static org.objectweb.asm.Opcodes.*;
import org.objectweb.asm.tree.analysis.Analyzer;
import org.objectweb.asm.tree.analysis.AnalyzerException;
import org.objectweb.asm.tree.analysis.BasicInterpreter;
import org.objectweb.asm.tree.analysis.Frame;

/**
 *
 * @author oorissan
 */
public class BytecodeVerifier extends MethodVisitor {
    
    String owner;
    MethodVisitor next;
    public BytecodeVerifier(String owner, int access, String name, String desc, MethodVisitor mv){
        super(ASM4, new MethodNode(access, name, desc, null, null));
        this.owner = owner;
        next = mv;
    }
    
    @Override
    public void visitEnd(){
        System.out.println("BytecodeVerifier:...");
        MethodNode mn = (MethodNode) mv;
        Analyzer a = new Analyzer(new BasicInterpreter());
        try{
            Frame[] f = a.analyze(owner, mn);
            System.out.println("Iterating Frame Array...");
            for (Frame v : f){
                System.out.println(v.getStackSize());
            }
            
        }catch (AnalyzerException e){
            throw new RuntimeException(e.getMessage());
        }
        mn.accept(next);
    }
    
}
