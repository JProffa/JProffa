package fi.lolcatz.profiler;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Arrays;
import org.objectweb.asm.ClassReader;

class MyTransformer implements ClassFileTransformer {

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

    private void printBytes(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        int bytecounter = 1;
        for (byte b : bytes) {
            sb.append(String.format("%02X ", b));
            if (bytecounter % 4 == 0) {
                sb.append(System.getProperty("line.separator"));
            }
            bytecounter++;
        }
        System.out.println(sb.toString());
    }
}
