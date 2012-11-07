package fi.lolcatz.profiler.archive;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * 
 * @author oorissan
 */
public class CyclomaticComplexityClassVisitor extends ClassVisitor implements Opcodes {

    ClassWriter cw;
    String className;

    public CyclomaticComplexityClassVisitor(ClassWriter cw, String className) {
        super(ASM4, cw);
        this.cw = cw;
        this.className = className;
    }

    /**
     * Create BytecodeVerifier object and return it as a MethodVisitor. {@inheritDoc}
     */
    @Override
    public MethodVisitor visitMethod(int access, String methodName, String desc, String signature, String[] exceptions) {
        System.out.println(" Method: " + methodName + desc);
        MethodVisitor mv = super.visitMethod(access, methodName, desc, signature, exceptions);
        CyclomaticComplexityMethodVisitor verifier = new CyclomaticComplexityMethodVisitor(className, access, methodName, desc, mv);
        return verifier;

    }

}
