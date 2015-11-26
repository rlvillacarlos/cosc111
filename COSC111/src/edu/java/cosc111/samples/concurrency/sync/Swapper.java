package edu.java.cosc111.samples.concurrency.sync;

public class Swapper implements Runnable{
    private final Shareable src;
    private final Shareable dest;
    private final String name;
    
    public Swapper(String name_, Shareable src_, Shareable dest_){
        name = name_;
        src = src_;
        dest = dest_;
    }

    @Override
    public void run() {
        src.swap(dest);        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {}
    }
}
