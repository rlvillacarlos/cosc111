package edu.java.cosc111.samples;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author RLVillacarlos
 */
public class RandomIntegerIDGenerator {
    public static final int MAX_DIGITS = 10; //int have up to 10 digits only
    
    public static List<Integer> generate(int numDigits, int minId, int count){
        
        //Check if number of digits is within range
        if(numDigits < 1 || numDigits > MAX_DIGITS){
            throw new IllegalArgumentException("Invalid number of digits.");
        }
        
        //Compute last id number for the given number of digits
        int maxId = (numDigits < MAX_DIGITS?((int)Math.pow(10, numDigits)) - 1:Integer.MAX_VALUE); 
        
        //Check if minimum allowed id number is within range
        if(minId < 1 || minId > maxId){
            throw new IllegalArgumentException("Invalid start ID value."); 
        }
         
        //Compute the number of possible ids
        int maxNumId = maxId - minId + 1; 
        
        //Check if rwuired number of id is within range
        if(count < 0 || count > maxNumId){
            throw new IllegalArgumentException("Invalid number of ID to generate.");
        }
        
        //Create a new (pseudo)random number generator
        Random rnd = new Random(System.currentTimeMillis());
        
        //Create a set for storing unique ids
        Set<Integer> set = new HashSet<>(count);
        
        //Loop until we have the required number of ids;
        while(set.size()<count){
            //Generate a new id
            int id = rnd.nextInt(maxNumId) + minId;
            
            //Add the new id to the set.
            set.add(id);
        }        
        
        //Return the ids as list instead of set.
        return new ArrayList<>(set);
    }
}
