package fi.lolcatz.profiler.archive;

import fi.lolcatz.profiler.Util;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

/**
 * Test transformation using ASMs Tree API. {@inheritDoc}
 */
public class TreeNodeTransformer implements ClassFileTransformer, Opcodes {

    /**
     * Transform class using tree API. asm4-guide.pdf pg. 96 {@inheritDoc}
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

            ClassNode classNode = Util.initClassNode(classfileBuffer);

            System.out.println("ClassNode: " + classNode);

            // Add a static field to class
            // classNode.fields.add(new FieldNode(ACC_PUBLIC + ACC_STATIC, "counter", "J", null, new Long(0)));

            String profileDataClass = "fi/lolcatz/profiler/ProfileData";
            // Add print instruction to beginning of each method
            for (MethodNode methodNode : (List<MethodNode>) classNode.methods) {
                // Skip constructors
                if ("<init>".equals(methodNode.name) || "<clinit>".equals(methodNode.name)) {
                    continue;
                }
                InsnList insns = methodNode.instructions;
                InsnList printInsn = getPrintInsn("Method: " + methodNode.name);
                insns.insert(printInsn);

                methodNode.maxStack += 6;
                InsnList counterIncreaseInsn = new InsnList();
                counterIncreaseInsn.add(new FieldInsnNode(GETSTATIC, profileDataClass, "callsToBasicBlock", "[J"));
                counterIncreaseInsn.add(new InsnNode(ICONST_0));
                counterIncreaseInsn.add(new InsnNode(DUP2));
                counterIncreaseInsn.add(new InsnNode(LALOAD));
                counterIncreaseInsn.add(new InsnNode(LCONST_1));
                counterIncreaseInsn.add(new InsnNode(LADD));
                counterIncreaseInsn.add(new InsnNode(LASTORE));
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

}
