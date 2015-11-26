package edu.java.cosc111.samples.concurrency.sync;

/**
 *
 * @author Russel
 */
public class ImmutableInteger {
    private final int val;

    public ImmutableInteger(int val_) {
        this.val = val_;
    }
    
    public ImmutableInteger setValue(int val_){
        return new ImmutableInteger(val_);
    }
    
    public int getValue(){
        return val;
    }
    
    public static void main(String[] args){
        ImmutableInteger n = new ImmutableInteger((5));
        System.out.println(n.getValue());        
        n = n.setValue(6);
        System.out.println(n.getValue());        
        
    }
    
}
