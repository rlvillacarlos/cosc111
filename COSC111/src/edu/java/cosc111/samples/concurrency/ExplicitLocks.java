package edu.java.cosc111.samples.concurrency;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author RLVillacarlos
 */
public class ExplicitLocks {
    private static class Container{
        private int value;
        private boolean bHasValue;
        private final ReentrantLock lck = new ReentrantLock();
        private final Condition hasValue = lck.newCondition();
        private final Condition hasNoValue = lck.newCondition();
        
        public Integer acquireValue(){
            try{
                lck.lock();
                while(!bHasValue){
                    long id = Thread.currentThread().getId();
                    System.out.printf("T%d: Waiting for value...%n",id);
                    hasValue.await();
                }
                bHasValue = false;
                hasNoValue.signal();             
                return value;
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }finally{
                lck.unlock();
            }
            return null;
        }
        
        public void setValue(Integer n){
            try{
                lck.lock();
                while(bHasValue){
                    long id = Thread.currentThread().getId();
                    System.out.printf("T%d: Waiting for old value to be acquired...%n",id);
                    hasNoValue.await();
                }
                bHasValue = true;
                value = n;
                hasValue.signal();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }finally{
                lck.unlock();
            }
        }
        
    }
    
    private static class Setter implements Runnable{
        private Container c;

        public Setter(Container c) {
            this.c = c;
        }

        @Override
        public void run() {
            try{
                ThreadLocalRandom r = ThreadLocalRandom.current();
                Thread.sleep(1000);
                long id = Thread.currentThread().getId();
                int v = r.nextInt(1, 100);
                c.setValue(v);
                System.out.printf("T%d: Value set to %d%n",id,v);
            }catch(InterruptedException ex){}
        }
        
        
    }
    
    private static class Acquirer implements Runnable{
        private Container c;

        public Acquirer(Container c) {
            this.c = c;
        }

        @Override
        public void run() {
            try{
                Thread.sleep(1000);
                long id = Thread.currentThread().getId();
                System.out.printf("T%d: Acquired value: %d%n",id,c.acquireValue());
            }catch(InterruptedException ex){}
        }
        
        
    }
    
    public static void main(String[] args) {
        Container c = new Container();
        new Thread(new Acquirer(c)).start();
        new Thread(new Setter(c)).start();
        new Thread(new Acquirer(c)).start();
        new Thread(new Setter(c)).start();
        new Thread(new Acquirer(c)).start();
        new Thread(new Setter(c)).start();
        
    }
}
