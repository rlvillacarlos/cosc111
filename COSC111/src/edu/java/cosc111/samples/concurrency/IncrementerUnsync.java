package edu.java.cosc111.samples.concurrency;

import edu.java.cosc111.samples.concurrency.unsync.UnsyncCounter;
import java.util.Scanner;

public class IncrementerUnsync {
    public static void main(String[] args) throws InterruptedException {
        Scanner in = new Scanner(System.in);
        
//        System.out.print("Number of threads: ");
        int n = 1000;
        
        Counter ctr = new UnsyncCounter(10);
//        Thread[] inc = new Thread[n];
//        
//        for(int i=0;i<inc.length;i++){
//            inc[i] = new Thread(new Incrementer(ctr));
//        }
//
//        for(Thread t:inc){
//            t.start();
//        }
//        
//        for(Thread t:inc){
//            t.join();
//        }
        for(int i = 0;i<n;i++){
            ctr.increment();
        }
        
        System.out.println("Counter: " + ctr.getCount());
    }
}