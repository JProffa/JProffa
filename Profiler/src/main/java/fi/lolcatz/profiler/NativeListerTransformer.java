package fi.lolcatz.profiler;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Iterator;

public class NativeListerTransformer implements ClassFileTransformer, Opcodes {
    @Override
    public byte[] transform(
            ClassLoader loader,
            String className,
            Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain,
            byte[] classfileBuffer) throws IllegalClassFormatException {
        ClassNode cn = Util.initClassNode(classfileBuffer);
        for (Object method : cn.methods) {
            MethodNode mn = (MethodNode) method;
            if ((mn.access & ACC_NATIVE) != 0) {
                System.out.println(className + "." + mn.name + mn.desc);
            }
        }
        return null;
    }
}
