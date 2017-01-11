/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.java.cosc111.samples.concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RLVillacarlos
 */
public class FutureTaskSample {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Callable<Integer> c = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                //Simulate long computation....
                Thread.sleep(1000);
                return 10;
            }
        };
        
        FutureTask<Integer> task = new FutureTask<>(c);
        
        new Thread(task).start();
        int ctr =0;
        while(true){
            try {
                System.out.println("Waiting....");
                System.out.println(task.get(10,TimeUnit.MILLISECONDS));
                break;
            } catch (TimeoutException ex) {
                System.out.println("No result yet....");

            }
        }
        
        
        
    }
}
