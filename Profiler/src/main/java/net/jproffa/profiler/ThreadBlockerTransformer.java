package net.jproffa.profiler;

import net.jproffa.profiledata.ThreadBlocker;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.List;
import org.apache.log4j.Logger;
import org.objectweb.asm.tree.*;
import static org.objectweb.asm.Opcodes.*;

/**
 * Blocks starting new threads by injecting code into {@code Thread.start()}.
 *
 * <p>
 * {@link ThreadBlocker} decides whether a thread is blocked. By default,
 * {@link DefaultThreadBlockerPolicy} is invoked.
 */
public class ThreadBlockerTransformer implements ClassFileTransformer {
    private static Logger logger = Logger.getLogger(ThreadBlockerTransformer.class);
    
    @Override
    @SuppressWarnings("unchecked")
    public byte[] transform(
            ClassLoader loader,
            String className,
            Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain,
            byte[] classfileBuffer) throws IllegalClassFormatException {
        try {
            if (!className.equals("java/lang/Thread")) {
                return null;
            }
            ClassNode thread = Util.initClassNode(classfileBuffer);

            InsnList insns = getInstructions();
            for (MethodNode methodNode : (List<MethodNode>) thread.methods) {
                if (!methodNode.name.equals("start")) {
                    continue;
                }
                methodNode.instructions.insert(insns);
            }
            Thread.currentThread().getStackTrace();
            return Util.generateBytecode(thread);
        } catch (Throwable t) {
            logger.fatal("Exception while transforming", t);
        }

        return null;
    }

    private InsnList getInstructions() {
        InsnList insnList = new InsnList();
        String className = ThreadBlocker.class.getName().replace('.', '/');
        String methodName = "checkStartingThreadAllowed";
        String methodSig = "(Ljava/lang/Thread;)V";
        insnList.add(new VarInsnNode(ALOAD, 0)); // Load "this" to pass to the method
        insnList.add(new MethodInsnNode(INVOKESTATIC, className, methodName, methodSig));
        return insnList;
    }
}
