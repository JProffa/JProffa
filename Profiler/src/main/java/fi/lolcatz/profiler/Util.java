package fi.lolcatz.profiler;

import static org.objectweb.asm.tree.AbstractInsnNode.*;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.management.ManagementFactory;
import java.util.Iterator;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import com.sun.tools.attach.VirtualMachine;

public class Util implements Opcodes {

    private static boolean agentLoaded = false;

    /**
     * Prints bytes as byte string with newline every 4 bytes.
     *
     * @param bytes Bytes to print.
     */
    public static void printBytes(byte[] bytes) {
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

    /**
     * Write byte array to file.
     *
     * @param filename Name of the file to write to.
     * @param bytes Byte array to write to file.
     */
    public static void writeByteArrayToFile(String filename, byte[] bytes) {
        try {
            DataOutputStream dout = new DataOutputStream(new FileOutputStream(new File(filename)));
            dout.write(bytes);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    /**
     * Create bytecode from ClassNode object.
     *
     * @param cn ClassNode to generate bytecode from.
     * @return Bytecode.
     */
    public static byte[] generateBytecode(ClassNode cn) {
        ClassWriter cw = new ClassWriter(0);
        cn.accept(cw);
        return cw.toByteArray();
    }

    /**
     * Init a ClassNode object using events generated by ClassReader from
     * <code>classFileBuffer</code>.
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

    public static void printInsnList(InsnList insns) {
        for (Iterator<AbstractInsnNode> iter = insns.iterator(); iter.hasNext();) {
            AbstractInsnNode node = iter.next();
            System.out.println("    " + getNodeString(node));
        }
    }

    public static String getNodeString(AbstractInsnNode node) {
        StringBuilder msg = new StringBuilder(getOpcodeString(node.getOpcode()) + " ");
        switch (node.getType()) {
            case LABEL:
                LabelNode labelNode = (LabelNode) node;
                Label label = labelNode.getLabel();
                msg.append("Label " + label.toString());
                break;
            case INSN:
                break;
            case LINE:
                LineNumberNode lineNumberNode = (LineNumberNode) node;
                msg.append("Line: " + lineNumberNode.line + ", Start: " + lineNumberNode.start.getLabel());
                break;
            case VAR_INSN:
                VarInsnNode varInsnNode = (VarInsnNode) node;
                msg.append(varInsnNode.var);
                break;
            case METHOD_INSN:
                MethodInsnNode methodInsnNode = (MethodInsnNode) node;
                msg.append(methodInsnNode.owner + "." + methodInsnNode.name + ":" + methodInsnNode.desc);
                break;
            case INT_INSN:
                IntInsnNode intInsnNode = (IntInsnNode) node;
                msg.append(intInsnNode.operand);
                break;
            case JUMP_INSN:
                JumpInsnNode jumpInsnNode = (JumpInsnNode) node;
                msg.append(jumpInsnNode.label.getLabel());
                break;
            case IINC_INSN:
                IincInsnNode iincInsnNode = (IincInsnNode) node;
                msg.append(iincInsnNode.var + ", " + iincInsnNode.incr);
                break;
            default:
                msg.append(node.toString());
                break;
        }
        return msg.toString();
    }

    public static String getOpcodeString(int opcode) {
        String s;
        switch (opcode) {
            case NOP:
                s = "NOP";
                break;
            case ACONST_NULL:
                s = "ACONST_NULL";
                break;
            case ICONST_M1:
                s = "ICONST_M1";
                break;
            case ICONST_0:
                s = "ICONST_0";
                break;
            case ICONST_1:
                s = "ICONST_1";
                break;
            case ICONST_2:
                s = "ICONST_2";
                break;
            case ICONST_3:
                s = "ICONST_3";
                break;
            case ICONST_4:
                s = "ICONST_4";
                break;
            case ICONST_5:
                s = "ICONST_5";
                break;
            case LCONST_0:
                s = "LCONST_0";
                break;
            case LCONST_1:
                s = "LCONST_1";
                break;
            case FCONST_0:
                s = "FCONST_0";
                break;
            case FCONST_1:
                s = "FCONST_1";
                break;
            case FCONST_2:
                s = "FCONST_2";
                break;
            case DCONST_0:
                s = "DCONST_0";
                break;
            case DCONST_1:
                s = "DCONST_1";
                break;
            case BIPUSH:
                s = "BIPUSH";
                break;
            case SIPUSH:
                s = "SIPUSH";
                break;
            case LDC:
                s = "LDC";
                break;
            case ILOAD:
                s = "ILOAD";
                break;
            case LLOAD:
                s = "LLOAD";
                break;
            case FLOAD:
                s = "FLOAD";
                break;
            case DLOAD:
                s = "DLOAD";
                break;
            case ALOAD:
                s = "ALOAD";
                break;
            case IALOAD:
                s = "IALOAD";
                break;
            case LALOAD:
                s = "LALOAD";
                break;
            case FALOAD:
                s = "FALOAD";
                break;
            case DALOAD:
                s = "DALOAD";
                break;
            case AALOAD:
                s = "AALOAD";
                break;
            case BALOAD:
                s = "BALOAD";
                break;
            case CALOAD:
                s = "CALOAD";
                break;
            case SALOAD:
                s = "SALOAD";
                break;
            case ISTORE:
                s = "ISTORE";
                break;
            case LSTORE:
                s = "LSTORE";
                break;
            case FSTORE:
                s = "FSTORE";
                break;
            case DSTORE:
                s = "DSTORE";
                break;
            case ASTORE:
                s = "ASTORE";
                break;
            case IASTORE:
                s = "IASTORE";
                break;
            case LASTORE:
                s = "LASTORE";
                break;
            case FASTORE:
                s = "FASTORE";
                break;
            case DASTORE:
                s = "DASTORE";
                break;
            case AASTORE:
                s = "AASTORE";
                break;
            case BASTORE:
                s = "BASTORE";
                break;
            case CASTORE:
                s = "CASTORE";
                break;
            case SASTORE:
                s = "SASTORE";
                break;
            case POP:
                s = "POP";
                break;
            case POP2:
                s = "POP2";
                break;
            case DUP:
                s = "DUP";
                break;
            case DUP_X1:
                s = "DUP_X1";
                break;
            case DUP_X2:
                s = "DUP_X2";
                break;
            case DUP2:
                s = "DUP2";
                break;
            case DUP2_X1:
                s = "DUP2_X1";
                break;
            case DUP2_X2:
                s = "DUP2_X2";
                break;
            case SWAP:
                s = "SWAP";
                break;
            case IADD:
                s = "IADD";
                break;
            case LADD:
                s = "LADD";
                break;
            case FADD:
                s = "FADD";
                break;
            case DADD:
                s = "DADD";
                break;
            case ISUB:
                s = "ISUB";
                break;
            case LSUB:
                s = "LSUB";
                break;
            case FSUB:
                s = "FSUB";
                break;
            case DSUB:
                s = "DSUB";
                break;
            case IMUL:
                s = "IMUL";
                break;
            case LMUL:
                s = "LMUL";
                break;
            case FMUL:
                s = "FMUL";
                break;
            case DMUL:
                s = "DMUL";
                break;
            case IDIV:
                s = "IDIV";
                break;
            case LDIV:
                s = "LDIV";
                break;
            case FDIV:
                s = "FDIV";
                break;
            case DDIV:
                s = "DDIV";
                break;
            case IREM:
                s = "IREM";
                break;
            case LREM:
                s = "LREM";
                break;
            case FREM:
                s = "FREM";
                break;
            case DREM:
                s = "DREM";
                break;
            case INEG:
                s = "INEG";
                break;
            case LNEG:
                s = "LNEG";
                break;
            case FNEG:
                s = "FNEG";
                break;
            case DNEG:
                s = "DNEG";
                break;
            case ISHL:
                s = "ISHL";
                break;
            case LSHL:
                s = "LSHL";
                break;
            case ISHR:
                s = "ISHR";
                break;
            case LSHR:
                s = "LSHR";
                break;
            case IUSHR:
                s = "IUSHR";
                break;
            case LUSHR:
                s = "LUSHR";
                break;
            case IAND:
                s = "IAND";
                break;
            case LAND:
                s = "LAND";
                break;
            case IOR:
                s = "IOR";
                break;
            case LOR:
                s = "LOR";
                break;
            case IXOR:
                s = "IXOR";
                break;
            case LXOR:
                s = "LXOR";
                break;
            case IINC:
                s = "IINC";
                break;
            case I2L:
                s = "I2L";
                break;
            case I2F:
                s = "I2F";
                break;
            case I2D:
                s = "I2D";
                break;
            case L2I:
                s = "L2I";
                break;
            case L2F:
                s = "L2F";
                break;
            case L2D:
                s = "L2D";
                break;
            case F2I:
                s = "F2I";
                break;
            case F2L:
                s = "F2L";
                break;
            case F2D:
                s = "F2D";
                break;
            case D2I:
                s = "D2I";
                break;
            case D2L:
                s = "D2L";
                break;
            case D2F:
                s = "D2F";
                break;
            case I2B:
                s = "I2B";
                break;
            case I2C:
                s = "I2C";
                break;
            case I2S:
                s = "I2S";
                break;
            case LCMP:
                s = "LCMP";
                break;
            case FCMPL:
                s = "FCMPL";
                break;
            case FCMPG:
                s = "FCMPG";
                break;
            case DCMPL:
                s = "DCMPL";
                break;
            case DCMPG:
                s = "DCMPG";
                break;
            case IFEQ:
                s = "IFEQ";
                break;
            case IFNE:
                s = "IFNE";
                break;
            case IFLT:
                s = "IFLT";
                break;
            case IFGE:
                s = "IFGE";
                break;
            case IFGT:
                s = "IFGT";
                break;
            case IFLE:
                s = "IFLE";
                break;
            case IF_ICMPEQ:
                s = "IF_ICMPEQ";
                break;
            case IF_ICMPNE:
                s = "IF_ICMPNE";
                break;
            case IF_ICMPLT:
                s = "IF_ICMPLT";
                break;
            case IF_ICMPGE:
                s = "IF_ICMPGE";
                break;
            case IF_ICMPGT:
                s = "IF_ICMPGT";
                break;
            case IF_ICMPLE:
                s = "IF_ICMPLE";
                break;
            case IF_ACMPEQ:
                s = "IF_ACMPEQ";
                break;
            case IF_ACMPNE:
                s = "IF_ACMPNE";
                break;
            case GOTO:
                s = "GOTO";
                break;
            case JSR:
                s = "JSR";
                break;
            case RET:
                s = "RET";
                break;
            case TABLESWITCH:
                s = "TABLESWITCH";
                break;
            case LOOKUPSWITCH:
                s = "LOOKUPSWITCH";
                break;
            case IRETURN:
                s = "IRETURN";
                break;
            case LRETURN:
                s = "LRETURN";
                break;
            case FRETURN:
                s = "FRETURN";
                break;
            case DRETURN:
                s = "DRETURN";
                break;
            case ARETURN:
                s = "ARETURN";
                break;
            case RETURN:
                s = "RETURN";
                break;
            case GETSTATIC:
                s = "GETSTATIC";
                break;
            case PUTSTATIC:
                s = "PUTSTATIC";
                break;
            case GETFIELD:
                s = "GETFIELD";
                break;
            case PUTFIELD:
                s = "PUTFIELD";
                break;
            case INVOKEVIRTUAL:
                s = "INVOKEVIRTUAL";
                break;
            case INVOKESPECIAL:
                s = "INVOKESPECIAL";
                break;
            case INVOKESTATIC:
                s = "INVOKESTATIC";
                break;
            case INVOKEINTERFACE:
                s = "INVOKEINTERFACE";
                break;
            case INVOKEDYNAMIC:
                s = "INVOKEDYNAMIC";
                break;
            case NEW:
                s = "NEW";
                break;
            case NEWARRAY:
                s = "NEWARRAY";
                break;
            case ANEWARRAY:
                s = "ANEWARRAY";
                break;
            case ARRAYLENGTH:
                s = "ARRAYLENGTH";
                break;
            case ATHROW:
                s = "ATHROW";
                break;
            case CHECKCAST:
                s = "CHECKCAST";
                break;
            case INSTANCEOF:
                s = "INSTANCEOF";
                break;
            case MONITORENTER:
                s = "MONITORENTER";
                break;
            case MONITOREXIT:
                s = "MONITOREXIT";
                break;
            case MULTIANEWARRAY:
                s = "MULTIANEWARRAY";
                break;
            case IFNULL:
                s = "IFNULL";
                break;
            case IFNONNULL:
                s = "IFNONNULL";
                break;

            case 0xFFFFFFFF:
                s = "FF";
                break;
            default:
                s = String.format("%02X", opcode);
                break;
        }
        return s;
    }

    /**
     * Load profiler agent to the running Java VM.
     */
    public static void loadAgent() {
        if (agentLoaded) {
            return;
        }
        agentLoaded = true;
        String nameOfRunningVM = ManagementFactory.getRuntimeMXBean().getName();
        String pid = nameOfRunningVM.substring(0, nameOfRunningVM.indexOf('@'));
        // System.out.println("VM pid: " + pid);
        try {
            VirtualMachine vm = VirtualMachine.attach(pid);
            File jarFile = new File(Util.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            String profilerJarPath = jarFile.getPath();
            vm.loadAgent(profilerJarPath);
            vm.detach();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
