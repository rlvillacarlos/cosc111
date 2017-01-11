package edu.java.cosc111.samples.concurrency.sync;

public class SetTest {
    public static void main(String[] args) 
                throws InterruptedException {
        Shareable s1  = new Shared(0,0,0);
        Thread th[] = new Thread[2];
        
        
        for(int i=0;i<th.length;i+=2){
            th[i] = new Thread(new SetterValue1("SV1[" + i/2 +"]", s1));
            th[i+1] = new Thread(new SetterValue2("SV2[" + i/2 +"]", s1));
        }
        
        Shared.doDelay(true);
        for(Thread t:th){
            t.start();
        }        
    }
    
}
