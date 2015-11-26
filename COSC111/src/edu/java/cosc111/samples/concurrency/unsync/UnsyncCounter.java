package edu.java.cosc111.samples.concurrency.unsync;

import edu.java.cosc111.samples.concurrency.Counter;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UnsyncCounter implements Counter{
    //nCurCount <= MAX_COUNT
    private int nCurCount;
    //MAX_COUNT must be >=0
    private final int MAX_COUNT;
    
    public UnsyncCounter(){
        this(Integer.MAX_VALUE);
    }
    
    //Pre-Condition: limit >=0
    //Post-Condition: MAX_COUNT == limit
    //@throws IllegalArgumentException if limit <0
    public UnsyncCounter(int limit){
        if(limit>=0){
            MAX_COUNT = limit;
        }else{
            throw new IllegalArgumentException();
        }
    }
    //Pre-condition: nCurCount <= MAX_COUNT
    //Post-condition: nCurCount <= MAX_COUNT, nCurCount has increased by 1
    @Override
    public int increment(){
        if(!isMaxed()){
            delay();
            nCurCount = nCurCount + 1;
        }            

        return nCurCount;
    }
    
    @Override
    public int getCount(){
        return nCurCount;
    }
    
    @Override
    public boolean isMaxed(){
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
