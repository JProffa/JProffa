package fi.lolcatz.profiler.archive;

import fi.lolcatz.profiler.Util;
import java.io.DataOutputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

/**
 * Transformer that runs OurMethodVisitor with classes given for transformation.
 */
public class OurMethodVisitorTransformer implements ClassFileTransformer {

    /**
     * Transform class using OurMethodVisitor. {@inheritDoc}
     */
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
        OurMethodVisitor cv = new OurMethodVisitor(cw, className);
        cr.accept(cv, 0);
        DataOutputStream dout;
        byte[] modifiedClassFileBuffer = cw.toByteArray();
        Util.writeByteArrayToFile(className + ".class", modifiedClassFileBuffer);

        return modifiedClassFileBuffer;
    }

}
