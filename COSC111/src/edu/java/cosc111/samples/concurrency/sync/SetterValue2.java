package edu.java.cosc111.samples.concurrency.sync;

import java.util.concurrent.ThreadLocalRandom;


public class SetterValue2 implements Runnable{
    private final Shareable s;
    private final String name;
    
    public SetterValue2(String name_, Shareable s_){
        name = name_;
        s = s_;
    }

    @Override
    public void run() {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        try {
            Thread.sleep(r.nextLong(1000L, 1500L));
            int n = r.nextInt(0,100);
            System.out.printf("%s sets value2 to %d.%n",name,n);
            s.setValue1(n);
        } catch (InterruptedException ex) {}
    }
}
