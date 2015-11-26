package edu.java.cosc111.samples.concurrency.unsync;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Datastore {
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
        }
    }
    
    public int getData(Reader r){
        try{
            System.out.println(r.getName() + " has entered with data = " + data + ".");
            try {
                r.sleep(500);
            } catch (InterruptedException ex) {}
            return data;
        }finally{
            System.out.println(r.getName() + " has exited with data = " + data + ".");
        }
    }    
}
