package edu.java.cosc111.samples.concurrency.sync;

public class SwapTest {
    public static void main(String[] args) 
                throws InterruptedException {
        
        Shareable s1  = new Shared(0,1,3);
        Shareable s2 = new Shared(1,2,4);
        Thread t1 = new Thread (
                        new Swapper("SW1",s1,s2));
        
        Thread t2 = new Thread (
                        new Swapper("SW2",s2,s1));
        
        System.out.println("-Before Swap-");
        System.out.println("s1.value1: " + s1.getValue1());
        System.out.println("s1.value2: " + s1.getValue2());
        System.out.println("s2.value1: " + s2.getValue1());
        System.out.println("s2.value2: " + s2.getValue2());

        Shared.doDelay(true);
        
        t1.start();
//        t2.start();
        
        t1.join();
//        t2.join();
        
        System.out.println("\n-After Swap-");
        System.out.println("s1.value1: " + s1.getValue1());
        System.out.println("s1.value2: " + s1.getValue2());
        System.out.println("s2.value1: " + s2.getValue1());
        System.out.println("s2.value2: " + s2.getValue2());
        
    }
    
}
