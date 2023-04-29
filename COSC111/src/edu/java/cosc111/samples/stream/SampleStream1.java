package edu.java.cosc111.samples.stream;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * @author Acer
 */
public class SampleStream1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        IntStream.range(0, 11).forEach((i)->{
            System.out.println(i);
        });
        System.out.println("");
       
        IntStream.range(0, 11).map(i->i*2).forEach(i->{
            System.out.println(i);
        });
        System.out.println("");
       
        IntStream.range(0, 11).filter(i->i%2 == 0).forEach(i->{
            System.out.println(i);
        });
        System.out.println("");
                
       
    }
    
}
