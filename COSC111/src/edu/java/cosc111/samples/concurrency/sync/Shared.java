package edu.java.cosc111.samples.concurrency.sync;

import java.util.concurrent.ThreadLocalRandom;

public class Shared implements Shareable{
    private int value1;
    private int value2;    
    private final int id;
    
    private final Object lck1 = new Object();
    private final Object lck2 = new Object();
    
    private ThreadLocalRandom r;
    private static boolean bDoDelay = false;

    public static void doDelay(boolean b){
        bDoDelay = b;
    }
    
    public Shared(int value1_, int value2_, int id_){
        value1 = value1_;
        value2 = value2_;
        id = id_;
    }
    
    @Override
    public int getValue1(){
        synchronized(lck1){
            delay();
            return value1;
        }
    }

    @Override
    public synchronized int setValue1(int v) {
        synchronized(lck1){
            delay();
            int tmp = value1;
            value1 = v;
            return tmp;
        }
    }

    @Override
    public synchronized int getValue2(){
        synchronized(lck2){
            delay();
            return value2;
        }
    }

    @Override
    public synchronized int setValue2(int v) {
        synchronized(lck1){
            delay();
            int tmp = value2;
            value2 = v;
            return tmp;
        }
    }
    
    @Override
    public void swap (Shareable s){
//        delay();
//        synchronized(this){
//            delay();
//            synchronized(s){
//                int tmp = s.getValue1();
//                s.setValue1(value1);
//                value1 = tmp;
//
//                tmp = s.getValue2();
//                s.setValue2(value2);
//                value2 = tmp;
//            }
//        }
        if(this.id>((Shared)s).id){
            delay();
            synchronized(this){
                delay();
                synchronized(s){
                    int tmp = s.getValue1();
                    s.setValue1(getValue1());
                    setValue1(tmp);

                    tmp = s.getValue2();
                    s.setValue2(getValue2());
                    setValue2(tmp);
                }
            }            
        }else{
            delay();
            synchronized(s){
                delay();
                synchronized(this){
                    int tmp = s.getValue1();
                    s.setValue1(getValue1());
                    setValue1(tmp);

                    tmp = s.getValue2();
                    s.setValue2(getValue2());
                    setValue2(tmp);
                }
            }
        
        }
       
    }
    private void delay(){
        delay(5000L,10000L);
    }
 
    private void delay(long l,long u){
        if(bDoDelay){
            try {
                r = ThreadLocalRandom.current();
                long n = r.nextLong(l,u);
                Thread.sleep(n);
            } catch (InterruptedException ex) {}
        }        
    }
}
