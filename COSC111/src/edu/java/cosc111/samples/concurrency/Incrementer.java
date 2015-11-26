package edu.java.cosc111.samples.concurrency;


public class Incrementer implements Runnable {
    private static int a = 0;
    private final Counter ctr;
    
    public Incrementer(Counter ctr_){
        ctr = ctr_;
    }
    
    @Override
    public void run() {     
        ctr.increment();
    }
}
