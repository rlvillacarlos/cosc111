package edu.java.cosc111.samples.concurrency.sync;

import java.util.concurrent.ThreadLocalRandom;


public class SetterValue1 implements Runnable{
    private final Shareable s;
    private final String name;
    
    public SetterValue1(String name_, Shareable s_){
        name = name_;
        s = s_;
    }

    @Override
    public void run() {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        try {
            Thread.sleep(r.nextLong(1000L, 1500L));
            int n = r.nextInt(0,100);
            s.setValue1(n);
            System.out.printf("%s sets value1 to %d.%n",name,n);
        } catch (InterruptedException ex) {}
    }
}
