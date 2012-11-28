package fi.lolcatz.profiler;

import fi.lolcatz.profiledata.ProfileData;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;


public abstract class AbstractImpl {

    private static Logger logger = Logger.getLogger(AbstractImpl.class);

    String methodName, className;
    
    public void setMethodName(String name) {
        this.methodName = name;
    }

    public void setClassName(String name) {
        this.className = name;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }
    
    private Method getMethod(Class<?>... parameterTypes) throws Exception {
        if (className == null) {
            throw new NullPointerException("className is null");
        }
        if (methodName == null) {
            throw new NullPointerException("methodName is null");
        }
        Class<?> c = Class.forName(className);
        outer:
        for (Method method : c.getMethods()) {
            if (!methodName.equals(method.getName())) continue;
            Class<?>[] methodParameterTypes = method.getParameterTypes();
            if (methodParameterTypes.length != parameterTypes.length) continue;
            for (int i = 0; i < parameterTypes.length; i++) {
                if (!changeToPrimitive(parameterTypes[i]).equals(changeToPrimitive(methodParameterTypes[i]))) {
                    continue outer;
                }
            }
            return method;
        }
        throw new NoSuchMethodException("Method with name " + methodName + " and suitable parameters not found from " + className);
    }
    
    /**
     * Run static method with given inputs.
     * @param inputs Input variables. Primitive boxing classes (Integer, Boolean, etc.) must not be nulls.
     * @return Total cost
     * @throws Exception 
     */
    public long runStatic(Object... inputs) throws Exception {
        return run(null, inputs);
    }
    
    /**
     * Run method of given instance with given inputs.
     * @param instance Instance of the class which we are testing.
     * @param inputs Input variables. Primitive boxing classes (Integer, Boolean, etc.) must not be nulls.
     * @return Total cost
     * @throws Exception 
     */
    public long run(Object instance, Object... inputs) throws Exception {
        ProfileData.disallowProfiling();
        System.runFinalization();
        System.gc();
        ProfileData.resetCounters();
        try {
            Class<?>[] parameterTypes = new Class<?>[inputs.length];
            for (int i = 0; i < inputs.length; i++) {
                parameterTypes[i] = inputs[i].getClass();
            }
            Method m = getMethod(parameterTypes);
            if (!Modifier.isStatic(m.getModifiers()) && instance == null) {
                throw new NullPointerException("Cannot access instance method of a null instance");
            }
            ProfileData.allowProfiling();
            m.invoke(instance, (Object[]) inputs);
        } catch (Exception x) {
            logger.fatal("Exception when running", x);
            ProfileData.allowProfiling();
            throw x;
        }
        return Util.getTotalCost();
    }
    
    public long getMarginOfError(long cost) {
        long len = Long.toString(cost).length();
        len = (len/3)*(len/3) + (len%3);
        if(len < 2) return 10;
        
        return len*50;
    }

    private Class<?> changeToPrimitive(Class<?> aClass) {
        if (Character.class.equals(aClass)) {
            return char.class;
        }
        if (Byte.class.equals(aClass)) {
            return byte.class;
        }
        if (Short.class.equals(aClass)) {
            return short.class;
        }
        if (Integer.class.equals(aClass)) {
            return int.class;
        }
        if (Long.class.equals(aClass)) {
            return long.class;
        }
        if (Float.class.equals(aClass)) {
            return float.class;
        }
        if (Double.class.equals(aClass)) {
            return double.class;
        }
        if (Boolean.class.equals(aClass)) {
            return boolean.class;
        }
        if (Void.class.equals(aClass)) {
            return void.class;
        }
        return aClass;
    }
    
}
