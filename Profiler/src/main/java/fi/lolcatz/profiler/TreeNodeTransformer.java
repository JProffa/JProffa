package fi.lolcatz.profiler;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.*;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.List;

import static org.objectweb.asm.Opcodes.*;

/**
 * Test transformation using ASMs Tree API.
 * {@inheritDoc}
 */
public class TreeNodeTransformer implements ClassFileTransformer {

    /**
     * Transform class using tree API. asm4-guide.pdf pg. 96
     * {@inheritDoc}
     */
    @Override
    public byte[] transform(
            ClassLoader loader,
            String className,
            Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain,
            byte[] classfileBuffer) throws IllegalClassFormatException {
        try {
            // Don't touch internal classes for now.
            if (className.startsWith("java/") || className.startsWith("sun/")) {
                return null;
            }

            System.out.println("Class: " + className);

            ClassNode classNode = initClassNode(classfileBuffer);

            System.out.println("ClassNode: " + classNode);

            // Add a static field to class
            classNode.fields.add(new FieldNode(ACC_PUBLIC + ACC_STATIC, "counter", "J", null, new Long(0)));

            // Add print instruction to beginning of each method
            for (MethodNode methodNode : (List<MethodNode>) classNode.methods) {
                // Skip constructors
                if ("<init>".equals(methodNode.name) || "<clinit>".equals(methodNode.name)) {
                    continue;
                }
                InsnList insns = methodNode.instructions;
                InsnList printInsn = getPrintInsn("Method: " + methodNode.name);
                insns.insert(printInsn);
                
                methodNode.maxStack += 4;
                InsnList counterIncreaseInsn = new InsnList();
                counterIncreaseInsn.add(new FieldInsnNode(GETSTATIC, className, "counter", "J"));
                counterIncreaseInsn.add(new InsnNode(LCONST_1));
                counterIncreaseInsn.add(new InsnNode(LADD));
                counterIncreaseInsn.add(new FieldInsnNode(PUTSTATIC, className, "counter", "J"));
                insns.insert(counterIncreaseInsn);

                System.out.println(className + "." + methodNode.name);
            }

            byte[] bytecode = Util.generateBytecode(classNode);
            String filename = className.substring(className.lastIndexOf('/') + 1);
            Util.writeByteArrayToFile(filename + ".class", bytecode);
            return bytecode;
        } catch (Exception e) { // Catch all exceptions because they are silenced otherwise.
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Creates a list of instructions that print the given message. Returned InsnList represents
     * System.out.println(message);
     *
     * @param message Message to print.
     * @return InsnList with proper instructions.
     */
    private InsnList getPrintInsn(String message) {
        InsnList printInsn = new InsnList();
        printInsn.add(new FieldInsnNode(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"));
        printInsn.add(new LdcInsnNode("RUNTIME: " + message));
        printInsn.add(new MethodInsnNode(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V"));
        return printInsn;
    }

    /**
     * Init a ClassNode object using events generated by ClassReader from <code>classFileBuffer</code>.
     *
     * @param classfileBuffer Class as byte[] to generate ClassNode from.
     * @return Initialized ClassNode object.
     */
    public static ClassNode initClassNode(byte[] classfileBuffer) {
        ClassNode cn = new ClassNode();
        ClassReader cr = new ClassReader(classfileBuffer);
        cr.accept(cn, 0);
        return cn;
    }

}
