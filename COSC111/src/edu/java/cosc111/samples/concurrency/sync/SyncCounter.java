package edu.java.cosc111.samples.concurrency.sync;

import edu.java.cosc111.samples.concurrency.Counter;
import java.util.concurrent.ThreadLocalRandom;

public class SyncCounter implements Counter{
    private int nCurCount;
    private final int MAX_COUNT;
    private final Object lck = new Object();
    
    public SyncCounter(){
        this(Integer.MAX_VALUE);
    }
    
    public SyncCounter(int limit){
        if(limit>=0){
            MAX_COUNT = limit;
        }else{
            MAX_COUNT = Integer.MAX_VALUE;
        }
    }
    
    public synchronized int increment(){        
            //Critical section
            if(!isMaxed()){            
                nCurCount = nCurCount + 1;
            }            
        return nCurCount;
    }
    
    public synchronized int getCount(){
        return nCurCount;
    }
    
    public synchronized boolean isMaxed(){
        return nCurCount == MAX_COUNT;
    }

    private void delay(){
        ThreadLocalRandom r = ThreadLocalRandom.current();
        try {
            //Simulate long computation
            Thread.sleep(r.nextLong(1000L, 1500L));
        } catch (InterruptedException ex) {}
    }
}
