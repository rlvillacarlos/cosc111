package edu.java.cosc111.samples.concurrency.unsync;

public class Reader implements Runnable{
    private final String sName;
    private final Datastore ds;
        
    public Reader(String sName_,Datastore ds_){
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
            System.out.println(sName + ": " + ds.getData(this));
        }catch(InterruptedException ex){}
    }
}
