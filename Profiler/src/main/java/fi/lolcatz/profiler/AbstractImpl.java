package fi.lolcatz.profiler;


public abstract class AbstractImpl{
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
    public long getMarginOfError(long cost) {
        long len = Long.toString(cost).length();
        len = (len/3)*(len/3) + (len%3);
        if(len < 2) return 10;
        
        return len*50;
    }
    
}
