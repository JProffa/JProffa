package fi.lolcatz.profiler;

import org.apache.log4j.Logger;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.List;

public class DenyThreadsInMain implements ClassFileTransformer, Opcodes {

    private static Logger logger = Logger.getLogger(DenyThreadsInMain.class);

    @Override
    public byte[] transform(
            ClassLoader loader,
            String className,
            Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain,
            byte[] classfileBuffer) throws IllegalClassFormatException {
        try {
            if (!className.equals("java/lang/Thread")) return null;
            ClassNode thread = Util.initClassNode(classfileBuffer);

            for (MethodNode methodNode : (List<MethodNode>) thread.methods) {
                if (!methodNode.name.equals("start")) continue;
                methodNode.instructions.insert(denyThreadStartInsnList());
            }
            Thread.currentThread().getStackTrace();
            return Util.generateBytecode(thread);
        } catch (Throwable t) {
            logger.fatal("Exception while transforming", t);
        }

        return null;
    }

    private InsnList denyThreadStartInsnList() {

        InsnList insnList = new InsnList();
        final String thread = "java/lang/Thread";
        final String string = "java/lang/String";
        final String exception = "java/lang/RuntimeException";
        final String profileData = "fi/lolcatz/profiledata/ProfileData";
        // final String stackTraceElement = "java/lang/StackTraceElement";

        LabelNode endLabel = new LabelNode();

        // allow if profiling disabled
        insnList.add(new MethodInsnNode(INVOKESTATIC, profileData, "isProfilingAllowed", "()Z"));
        insnList.add(new JumpInsnNode(IFEQ, endLabel));

        // allow if starting thread isn't called main
        insnList.add(new LdcInsnNode("main"));
        insnList.add(new MethodInsnNode(INVOKESTATIC, thread, "currentThread", "()L" + thread  + ";"));
        insnList.add(new MethodInsnNode(INVOKEVIRTUAL, thread, "getName", "()L" + string + ";"));
        insnList.add(new MethodInsnNode(INVOKEVIRTUAL, string, "equals", "(Ljava/lang/Object;)Z"));
        insnList.add(new JumpInsnNode(IFEQ, endLabel));

//        insnList.add(new MethodInsnNode(INVOKESTATIC, thread, "currentThread", "()L" + thread  + ";"));
//        insnList.add(new MethodInsnNode(INVOKEVIRTUAL, thread, "getStackTrace", "()[L" + stackTraceElement + ";"));
//        insnList.add(new InsnNode(ICONST_1));
//        insnList.add(new InsnNode(AALOAD));
//        insnList.add(new MethodInsnNode(INVOKEVIRTUAL, stackTraceElement, "getClassName", "()L" + string + ";"));
//        insnList.add(new MethodInsnNode(
//                INVOKESTATIC, "fi/lolcatz/profiler/ClassBlacklist", "isBlacklisted", "(L" + string + ";)Z"));
//        insnList.add(new JumpInsnNode(IFEQ, endLabel));

        // throw exception
        insnList.add(new TypeInsnNode(NEW, exception));
        insnList.add(new InsnNode(DUP));
        insnList.add(new LdcInsnNode("Starting threads from main while profiling not allowed"));
        insnList.add(new MethodInsnNode(INVOKESPECIAL, exception, "<init>", "(L" + string + ";)V"));
        insnList.add(new InsnNode(ATHROW));

        insnList.add(endLabel);
        return insnList;
    }
}
