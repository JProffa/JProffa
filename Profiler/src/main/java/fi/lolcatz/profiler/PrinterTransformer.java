package fi.lolcatz.profiler;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Arrays;
import org.objectweb.asm.ClassReader;

/**
 * ClassFileTransormer that visits classes using ClassPrinter.
 */
class PrinterTransformer implements ClassFileTransformer {

    /**
     * Visit classes with ClassPrinter.
     * {@inheritDoc}
     */
    @Override
    public byte[] transform(
            ClassLoader loader,
            String className,
            Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain,
            byte[] classfileBuffer) throws IllegalClassFormatException {
        System.out.println("className: " + className);
//        String[] classPath = className.split("/");
//        if (!"SimpleProject".equals(classPath[0])) {
//            return null;
//        }
        ClassPrinter cp = new ClassPrinter();
        ClassReader cr = new ClassReader(classfileBuffer);
        cr.accept(cp, 0);
        return null;
    }
}
