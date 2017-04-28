package edu.java.cosc111.samples.concurrency;


public class GreetingRunnableTest1 {
    public static void main(String[] args){
        String msg = "This message is just a test. Try to change me.";
        System.out.print("Your message: ");
        int i =0;
        for(String s:msg.split(" ")) {
             Runnable runnableCode = new GreetingRunnable(s + " ");
             Thread t = new Thread(runnableCode,"Message " + i);
             t.start();
             i++;
        }
        System.out.println("End");
    }
}
