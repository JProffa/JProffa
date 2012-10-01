package fi.lolcatz.profiler;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

public class Util implements Opcodes {

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

    public static ClassNode createProfilerClass() {
        ClassNode cn = new ClassNode();
        cn.version = V1_6;
        cn.superName = "java/lang/Object";
        cn.access = ACC_PUBLIC + ACC_SUPER;
        cn.name = "fi/lolcatz/profiler/ProfileData";
        cn.fields.add(new FieldNode(ACC_PUBLIC + ACC_STATIC, "counter", "I", null, new Integer(0)));
        return cn;
    }
}
