package fi.lolcatz.profiler;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

public class BytecodeCounterVisitor extends ClassVisitor {
    public BytecodeCounterVisitor() {
        super(ASM4);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        System.out.println("Method: " + name);
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        MethodVisitor counterVisitor = new CounterVisitor(api, mv);
        return counterVisitor;
    }

    private class CounterVisitor extends MethodVisitor {

        public CounterVisitor(int api, MethodVisitor mv) {
            super(api, mv);
        }

        @Override
        public void visitInsn(int opcode) {
            System.out.println(opcode);
        }
    }
}
