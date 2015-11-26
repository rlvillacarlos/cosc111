package edu.java.cosc111.samples.concurrency.sync;

/**
 *
 * @author Russel
 */
public interface Shareable {
    public int getValue1();
    public int setValue1(int v);
    public int getValue2();
    public int setValue2(int v);    
    public void swap (Shareable s);
}
