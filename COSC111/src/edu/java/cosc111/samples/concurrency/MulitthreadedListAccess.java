package edu.java.cosc111.samples.concurrency;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RLVillacarlos
 */
public class MulitthreadedListAccess {
   private static class Printer implements Runnable{
        private List<String> lst;

        public Printer(List<String> lst) {
            this.lst = lst;
        }
        
        @Override
        public void run() {
            try {
                Thread.sleep(100);
//                synchronized(lst){
                    for(String s: lst){
                        System.out.println(s);
                    }
//                }
            } catch (InterruptedException ex) {
                Logger.getLogger(MulitthreadedListAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       
   }
   private static class Remover implements Runnable{
        private List<String> lst;

        public Remover(List<String> lst) {
            this.lst = lst;
        }
        
        @Override
        public void run() {
            try {
                synchronized(lst){
                    Thread.sleep(100);
                    if(!lst.isEmpty()){
                        Thread.sleep(100);
                        System.out.printf("Item \"%s\" was removed%n",lst.remove(0));
                    }
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(MulitthreadedListAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       
   }
   private static class Adder implements Runnable{
        private List<String> lst;
        private String content;
        
        public Adder(List<String> lst,String content){
            this.lst = lst;
            this.content = content;
        }
               
       
        @Override
        public void run() {
            try {
                Thread.sleep(100);
//                synchronized(lst){
                    lst.add(content);                
//                }
            } catch (InterruptedException ex) {
                Logger.getLogger(MulitthreadedListAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        }   
   }
   
   
    public static void main(String[] args) {
        List lst = new CopyOnWriteArrayList();
        Map<String,List> friends = new ConcurrentHashMap<>();
        
        new Thread(new Adder(lst, "Hello")).start();
        new Thread(new Remover(lst)).start();
        new Thread(new Remover(lst)).start();
//        new Thread(new Adder(lst, "World")).start();
//        new Thread(new Adder(lst, "Bye")).start();
        
    }
}
