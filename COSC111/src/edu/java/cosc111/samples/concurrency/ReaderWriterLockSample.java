package edu.java.cosc111.samples.concurrency;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RLVillacarlos
 */
public class ReaderWriterLockSample {
    private static class Message{
        ReentrantReadWriteLock lck = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLck = lck.readLock();
        ReentrantReadWriteLock.WriteLock writeLck = lck.writeLock();
        
        private String msg;

        public Message(String msg) {
            this.msg = msg;
        }
        
        
        public void setMessage(String msg){
            try{
                writeLck.lock();
                this.msg = msg; 
            }finally{
                writeLck.unlock();
            }
        }
        
        public String getMessage(){
            try{
                readLck.lock();
                Thread.sleep(1000);
                return msg; 
            } catch (InterruptedException ex) {
                Logger.getLogger(ReaderWriterLockSample.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                readLck.unlock();
            }
            
            return "";
        }
    }
    
    private static Message msg = new Message("Blank Message");
    
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(msg.getMessage());
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(msg.getMessage());
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                msg.setMessage("New Message");
//                System.out.println();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(msg.getMessage());
            }
        }).start();
        
        
    }
    
    
}
