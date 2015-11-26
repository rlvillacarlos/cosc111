package edu.java.cosc111.samples.concurrency.sync;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Datastore {
    private final ReentrantReadWriteLock lckRW = new ReentrantReadWriteLock();
    private final ReadLock lckRead = lckRW.readLock();
    private final WriteLock lckWrite = lckRW.writeLock();
    private int data=0;
    
    public Datastore(){
        this(0);
    }
    
    public Datastore(int data_){
        data = data_;
    }
    
    public int setData(Writer w, int data_){
        int tmp; 
        try {
            lckWrite.lock();
            System.out.println(w.getName() + " has entered with data = " + data +
                               ". Setting data to " + data_);
            tmp = data;
            data = data_;
            try {
                w.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Datastore.class.getName()).log(Level.SEVERE, null, ex);
            }
            return tmp;
        } finally {
            System.out.println(w.getName() + " has exited with data = " + data + ".");
            lckWrite.unlock();
        }
    }
    
    public int getData(Reader r){
        try{
            lckRead.lock();
            System.out.println(r.getName() + " has entered with data = " + data + ".");
            try {
                r.sleep(500);
            } catch (InterruptedException ex) {}
            return data;
        }finally{
            System.out.println(r.getName() + " has exited with data = " + data + ".");
            lckRead.unlock();
        }
    }    
}
