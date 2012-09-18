package fi.lolcatz.profiler;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import static org.objectweb.asm.Opcodes.*;

public class BytecodeCountTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(
            ClassLoader loader,
            String className,
            Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain,
            byte[] classfileBuffer) throws IllegalClassFormatException {
        System.out.println("Class: " + className);
        ClassReader cr = new ClassReader(classfileBuffer);
        
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        
        //Wrap the ClassWriter with our custom ClassVisitor
        //ModifierClassWriter mcw=new ModifierClassWriter(Opcodes.ASM4, cw);
        BytecodeCounterVisitor cv = new BytecodeCounterVisitor(cw);
        cr.accept(cv, 0);
        
        return cw.toByteArray();
    }
}
