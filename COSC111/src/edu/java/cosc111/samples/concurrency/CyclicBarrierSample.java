package edu.java.cosc111.samples.concurrency;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RLVillacarlos
 */
public class CyclicBarrierSample {
    private static CyclicBarrier barrier;
    private static List<Integer> lst = Collections.synchronizedList(new ArrayList<>());
    private static int i;
    public static void main(String[] args) {
        barrier = new CyclicBarrier(3,new Runnable() {
            @Override
            public void run() {
                int sum =0;
                for(int n:lst){
                    sum += n;
                }
                System.out.println(sum);
            }
        });
        
        for(i = 0;i<3;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        lst.add(1);
                        System.out.println("Waiting for other threads to finish...");
                        Thread.sleep(2000);                        
                        barrier.await();
                        System.out.println("Done");

                    } catch (InterruptedException ex) {
                        Logger.getLogger(CyclicBarrierSample.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (BrokenBarrierException ex) {
                        Logger.getLogger(CyclicBarrierSample.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
            }).start();
        }
    }
}
