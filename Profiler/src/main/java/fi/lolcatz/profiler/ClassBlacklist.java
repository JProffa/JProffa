package fi.lolcatz.profiler;

import java.util.ArrayList;
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
        
        classBlacklist.add("org/junit/");
        classBlacklist.add("org/apache/maven/");
        
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
}
