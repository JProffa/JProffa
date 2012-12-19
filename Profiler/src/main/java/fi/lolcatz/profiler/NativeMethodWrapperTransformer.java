package fi.lolcatz.profiler;

import fi.lolcatz.profiledata.ProfileData;

import org.apache.log4j.Logger;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.LinkedList;
import java.util.List;

public class NativeMethodWrapperTransformer implements ClassFileTransformer, Opcodes {

    private static Logger logger = Logger.getLogger(NativeMethodWrapperTransformer.class);
    public String prefix;
    public String className;

    public NativeMethodWrapperTransformer(String prefix) {
        this.prefix = prefix;
    }

    @Override
    @SuppressWarnings("unchecked")
    public byte[] transform(
            ClassLoader loader,
            String className,
            Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain,
            byte[] classfileBuffer) throws IllegalClassFormatException {
        try {
            this.className = className;
            logger.info("Class " + className);
            ClassNode cn = Util.initClassNode(classfileBuffer);

            List<MethodNode> wrappers = new LinkedList<MethodNode>();
            for (MethodNode mn : (List<MethodNode>) cn.methods) {
                logger.info("  Method " + mn.name);
                if ((mn.access & ACC_NATIVE) != 0) {
                    if (!mn.desc.equals("()V")) {
                        logger.warn("    Native method " + className + "." + mn.name + " with this desc isn't supported yet: " + mn.desc);
                        continue;
                    }
                    logger.info("    Original InsnList: " + Util.getInsnListString(mn.instructions));
                    System.out.println("    Wrappable native method " + className + "." + mn.name + " found!");
                    logger.info("    Node: " + mn.toString() + " Desc: " + mn.desc + " sign: " + mn.signature + " exep: " +
                            mn.exceptions + " acc: " + mn.access + " ins: " + mn.instructions + " attrs: " + mn.attrs +
                    " localvars: " + mn.localVariables + " maxstack: " + mn.maxStack + " maxlocals: " + mn.maxLocals);

                    MethodNode wrapper = new MethodNode();
                    wrapper.name = mn.name;
                    mn.name = prefix + mn.name;
                    wrapper.access = mn.access - ACC_NATIVE;

                    wrapper.annotationDefault = mn.annotationDefault;
                    wrapper.attrs = mn.attrs;
                    wrapper.desc = mn.desc;
                    wrapper.exceptions = mn.exceptions;
                    wrapper.invisibleAnnotations = mn.invisibleAnnotations;
                    wrapper.localVariables = mn.localVariables;
                    wrapper.maxStack = 4;
                    wrapper.maxLocals = 4;
                    wrapper.invisibleParameterAnnotations = mn.invisibleParameterAnnotations;
                    wrapper.signature = mn.signature;
                    wrapper.tryCatchBlocks = mn.tryCatchBlocks;
                    wrapper.visibleAnnotations = mn.visibleAnnotations;
                    wrapper.visibleParameterAnnotations = mn.visibleParameterAnnotations;

                    wrapper.instructions.add(getPrintInsn("We're at " + className + "." + wrapper.name + "!"));
                    wrapper.instructions.add(callWrappedNative(mn));
                    wrapper.instructions.add(new InsnNode(RETURN));
                    logger.trace(Util.getInsnListString(wrapper.instructions));
                    wrappers.add(wrapper);
                }
            }
            cn.methods.addAll(wrappers);



            byte[] newBytecode = Util.generateBytecode(cn);
            String filename = className.substring(className.lastIndexOf('/') + 1);
            Util.writeByteArrayToFile(filename + ".class", newBytecode);
            return newBytecode;
        } catch (Exception e) {
            logger.fatal("Exception when running NativeMethodWrapperTransformer.transform", e);
        }
        return null;
    }

    private InsnList callWrappedNative(MethodNode mn) {
        InsnList insns = new InsnList();
        int invokeOpcode;
        if ((mn.access & ACC_STATIC) != 0) invokeOpcode = INVOKESTATIC;
        else if ((mn.access & ACC_PRIVATE) != 0) invokeOpcode = INVOKESPECIAL;
        else invokeOpcode = INVOKEVIRTUAL;
        insns.add(new MethodInsnNode(invokeOpcode, className, mn.name, mn.desc));
        return insns;
    }

    /**
     * Creates new InsnList containing bytecode instructions to increment counter.
     *
     * @return Counter increment InsnList
     */
    private InsnList createCounterIncrementInsnList(int basicBlockIndex) {
        InsnList counterIncrementInsnList = new InsnList();
        counterIncrementInsnList.add(intPushInsn(basicBlockIndex));
        counterIncrementInsnList.add(new MethodInsnNode(INVOKESTATIC, "fi/lolcatz/profiledata/ProfileData",
                "incrementCallsToBasicBlock", "(I)V"));
        return counterIncrementInsnList;
    }

    /**
     * Creates an instruction that can be used to push any Int value to stack.
     *
     * @param i Int to push to stack.
     * @return Instruction to push <code>i</code> to stack.
     */
    private AbstractInsnNode intPushInsn(int i) {
        if (i < 128) {
            return new IntInsnNode(BIPUSH, i);
        } else if (i < 32768) {
            return new IntInsnNode(SIPUSH, i);
        } else {
            return new LdcInsnNode(i);
        }
    }

    private InsnList getPrintInsn(String message) {
        InsnList printInsn = new InsnList();
        printInsn.add(new FieldInsnNode(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"));
        printInsn.add(new LdcInsnNode("RUNTIME: " + message));
        printInsn.add(new MethodInsnNode(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V"));
        return printInsn;
    }
}
