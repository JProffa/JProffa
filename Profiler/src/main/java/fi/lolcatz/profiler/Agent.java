package fi.lolcatz.profiler;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.util.ArrayList;

public class Agent {

    private static Instrumentation inst;
    private static Logger logger = Logger.getLogger(Agent.class);
    private static final String log4jConfFilename = "log4j.properties";

    /**
     * Method that is called when agent is ran from command line using -javaagent option when launching .jar that you
     * want to profile.
     *
     * @param agentArgs Command line arguments given to agent when called.
     * @param inst Instrumentation object that can be used to instrument classses that are given to this agent.
     * @throws IOException
     */
    public static void premain(String agentArgs, Instrumentation inst) throws IOException {
        loadLoggingConf();
        logger.info("AgentArgs: " + agentArgs);

        Agent.inst = inst;
        printInstrumentationInfo(inst);
        // This adds a new ClassFileTransformer. Each transformer is called once for each loaded class.
        inst.addTransformer(new ProfilerTransformer());

        // Add shutdown hook to print total cost
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                ProfileData.printBasicBlocksCost();
                System.out.println("Total cost: " + ProfileData.getTotalCost());
            }
        });
    }

    /**
     * Method that is called when agent is loaded on runtime using virtualMachine.loadAgent(). Retransforms all
     * modifiable loaded classes.
     *
     * @param args String given to loadAgent() when loading this agent.
     * @param inst Instrumentation object that can be used to instrument classes that are given to this agent.
     * @throws Exception
     */
    public static void agentmain(String args, Instrumentation inst) throws Exception {
        loadLoggingConf();
        logger.info("AgentArgs: " + args);

        if (!inst.isRetransformClassesSupported()) {
            throw new Exception("Retransforming classes not allowed.");
        }

        inst.addTransformer(new ProfilerTransformer(), true);
        Class[] loadedClasses = inst.getAllLoadedClasses();

        ArrayList<Class> modifiableClasses = new ArrayList<Class>(loadedClasses.length);
        for (Class clazz : loadedClasses) {
            if (inst.isModifiableClass(clazz)) {
                modifiableClasses.add(clazz);
            } else logger.info("Unmodifiable class: " + clazz.getName());
        }

        inst.retransformClasses(modifiableClasses.toArray(new Class[modifiableClasses.size()]));
    }

    private static void loadLoggingConf() {
        File f = new File(log4jConfFilename);
        if (f.exists())
            PropertyConfigurator.configure(log4jConfFilename);
        else
            System.out.println(log4jConfFilename + " not found. Loading default configuration file.");
    }

    /**
     * Prints information about instrumentation object.
     *
     * @param inst Object to print information from.
     */
    private static void printInstrumentationInfo(Instrumentation inst) {
        logger.debug("isNativeMethodPrefixSupported: " + inst.isNativeMethodPrefixSupported());
        logger.debug("isRedefineClassesSupported: " + inst.isRedefineClassesSupported());
        logger.debug("isRetransformClassesSupported: " + inst.isRetransformClassesSupported());
        // This print quite a lot of information. Use only when needed.
        // System.out.println("AllLoadedClasses: " + Arrays.toString(inst.getAllLoadedClasses()));
        // System.out.println("InitiatedClasses: " +
        // Arrays.toString(inst.getInitiatedClasses(ClassLoader.getSystemClassLoader())));
    }
}
