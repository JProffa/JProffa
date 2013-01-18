package net.jproffa.testproject;

public class ThreadCreation {
    public static void createThreadAndStart() {
        Thread t = new Thread();
        t.start();
        System.out.println("You should never see me!");
    }
}
