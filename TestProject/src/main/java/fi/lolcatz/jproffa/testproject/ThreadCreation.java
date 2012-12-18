package fi.lolcatz.jproffa.testproject;

public class ThreadCreation {
    public static void createThreadAndStart() {
        System.out.println("Hi");
        Thread t = new Thread();
        t.start();
        System.out.println("You will never see me!");
    }
}
