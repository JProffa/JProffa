package fi.lolcatz.profiler;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Stores the list of blacklisted classes.
 */
public class ClassBlacklist {

    // TODO: perhaps optimize with a Trie or similar?
    private static final Set<String> classBlacklist;

    static {
        classBlacklist = new HashSet<String>();

        // Add classes blacklisted by default

        classBlacklist.add("fi/lolcatz/profiler/");
        classBlacklist.add("fi/lolcatz/profiledata/");

        // ASM
        classBlacklist.add("org/objectweb/asm/");

        // JUnit
        classBlacklist.add("org/junit/");
        classBlacklist.add("org/hamcrest/");
        classBlacklist.add("junit/");
        classBlacklist.add("org/apache/maven/");

        // TestNG
        classBlacklist.add("org/testng/");

        // Mockito
        classBlacklist.add("org/mockito/");

        // EasyMock
        classBlacklist.add("org/easymock/");
        classBlacklist.add("org/unitils/");

        // JMock
        classBlacklist.add("org/jmock/");


        // classBlacklist.add("java/");
        classBlacklist.add("java/lang/Thread");
        classBlacklist.add("sun/");
        classBlacklist.add("com/sun/");

    }

    /**
     * Get list of classes to be blacklisted.
     *
     * @return List of blacklisted classes
     */
    public static Set<String> getBlacklist() {
        return classBlacklist;
    }

    /**
     * Checks if given classname starts with any entry in the blacklist.
     *
     * @param className Classname to be checked
     * @return Is given classname blacklisted
     */
    public static boolean isBlacklisted(String className) {
        for (String blacklistedClass : classBlacklist) {
            if (className.startsWith(blacklistedClass)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Add given classes to the blacklist.
     *
     * @param classToBlacklist List of classes to blacklist.
     */
    public static void add(Class<?>... classToBlacklist) {
        for (Class aClass : classToBlacklist) {
            String className = aClass.getName();
            className = className.replace('.', '/');
            classBlacklist.add(className);
        }
    }
}
