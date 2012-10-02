package fi.lolcatz.profiler;

import fi.lolcatz.profiler.archive.TreeNodeTransformer;
import java.io.IOException;
import java.lang.instrument.Instrumentation;

public class Agent {

    private static Instrumentation inst;

    /**
     * Method that is called when agent is ran from command line using -javaagent option when launching .jar that you
     * want to profile.
     * 
     * @param agentArgs Command line arguments given to agent when called.
     * @param inst Instrumentation object that can be used to instrument classses that are given to this agent.
     * @throws IOException
     */
    public static void premain(String agentArgs, Instrumentation inst) throws IOException {
        System.out.println("agentArgs: " + agentArgs);
        Agent.inst = inst;
        printInstrumentationInfo(inst);
        ProfileData.addBasicBlock();
        ProfileData.initialize();
        // This adds a new ClassFileTransformer. Each transformer is called once for each loaded class.
        // inst.addTransformer(new TreeNodeTransformer());
        inst.addTransformer(new ProfilerTransformer());
    }

    /**
     * Method that is called when agent is loaded on runtime using virtualMachine.loadAgent().
     * 
     * @param args String given to loadAgent() when loading this agent.
     * @param inst Instrumentation object that can be used to instrument classses that are given to this agent.
     * @throws Exception
     */
    public static void agentmain(String args, Instrumentation inst) throws Exception {
        ProfileData.addBasicBlock();
        ProfileData.initialize();
        inst.addTransformer(new ProfilerTransformer());
    }

    /**
     * Prints information about instrumentation object.
     * 
     * @param inst Object to print information from.
     */
    private static void printInstrumentationInfo(Instrumentation inst) {
        System.out.println(inst);
        System.out.println("isNativeMethodPrefixSupported: " + inst.isNativeMethodPrefixSupported());
        System.out.println("isRedefineClassesSupported: " + inst.isRedefineClassesSupported());
        System.out.println("isRetransformClassesSupported: " + inst.isRetransformClassesSupported());
        // This print quite a lot of information. Use only when needed.
        // System.out.println("AllLoadedClasses: " + Arrays.toString(inst.getAllLoadedClasses()));
        // System.out.println("InitiatedClasses: " +
        // Arrays.toString(inst.getInitiatedClasses(ClassLoader.getSystemClassLoader())));

    }
}
