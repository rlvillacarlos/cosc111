package edu.java.cosc111.samples.concurrency;

public class GreetingThread extends Thread {
    private final String greeting;

    public GreetingThread(String greeting_) {
        this.greeting = greeting_;
    }

    @Override
    public void run() {
        try {
            sleep(1000);
            System.out.print(greeting);
        } catch (InterruptedException e) {}
    }
}
