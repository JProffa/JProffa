package fi.lolcatz.profiler;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import static org.objectweb.asm.Opcodes.*;

public class BytecodeCounterVisitor extends ClassVisitor {
    ClassWriter cw = new ClassWriter(0);
 
    public BytecodeCounterVisitor() {
        super(ASM4);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {

        System.out.println(" Method: " + name + desc);
        MethodVisitor mv = cw.visitMethod(access, name, desc, signature, exceptions);
        MethodVisitor counterVisitor = new CounterVisitor(api, mv);
        return counterVisitor;
    }

    private class CounterVisitor extends MethodVisitor {

        public CounterVisitor(int api, MethodVisitor mv) {
            super(api, mv);
        }
        
        private void visitOpcode(int opcode) {
            System.out.println("  OPCODE: " + opcode);
        }

        @Override
        public void visitFieldInsn(int opcode, String owner, String name, String desc) {
            visitOpcode(opcode);
        }

        @Override
        public void visitInsn(int opcode) {
            visitOpcode(opcode);
        }

        @Override
        public void visitIntInsn(int opcode, int operand) {
            visitOpcode(opcode);
        }

        @Override
        public void visitJumpInsn(int opcode, Label label) {
            visitOpcode(opcode);
        }

        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            visitOpcode(opcode);
        }

        @Override
        public void visitTypeInsn(int opcode, String type) {
            visitOpcode(opcode);
        }

        @Override
        public void visitVarInsn(int opcode, int var) {
            visitOpcode(opcode);
        }
    }
}
