package fi.lolcatz.profiler;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Prints signatures of methods and fields from classes that are visited.
 */
public class ClassPrinter extends ClassVisitor implements Opcodes {

    public ClassPrinter() {
        super(ASM4);
    }

    /**
     * Visit class signature and print class name and its superclass. {@inheritDoc}
     */
    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        System.out.println(name + " extends " + superName + " {");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitSource(String source, String debug) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitOuterClass(String owner, String name, String desc) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitAttribute(Attribute attr) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitInnerClass(String name, String outerName, String innerName, int access) {
    }

    /**
     * Visit field signature and print name and type. {@inheritDoc}
     */
    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        System.out.println("    " + desc + " " + name);
        return null;
    }

    /**
     * Visit method and print its arguments types and return type. {@inheritDoc}
     */
    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        System.out.println("    " + name + desc);
        return null;
    }

    /**
     * Called at classes end. Prints closing bracket. {@inheritDoc}
     */
    @Override
    public void visitEnd() {
        System.out.println("}");
    }
}
