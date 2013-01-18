package fi.lolcatz.jproffa.test;

import fi.lolcatz.profiledata.ProfileData;
import fi.lolcatz.profiler.RunnableEx;
import fi.lolcatz.profiler.WithProfiling;
import java.util.concurrent.Semaphore;
import org.junit.Before;
import org.junit.Test;

public class BackgroundThreadTest {
    
    @Before
    public void setUp() {
        ProfileData.disableProfiling();
    }
    
    @Test
    public void backgroundThreadsCanStartNewThreads() throws Exception {
        final Semaphore sem = new Semaphore(0);
        final Thread bg = new Thread(new Runnable() {
            @Override
            public void run() {
                sem.acquireUninterruptibly();
                Thread subthread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
                subthread.setDaemon(true);
                subthread.start();
                try {
                    subthread.join();
                } catch (InterruptedException ex) {
                }
            }
        });
        bg.setDaemon(true);
        bg.start();
        
        WithProfiling.in(new RunnableEx() {
            @Override
            public void run() throws Exception {
                sem.release();
                bg.join();
            }
        });
    }
    
    @Test
    public void backgroundThreadsCanTriggerGC() throws Exception {
        final Semaphore sem = new Semaphore(0);
        final Thread bg = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; ++i) {
                    for (int j = 0; j < 100000; ++j) {
                        newObject();
                    }
                    System.gc();
                }
            }
        });
        bg.setDaemon(true);
        bg.start();
        
        WithProfiling.in(new RunnableEx() {
            @Override
            public void run() throws Exception {
                sem.release();
                bg.join();
            }
        });
    }
    
    private Object newObject() { // Works around NB's "unused" warning for a simple new Object.
        return new Object();
    }
}
