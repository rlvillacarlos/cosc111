package edu.java.cosc111.samples.concurrency;

public class GreetingThreadTest1 {
    public static void main(String[] args) throws InterruptedException{		
        String msg = "This message is just a test. Try to change me.";
        System.out.print("Your message: ");
        for(String s:msg.split(" ")) {
            //Create and run a GreetingThread
            Thread t = new GreetingThread(s + " ");            
//            System.out.println(t.getState());
            t.start();
//            System.out.println(t.getState());
//            t.join();
        }
    }
}
