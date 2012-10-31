package fi.lolcatz.profiler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class that stores list of blacklisted classes.
 */
public class ClassBlacklist {
    
    private static List<String> classBlacklist;
    
    static {
        classBlacklist = new ArrayList<String>();
        
        // Add classes blacklisted by default
        
        classBlacklist.add("fi/lolcatz/profiler/");
        
        // ASM
        classBlacklist.add("org/objectweb/asm/");
        
        // JUnit
        classBlacklist.add("org/junit/");
        classBlacklist.add("org/hamcrest/");
        classBlacklist.add("junit/");
        classBlacklist.add("org/apache/maven/");
        
        // Mockito
        classBlacklist.add("org/mockito/");
        
        // EasyMock
        classBlacklist.add("org/easymock/");
        
        // JMock
        classBlacklist.add("org/jmock/");
        
        
        classBlacklist.add("java/");
        classBlacklist.add("sun/");
        classBlacklist.add("com/sun/");
        
    }
    
    /**
     * Get list of classes to be blacklisted.
     * @return List of blacklisted classes
     */
    public static List<String> getBlacklist() {
        return classBlacklist;
    }
    
    /**
     * Set list of classes to be blacklisted.
     * @param classBlacklistToSet List of classes to be blacklisted
     */
    public static void setBlacklist(List<String> classBlacklistToSet) {
        classBlacklist = classBlacklistToSet;
    }
    
    /**
     * Checks if given classname starts with any entry in the blacklist.
     * @param className Classname to be checked
     * @return Is given classname blacklisted
     */
    public static boolean isBlacklisted(String className) {
        for (String blacklistedClass : classBlacklist) {
            if (className.startsWith(blacklistedClass)) return true;
        }
        return false;
    }

    /**
     * Add given classes to the blacklist.
     * @param classToBlacklist List of classes to blacklist.
     */
    public static void add(Class<?>... classToBlacklist) {
        for (Class aClass : classToBlacklist) {
            String className = aClass.getName();
            className = className.replace('.', '/');
            classBlacklist.add(className);
        }
    }
    
    /**
     * Add given classnames to the blacklist.
     * @param classNamesToBlacklist List of classnames to blacklist.
     */
    public static void add(String... classNamesToBlacklist) {
        classBlacklist.addAll(Arrays.asList(classNamesToBlacklist));
    }
}
