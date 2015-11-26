package edu.java.cosc111.samples.concurrency.unsync;

public class Writer implements Runnable{
    private final String sName;
    private final Datastore ds;
        
    public Writer(String sName_,Datastore ds_){
        sName = sName_;
        ds = ds_;
    }
    
    public String getName() {
        return sName;
    }
    
    public void sleep(long millis) throws InterruptedException{
        Thread.sleep(millis);
    }
    
    @Override
    public void run(){
        try{
            Thread.sleep(1000);
            int data = (int)(Math.random()*100);
            ds.setData(this,data);
            System.out.println(sName + ": " + data);
        }catch(InterruptedException ex){}
    }    
}
