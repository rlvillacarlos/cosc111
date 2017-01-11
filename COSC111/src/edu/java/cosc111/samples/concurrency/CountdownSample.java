package edu.java.cosc111.samples.concurrency;

import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RLVillacarlos
 */
public class CountdownSample {
    private static CountDownLatch latch = new CountDownLatch(5);
    
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                for(int i =0;i<5;i++){
                    
                    try {
                        System.out.println("Waiting....");
                        latch.await();
                        System.out.println("Can proceed...");
//                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(CountdownSample.class.getName()).log(Level.SEVERE, null, ex);
                    }
//                }
            }
        }).start();
        
        for(int i = 0;i<5;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("Processing...");
                        Thread.sleep(1000);
                        latch.countDown();
                        System.out.println("Done");
                    } catch (InterruptedException ex) {
                        Logger.getLogger(CountdownSample.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }).start();
        }
        
    }
}
