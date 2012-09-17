package fi.lolcatz.profiler;

import java.lang.instrument.Instrumentation;

public class Agent {

    private static Instrumentation inst;

    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("agentArgs: " + agentArgs);
        Agent.inst = inst;
        printInstrumentationInfo(inst);
        //inst.addTransformer(new PrinterTransformer());
        inst.addTransformer(new BytecodeCountTransformer());
    }
    
    public static void agentmain(String args, Instrumentation inst) throws Exception {
        inst.addTransformer(new PrinterTransformer());
    }

    private static void printInstrumentationInfo(Instrumentation inst) {
        System.out.println(inst);
        System.out.println("isNativeMethodPrefixSupported: " + inst.isNativeMethodPrefixSupported());
        System.out.println("isRedefineClassesSupported: " + inst.isRedefineClassesSupported());
        System.out.println("isRetransformClassesSupported: " + inst.isRetransformClassesSupported());
//        System.out.println("AllLoadedClasses: " + Arrays.toString(inst.getAllLoadedClasses()));
//        System.out.println("InitiatedClasses: " +
//                Arrays.toString(inst.getInitiatedClasses(ClassLoader.getSystemClassLoader())));

    }
}
