package edu.java.cosc111.samples.concurrency;

/**
 *
 * @author Russel
 */
public interface Counter {
    public int increment();    
    public int getCount();    
    public boolean isMaxed();
}
