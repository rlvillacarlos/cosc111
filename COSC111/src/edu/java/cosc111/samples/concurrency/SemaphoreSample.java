/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.java.cosc111.samples.concurrency;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RLVillacarlos
 */
public class SemaphoreSample {
    //Binary Semaphore
    private static Semaphore lck= new Semaphore(2);
    
    public static void main(String[] args) {
        for(int i=0;i<5;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        lck.acquire();
                        System.out.println("Ticket acquired...");
                        Thread.sleep(1000);
                        lck.release();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(SemaphoreSample.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }).start();
        }
        
        
    }
}
