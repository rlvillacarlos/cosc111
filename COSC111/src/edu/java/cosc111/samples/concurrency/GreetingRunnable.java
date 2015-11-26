package edu.java.cosc111.samples.concurrency;

public class GreetingRunnable implements Runnable{
    private final String greeting;

    public GreetingRunnable(String greeting_) {
        this.greeting = greeting_;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            System.out.print(greeting);
        } catch (InterruptedException e) {}
    }
}