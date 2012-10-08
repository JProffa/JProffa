package fi.lolcatz.profiler;

import org.objectweb.asm.tree.ClassNode;

public class App {

    public static void main(String[] args) {
        System.out.println("Use this .jar by running it with: java -javaagent:Profiler.jar -jar ProjectToProfile.jar");

        // String nameOfRunningVM = ManagementFactory.getRuntimeMXBean().getName();
        // int p = nameOfRunningVM.indexOf('@');
        // String pid = nameOfRunningVM.substring(0, p);
        // System.out.println("VM pid: " + pid);
        // try {
        // VirtualMachine vm = VirtualMachine.attach(pid);
        // vm.loadAgent("/home/logima/Dropbox/NetBeansProjects/Profiler/target/Profiler-1.0-SNAPSHOT.jar", "");
        // vm.detach();
        // } catch (Exception e) {
        // throw new RuntimeException(e);
        // }
    }
}
