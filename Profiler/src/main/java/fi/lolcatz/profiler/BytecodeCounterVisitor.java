package fi.lolcatz.profiler;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

public class BytecodeCounterVisitor extends ClassVisitor {

    ClassWriter cw;

    public BytecodeCounterVisitor(ClassWriter cw) {
        super(ASM4, cw);
        this.cw = cw;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        System.out.println(" Method: " + name + desc);
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        MethodVisitor counterVisitor = new CounterVisitor(api, mv, name);
        return counterVisitor;
    }

    private class CounterVisitor extends MethodVisitor {

        private String methodName;

        public CounterVisitor(int api, MethodVisitor mv, String name) {
            super(api, mv);
            this.methodName = name;
        }

        private void visitOpcode(int opcode) {
            System.out.println("  OPCODE: " + opcode);
        }

        /**
         * Prints methods methodName.
         * Source: <url>http://www.geekyarticles.com/2011/10/manipulating-java-class-files-with-asm.html</url>.
         * {@inheritDoc}
         */
        @Override
        public void visitCode() {
            super.visitCode();
            super.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            super.visitLdcInsn("RUNTIME: method: " + methodName);
            super.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitFieldInsn(int opcode, String owner, String name, String desc) {
            super.visitFieldInsn(opcode, owner, name, desc);
            visitOpcode(opcode);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitInsn(int opcode) {
            super.visitInsn(opcode);
            visitOpcode(opcode);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitIntInsn(int opcode, int operand) {
            super.visitIntInsn(opcode, operand);
            visitOpcode(opcode);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitJumpInsn(int opcode, Label label) {
            super.visitJumpInsn(opcode, label);
            visitOpcode(opcode);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            super.visitMethodInsn(opcode, owner, name, desc);
            visitOpcode(opcode);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitTypeInsn(int opcode, String type) {
            super.visitTypeInsn(opcode, type);
            visitOpcode(opcode);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitVarInsn(int opcode, int var) {
            super.visitVarInsn(opcode, var);
            visitOpcode(opcode);
        }
    }
}
