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
        BytecodeCounterVisitor cv = new BytecodeCounterVisitor();
        cr.accept(cv, 0);
        return null;
    }
}
